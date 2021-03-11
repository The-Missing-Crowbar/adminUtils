package com.themissingcrowbar.adminUtils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TabCompleteGetWarnTypes implements TabCompleter {
    List<String> warnTypeNames = new ArrayList<>();

    public TabCompleteGetWarnTypes() {
        try {
            ResultSetConnectionWrapper result = Database.getWarnTypes();
            while (result.resultSet.next())
                warnTypeNames.add(result.resultSet.getString(3));
            result.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length==1) {
            return warnTypeNames;
        }
        return new ArrayList<>();
    }
}
