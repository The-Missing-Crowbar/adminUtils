package com.themissingcrowbar.adminUtils;

import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.Calendar;
import java.util.Locale;

public class Database {
    private static final String url = "jdbc:mysql://192.168.1.52:5618/minecraft?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String user = "quentin";
    private static final String password = "9o#A0eFKUk9xF";

    public static boolean deleteWarn(int id) throws SQLException {
        String query = "DELETE FROM minecraft.warnings w WHERE w.warnId = ?;";

        Connection con = DriverManager.getConnection(url, user, password);
        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        con.close();
        return true;
    }

    public static boolean editWarn (int id, String column, Object newValue) throws SQLException {
        String query;
        if (column.equals("player")) {
            query = "UPDATE minecraft.warnings w SET w.player=? WHERE w.warnId=?;";
        }
        else if (column.equals("warnType")) {
            query = "UPDATE minecraft.warnings w SET w.warnType=? WHERE w.warnId=?;";
        }
        else if (column.equals("description")) {
            query = "UPDATE minecraft.warnings w SET w.description=? WHERE w.warnId=?;";
        }
        else {
            return false;
        }

        Connection con = DriverManager.getConnection(url, user, password);
        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setObject(1, newValue);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
        con.close();
        return true;
    }

    @Nullable
    public static ResultSetConnectionWrapper getWarnings(String UUID) throws SQLException {
        String query = "SELECT * FROM minecraft.warnings w WHERE player=?;";

        Connection con = DriverManager.getConnection(url, user, password);
        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setString(1, UUID);
        return new ResultSetConnectionWrapper(preparedStatement.executeQuery(), con);
    }

    @Nullable
    public static ResultSetConnectionWrapper getWarnings(String UUID, int limit) throws SQLException {
        String query = "SELECT * FROM minecraft.warnings w WHERE player=? LIMIT ?;";

        Connection con = DriverManager.getConnection(url, user, password);
        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setString(1, UUID);
        preparedStatement.setInt(2, limit);
        return new ResultSetConnectionWrapper(preparedStatement.executeQuery(), con);
    }

    @Nullable
    public static ResultSetConnectionWrapper getWarnings(String UUID, int limit, String order) throws SQLException {
        String query;
        if (order.equalsIgnoreCase("asc"))
            query = "SELECT * FROM minecraft.warnings w WHERE player=? ORDER BY w.warnId asc LIMIT ?;";
        else if (order.equalsIgnoreCase("desc"))
            query = "SELECT * FROM minecraft.warnings w WHERE player=? ORDER BY w.warnId desc LIMIT ?;";
        else
            return null;

        Connection con = DriverManager.getConnection(url, user, password);
        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setString(1, UUID);
        preparedStatement.setInt(2, limit);
        return new ResultSetConnectionWrapper(preparedStatement.executeQuery(), con);
    }

    @Nullable
    public static ResultSetConnectionWrapper getWarnings(String UUID, String order) throws SQLException {
        String query;
        if (order.equalsIgnoreCase("asc"))
            query = "SELECT * FROM minecraft.warnings w WHERE player=? ORDER BY w.warnId asc;";
        else if (order.equalsIgnoreCase("desc"))
            query = "SELECT * FROM minecraft.warnings w WHERE player=? ORDER BY w.warnId desc;";
        else
            return null;

        Connection con = DriverManager.getConnection(url, user, password);
        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setString(1, UUID);
        return new ResultSetConnectionWrapper(preparedStatement.executeQuery(), con);
    }

    @Nullable
    public static ResultSetConnectionWrapper getWarnTypes() throws SQLException {
        String query = "SELECT * FROM minecraft.warning_types";

        Connection con = DriverManager.getConnection(url, user, password);
        PreparedStatement preparedStatement = con.prepareStatement(query);
        return new ResultSetConnectionWrapper(preparedStatement.executeQuery(), con);
    }

