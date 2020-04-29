package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());


    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume(uuid,full_name) VALUES (?,?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.executeUpdate();
            }
            upsertContacts(resume, conn);
            upsertSections(resume, conn);
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                doUpdate(ps, resume.getUuid());
            }
            deleteContacts(resume, conn);
            upsertContacts(resume, conn);

            deleteSections(resume, conn);
            upsertSections(resume, conn);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return sqlHelper.execute("" +
                                         " SELECT r.full_name AS r_full_name,                  " +
                                         "        c.value AS c_value,                          " +
                                         "        c.type AS c_type,                            " +
                                         "        s.value AS s_value,                          " +
                                         "        s.type AS s_type                             " +
                                         "   FROM resume r                                     " +
                                         "     LEFT JOIN contact c ON r.uuid = c.resume_uuid   " +
                                         "       LEFT JOIN section s ON r.uuid = s.resume_uuid " +
                                         "         WHERE r.uuid =?                             ",
                                 ps -> {
                                     ps.setString(1, uuid);
                                     ResultSet rs = ps.executeQuery();
                                     if (!rs.next()) {
                                         throw new NotExistStorageException(uuid);
                                     }
                                     Resume r = new Resume(uuid, rs.getString("r_full_name"));
                                     do {
                                         String contactValue = rs.getString("c_value");
                                         String contactTypeName = rs.getString("c_type");
                                         String sectionValue = rs.getString("s_value");
                                         String sectionTypeName = rs.getString("s_type");

                                         readContact(r, contactTypeName, contactValue);
                                         readSection(r, sectionTypeName, sectionValue);
                                     } while (rs.next());

                                     return r;
                                 });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        sqlHelper.execute("DELETE FROM resume r WHERE r.uuid=?", (ps -> {
            ps.setString(1, uuid);
            return doUpdate(ps, uuid);
        }));
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("Get sorted list of Resumes");
        Map<String, Resume> resumesByUuid = new LinkedHashMap<>();
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    String fullName = rs.getString("full_name");
                    resumesByUuid.put(uuid, new Resume(uuid, fullName));
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String resumeUuid = rs.getString("resume_uuid");
                    String contactTypeName = rs.getString("type");
                    String contactValue = rs.getString("value");
                    readContact(resumesByUuid.get(resumeUuid), contactTypeName, contactValue);
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String resumeUuid = rs.getString("resume_uuid");
                    String sectionTypeName = rs.getString("type");
                    String sectionValue = rs.getString("value");
                    readSection(resumesByUuid.get(resumeUuid), sectionTypeName, sectionValue);
                }
            }
            return null;
        });
        return new ArrayList<>(resumesByUuid.values());
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) AS rowcount FROM resume", (ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("rowcount");
        }));
    }

    private int doUpdate(PreparedStatement ps, String uuid) throws SQLException {
        int updatedRows = ps.executeUpdate();
        if (updatedRows == 0) {
            LOG.warning("The Resume [" + uuid + "] is not exist");
            throw new NotExistStorageException(uuid);
        }
        return updatedRows;
    }

    private void upsertContacts(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("" +
                                                                  "  INSERT INTO contact(resume_uuid, type, value)" +
                                                                  "    VALUES (?,?,?) ON CONFLICT (type,resume_uuid) " +
                                                                  "      DO UPDATE SET value=?  ")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.setString(4, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void upsertSections(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("" +
                                                                  "  INSERT INTO section(resume_uuid, type, value)" +
                                                                  "    VALUES (?,?,?) ON CONFLICT (type,resume_uuid) " +
                                                                  "      DO UPDATE SET value=?  ")) {
            for (Map.Entry<SectionType, Section> e : resume.getSections().entrySet()) {
                SectionType sectionType = e.getKey();
                Section section = e.getValue();
                ps.setString(1, resume.getUuid());
                ps.setString(2, sectionType.name());

                switch (e.getKey()) {
                    case PERSONAL:
                    case OBJECTIVE:
                        ps.setString(3, ((TextSection) section).getText());
                        ps.setString(4, ((TextSection) section).getText());
                        break;
                    case ACHIEVEMENTS:
                    case QUALIFICATIONS:
                        List<String> listSectionContent = ((ListSection) section).getList();
                        String joinedListSectionContent = String.join("\n", listSectionContent);
                        ps.setString(3, joinedListSectionContent);
                        ps.setString(4, joinedListSectionContent);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + sectionType.name());
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteContacts(Resume resume, Connection conn) throws SQLException {
        Array contactTypes = conn.createArrayOf("TEXT", resume.getContacts().keySet().toArray());
        try (PreparedStatement ps = conn.prepareStatement("" +
                                                                  "  DELETE FROM contact  " +
                                                                  "    WHERE (resume_uuid=? AND type <> ALL (?))  ")) {
            ps.setString(1, resume.getUuid());
            ps.setArray(2, contactTypes);
            ps.execute();
        }
    }

    private void deleteSections(Resume resume, Connection conn) throws SQLException {
        Array sectionTypes = conn.createArrayOf("TEXT", resume.getSections().keySet().toArray());
        try (PreparedStatement ps = conn.prepareStatement("" +
                                                                  "  DELETE FROM section  " +
                                                                  "    WHERE (resume_uuid=? AND type <> ALL (?))  ")) {
            ps.setString(1, resume.getUuid());
            ps.setArray(2, sectionTypes);
            ps.execute();
        }
    }

    private void readSection(Resume resume, String sectionTypeName, String sectionValue) {
        if (sectionValue != null && sectionTypeName != null) {
            SectionType sectionType = SectionType.valueOf(sectionTypeName);
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    resume.addSection(sectionType, new TextSection(sectionValue));
                    break;
                case ACHIEVEMENTS:
                case QUALIFICATIONS:
                    List<String> listSectionContent = new ArrayList<>(Arrays.asList(sectionValue.split("\n")));
                    resume.addSection(sectionType, new ListSection(listSectionContent));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + sectionType.name());

            }

        }
    }

    private void readContact(Resume resume, String contactTypeName, String contactValue) {
        if (contactValue != null && contactTypeName != null) {
            ContactType contactType = ContactType.valueOf(contactTypeName);
            resume.addContact(contactType, contactValue);
        }
    }
}
