package com.themissingcrowbar.adminUtils;

import java.sql.*;

public class Database {
    private static final String url = "jdbc:mysql://192.168.1.52:5618/minecraft?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String user = "quentin";
    private static final String password = "9o#A0eFKUk9xF";

    public static void deleteWarn(int id) {
        String query = "DELETE FROM minecraft.warnings w WHERE w.warnId = ?;";

        try {
            Connection con = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate(query);
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public static boolean editWarn (int id, String column, Object newValue) {
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

        try {
            Connection con = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setObject(1, newValue);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            System.out.println(preparedStatement);
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }
        return true;
    }
}

