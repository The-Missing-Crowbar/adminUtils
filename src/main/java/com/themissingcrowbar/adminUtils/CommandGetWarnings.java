package com.themissingcrowbar.adminUtils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.UUID;

public class CommandGetWarnings implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length < 1)
            return false;

        String UUID = Utilities.getUUID(Bukkit.getPlayer(strings[0]), strings[0]);
        if (UUID == null) {
            commandSender.sendMessage("Error, does user exist?");
            return false;
        }

        if (strings.length == 1)
            return getWarnings(commandSender, UUID);

        try {
            int limit = Integer.parseInt(strings[1]);
            if (strings.length >= 3)
                return getWarnings(commandSender, UUID, limit, strings[2]);
            return getWarnings(commandSender, UUID, limit);
        } catch (NumberFormatException ignored) {
        }

        return getWarnings(commandSender, UUID, strings[1]);
    }

    private boolean getWarnings(CommandSender commandSender, String UUID, String order) {
        try {
            ResultSetConnectionWrapper resultSet = Database.getWarnings(UUID, order);
            parseResultSet(commandSender, UUID, resultSet);

        } catch (SQLException throwables) {
            return false;
        }
        return true;
    }

    private boolean getWarnings(CommandSender commandSender, String UUID, int limit) {
        try {
            ResultSetConnectionWrapper resultSet = Database.getWarnings(UUID, limit);
            parseResultSet(commandSender, UUID, resultSet);

        } catch (SQLException throwables) {
            return false;
        }
        return true;
    }

    private boolean getWarnings(CommandSender commandSender, String UUID, int limit, String order) {
        try {
            ResultSetConnectionWrapper resultSet = Database.getWarnings(UUID, limit, order);
            parseResultSet(commandSender, UUID, resultSet);

        } catch (SQLException throwables) {
            return false;
        }
        return true;
    }

    public boolean getWarnings(CommandSender commandSender, String UUID) {
        try {
            ResultSetConnectionWrapper result = Database.getWarnings(UUID);
            parseResultSet(commandSender, UUID, result);
        } catch (SQLException ignore) {
            return false;
        }

        return true;
    }

    private void parseResultSet(CommandSender commandSender, String UUID, ResultSetConnectionWrapper result) throws SQLException {
        OfflinePlayer player = Bukkit.getOfflinePlayer(java.util.UUID.fromString(UUID));
        try {
            while (result.resultSet.next()) {
                ResultSetConnectionWrapper typeSet = Database.getWarnTypes(result.resultSet.getInt(2));
                typeSet.resultSet.next();
                String type = typeSet.resultSet.getString(3);
                typeSet.close();


                String message = "WarnID: " + result.resultSet.getInt(1) + "\n" +
                        " - Type: " + type + "\n" +
                        " - Date: " + result.resultSet.getDate(3).toString() + "\n" +
                        " - Player: " + player.getName() + "\n" +
                        " - Description: " + (result.resultSet.getString(5) != null? result.resultSet.getString(5):"None");
                commandSender.sendMessage(message);
            }
        } catch (NullPointerException e) {
            commandSender.sendMessage("Player "+ player.getName()+" doesn't have any warnings");
        }
        result.close();
    }
}
