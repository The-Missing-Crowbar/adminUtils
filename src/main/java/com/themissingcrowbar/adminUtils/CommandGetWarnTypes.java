package com.themissingcrowbar.adminUtils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommandGetWarnTypes implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length <=0 )
            return false;

        try {
            int typeId = Integer.parseInt(strings[0]);
            return getWarnTypes(commandSender, typeId);
        } catch (NumberFormatException ignore ) {
            return getWarnTypes(commandSender, strings[0]);
        }
    }

    public boolean getWarnTypes(CommandSender commandSender) {
        try {
            parseResultSet(commandSender, Database.getWarnTypes());
        } catch (SQLException ignore) {
            return false;
        }
        return true;
    }

    public boolean getWarnTypes(CommandSender commandSender, String typeName) {
        try {
            parseResultSet(commandSender, Database.getWarnTypes(typeName));
        } catch (SQLException ignore) {
            return false;
        }
        return true;
    }

    public boolean getWarnTypes(CommandSender commandSender, int typeId) {
        try {
            parseResultSet(commandSender, Database.getWarnTypes(typeId));
        } catch (SQLException ignore) {
            return false;
        }
        return true;
    }

    public void parseResultSet(CommandSender commandSender, ResultSetConnectionWrapper result) throws SQLException {
        try {
            while (result.resultSet.next()) {
                String message = "TypeID: " + result.resultSet.getInt(1) + "\n" +
                        " - Weight: " + result.resultSet.getInt(2) + "\n" +
                        " - Name: " + result.resultSet.getString(3) + "\n" +
                        " - DecayRate: " + result.resultSet.getBigDecimal(5).toString() + "\n" +
                        " - Description: " + result.resultSet.getString(4);
                commandSender.sendMessage(message);
            }
        } catch (NullPointerException e) {
            commandSender.sendMessage("No warnType found");
        }
        result.close();
    }
}
