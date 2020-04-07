package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.TransactionExecutor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private final TransactionExecutor transaction;
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());


    public SqlStorage() {
        transaction = new TransactionExecutor();
    }

    @Override
    public void clear() {
        transaction.execute("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        transaction.execute("INSERT INTO resume(uuid,full_name) VALUES (?,?)", (ps) -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            return ps.executeUpdate();
        });
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        transaction.execute("UPDATE resume SET full_name = ? WHERE uuid = ?", (ps) -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            return doUpdate(ps, resume.getUuid());
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return transaction.execute("SELECT * FROM resume r WHERE r.uuid=?", (ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        }));
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        transaction.execute("DELETE FROM resume r WHERE r.uuid=?", (ps -> {
            ps.setString(1, uuid);
            return doUpdate(ps, uuid);
        }));
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GetAllSorted");
        return transaction.execute("SELECT * FROM resume ORDER BY full_name, uuid", (ps -> {
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
        return transaction.execute("SELECT COUNT(*) AS rowcount FROM resume", (ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("rowcount");
        }));
    }

    private int doUpdate(PreparedStatement ps, String uuid) throws SQLException {
        int updatedRows = ps.executeUpdate();
        if(updatedRows == 0){
            LOG.warning("The Resume [" + uuid + "] is not exist");
            throw new NotExistStorageException(uuid);
        }
        return updatedRows;
    }
}
