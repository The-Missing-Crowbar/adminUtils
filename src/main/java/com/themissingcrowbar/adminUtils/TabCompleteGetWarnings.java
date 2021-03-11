package com.themissingcrowbar.adminUtils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TabCompleteGetWarnings implements TabCompleter {
    List<String> ordersAndNums = new ArrayList<>();
    List<String> orders = new ArrayList<>();

    public TabCompleteGetWarnings() {
        orders.add("asc");
        orders.add("desc");
        ordersAndNums.add("asc");
        ordersAndNums.add("desc");
        for (int i = 0; i <= 100; i++)
            ordersAndNums.add(String.valueOf(i));
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length==1)
            return null; // Defaults to usernames

        if (strings.length==2)
            return ordersAndNums;

        try {
            Integer.parseInt(strings[1]);
            return orders;
        } catch (NumberFormatException ignored) {
            return new ArrayList<>();
        }
    }
}
