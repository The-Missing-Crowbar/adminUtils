package com.themissingcrowbar.adminUtils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class CommandDeleteWarn implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length != 1)
            return false;
        try {
            int warnId = Integer.parseInt(strings[0]);
            return Database.deleteWarn(warnId);
        } catch (NumberFormatException | SQLException ignore) {
            return false;
        }
    }
}
