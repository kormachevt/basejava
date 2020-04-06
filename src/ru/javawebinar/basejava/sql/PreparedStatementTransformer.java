package ru.javawebinar.basejava.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PreparedStatementTransformer<T> {
    T transform(PreparedStatement preparedStatement) throws SQLException;
}