package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private static final String dbUrl = Config.get().getDbUrl();
    private static final String dbUser = Config.get().getDbUser();
    private static final String dbPassword = Config.get().getDbPassword();
    private final ConnectionFactory connectionFactory;


    public SqlHelper() {
        this.connectionFactory = () -> {
            if (!org.postgresql.Driver.isRegistered()) {
                org.postgresql.Driver.register();
            }
            return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        };
    }

    public <T> T execute(String sql, PreparedStatementTransformer<T> statementTransformer) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            return statementTransformer.transform(preparedStatement);
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }

    public <T> T transactionalExecute(SqlTransaction<T> executor) {
        try (Connection conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                T res = executor.execute(conn);
                conn.commit();
                return res;
            } catch (SQLException e) {
                conn.rollback();
                throw ExceptionUtil.convertException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
