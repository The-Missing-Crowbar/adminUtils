package com.themissingcrowbar.adminUtils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class CommandEditWarn implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length < 3)
            return false;

        try {
            int warnId = Integer.parseInt(strings[0]);
            StringBuilder reason = new StringBuilder();
            for (int i=2; i<strings.length; i++)
                reason.append(strings[i]).append(' ');
            return Database.editWarn(warnId, strings[1], reason);
        } catch (NumberFormatException | SQLException ignore) {
            return false;
        }
    }
}
