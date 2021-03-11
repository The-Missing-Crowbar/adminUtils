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

public class TabCompleteWarn implements TabCompleter {
    List<String> warnTypeNames = new ArrayList<>();
    List<String> numbers = new ArrayList<>();

    public TabCompleteWarn() {
        try {
            ResultSetConnectionWrapper result = Database.getWarnTypes();
            while (result.resultSet.next())
                warnTypeNames.add(result.resultSet.getString(3));
            result.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        for (int i = 0; i <= 100; i++)
            numbers.add(String.valueOf(i));
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length==1)
            return null;

        if (strings.length==2)
            return warnTypeNames;

        if (strings.length==3)
            return numbers;

        return new ArrayList<>();
    }
}
