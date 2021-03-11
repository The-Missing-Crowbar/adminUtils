package com.themissingcrowbar.adminUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetConnectionWrapper {
    public final ResultSet resultSet;
    public final Connection connection;

    public ResultSetConnectionWrapper(ResultSet resultSet, Connection connection) {
        this.resultSet = resultSet;
        this.connection = connection;
    }

    public void close() throws SQLException {
        resultSet.close();
        connection.close();
    }
}