    @Nullable
    public static ResultSetConnectionWrapper getWarnTypes(int id) throws SQLException {
        String query = "SELECT * FROM minecraft.warning_types w where w.warnId=?";

        Connection con = DriverManager.getConnection(url, user, password);
        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setInt(1, id);
        return new ResultSetConnectionWrapper(preparedStatement.executeQuery(), con);
    }

    @Nullable
    public static ResultSetConnectionWrapper getWarnTypes(String type) throws SQLException {
        String query = "SELECT * FROM minecraft.warning_types w where w.name=?";

        Connection con = DriverManager.getConnection(url, user, password);
        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setString(1, type.toUpperCase(Locale.ROOT));
        return new ResultSetConnectionWrapper(preparedStatement.executeQuery(), con);
    }

    public static boolean warn(String UUID, int typeId, int offences, String reason) throws SQLException {
        Date today = new Date(Calendar.getInstance().getTime().getTime());
        String query = "INSERT INTO minecraft.warnings (warnType, date, player, description) VALUES (?, ?, ?, ?);";

        for (int i = 0; i < offences; i++) {
            Connection con = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, typeId);
            preparedStatement.setDate(2, today);
            preparedStatement.setString(3, UUID);
            preparedStatement.setString(4, reason);
            preparedStatement.executeUpdate();
            con.close();
        }
        return true;
    }

    public static boolean warn(String UUID, int typeId, String reason) throws SQLException {
        Date today = new Date(Calendar.getInstance().getTime().getTime());
        String query = "INSERT INTO minecraft.warnings (warnType, date, player, description) VALUES (?, ?, ?, ?);";

        Connection con = DriverManager.getConnection(url, user, password);
        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setInt(1, typeId);
        preparedStatement.setDate(2, today);
        preparedStatement.setString(3, UUID);
        preparedStatement.setString(4, reason);
        preparedStatement.executeUpdate();
        con.close();
        return true;
    }

    public static boolean warn(String UUID, int typeId, int offences) throws SQLException {
        Date today = new Date(Calendar.getInstance().getTime().getTime());
        String query = "INSERT INTO minecraft.warnings (warnType, date, player, description) VALUES (?, ?, ?, null);";

        for (int i = 0; i < offences; i++) {
            Connection con = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, typeId);
            preparedStatement.setDate(2, today);
            preparedStatement.setString(3, UUID);
            preparedStatement.executeUpdate();
            con.close();
        }
        return true;
    }

    public static boolean warn(String UUID, int typeId) throws SQLException {
        Date today = new Date(Calendar.getInstance().getTime().getTime());
        String query = "INSERT INTO minecraft.warnings (warnType, date, player, description) VALUES (?, ?, ?, null);";

        Connection con = DriverManager.getConnection(url, user, password);
        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setInt(1, typeId);
        preparedStatement.setDate(2, today);
        preparedStatement.setString(3, UUID);
        preparedStatement.executeUpdate();
        con.close();
        return true;
    }

    public static boolean warn(String UUID, String typeName, int offences, String reason) throws SQLException {
        ResultSetConnectionWrapper result = Database.getWarnTypes(typeName);
        result.resultSet.next();
        int typeId = result.resultSet.getInt("warnId");
        result.close();
        return warn(UUID, typeId, offences, reason);
    }

    public static boolean warn(String UUID, String typeName, String reason) throws SQLException {
        ResultSetConnectionWrapper result = Database.getWarnTypes(typeName);
        result.resultSet.next();
        int typeId = result.resultSet.getInt("warnId");
        result.close();
        return warn(UUID, typeId, reason);
    }

    public static boolean warn(String UUID, String typeName, int offences) throws SQLException {
        ResultSetConnectionWrapper result = Database.getWarnTypes(typeName);
        result.resultSet.next();
        int typeId = result.resultSet.getInt("warnId");
        result.close();
        return warn(UUID, typeId, offences);
    }

    public static boolean warn(String UUID, String typeName) throws SQLException {
        ResultSetConnectionWrapper result = Database.getWarnTypes(typeName);
        result.resultSet.next();
        int typeId = result.resultSet.getInt("warnId");
        result.close();
        return warn(UUID, typeId);
    }


}

