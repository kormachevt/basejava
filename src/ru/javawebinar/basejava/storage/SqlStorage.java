package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());


    public SqlStorage() {
        sqlHelper = new SqlHelper();
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
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
                for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, e.getKey().name());
                    ps.setString(3, e.getValue());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        sqlHelper.execute("UPDATE resume SET full_name = ? WHERE uuid = ?", (ps) -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            return doUpdate(ps, resume.getUuid());
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return sqlHelper.execute("" +
                                         " SELECT * FROM resume r " +
                                         "   LEFT JOIN contact c " +
                                         "     ON r.uuid = c.resume_uuid " +
                                         "       WHERE r.uuid =?",
                                 ps -> {
                                     ps.setString(1, uuid);
                                     ResultSet rs = ps.executeQuery();
                                     if (!rs.next()) {
                                         throw new NotExistStorageException(uuid);
                                     }
                                     Resume r = new Resume(uuid, rs.getString("full_name"));
                                     do {
                                         String value = rs.getString("value");
                                         ContactType type = ContactType.valueOf(rs.getString("type"));
                                         r.addContact(type, value);
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
        LOG.info("GetAllSorted");
        return sqlHelper.execute("SELECT * FROM resume ORDER BY full_name, uuid", (ps -> {
            ResultSet rs = ps.executeQuery();
            ArrayList<Resume> resumeList = new ArrayList<>();
            while (rs.next()) {
                Resume resume = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                resumeList.add(resume);
            }
            return resumeList;
        }));
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
}
