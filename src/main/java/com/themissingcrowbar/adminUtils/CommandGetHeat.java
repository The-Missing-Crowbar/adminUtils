package com.themissingcrowbar.adminUtils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class CommandGetHeat implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length != 1)
            return false;

        Player player = Bukkit.getPlayer(strings[0]);
        String UUID = Utilities.getUUID(player, strings[0]);
        if (UUID == null) {
            commandSender.sendMessage("Error, does user exist?");
            return false;
        }

        ResultSetConnectionWrapper result;
        try {
            result = Database.getWarnings(UUID);
        } catch (SQLException throwables) {
            return false;
        }

        try {
            commandSender.sendMessage(String.valueOf(Utilities.getHeat(result)));
            result.close();
            return true;
        } catch (SQLException | NotAWarningException throwables) {
            return false;
        }
    }
}
