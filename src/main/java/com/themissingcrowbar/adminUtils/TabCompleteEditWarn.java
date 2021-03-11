package com.themissingcrowbar.adminUtils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TabCompleteEditWarn implements TabCompleter {
    List<String> columns = new ArrayList<>();

    public TabCompleteEditWarn() {
        columns.add("player");
        columns.add("warnType");
        columns.add("description");
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length==2)
            return columns;
        return new ArrayList<>();
    }
}
