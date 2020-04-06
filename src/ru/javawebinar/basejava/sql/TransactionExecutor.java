package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionExecutor {
    private static final String dbUrl = Config.get().getDbUrl();
    private static final String dbUser = Config.get().getDbUser();
    private static final String dbPassword = Config.get().getDbPassword();
    private final ConnectionFactory connectionFactory;


    public TransactionExecutor() {
        this.connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public <T> T execute(String sql, PreparedStatementTransformer<T> statementTransformer) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            return statementTransformer.transform(preparedStatement);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
