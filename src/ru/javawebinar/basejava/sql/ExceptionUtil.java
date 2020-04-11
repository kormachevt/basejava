package ru.javawebinar.basejava.sql;

import org.postgresql.util.PSQLException;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.storage.AbstractStorage;

import java.sql.SQLException;
import java.util.logging.Logger;

public class ExceptionUtil {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    private ExceptionUtil() {
    }

    public static StorageException convertException(SQLException e) {
        if (e instanceof PSQLException) {

//            http://www.postgresql.org/docs/9.3/static/errcodes-appendix.html
            if (e.getSQLState().equals("23505")) {
                LOG.warning(e.getMessage());
                return new ExistStorageException(null);
            }
        }
        return new StorageException(e);
    }
}