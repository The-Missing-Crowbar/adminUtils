package com.themissingcrowbar.adminUtils;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onDisable() {
        getLogger().info("Initializing AdminUtils");
    }

    @Override
    public void onEnable() {
        getLogger().info("Init AdminUtils");
        getLogger().info("Registering commands");
        this.getCommand("getWarnings").setExecutor(new CommandGetWarnings());
        this.getCommand("getWarnings").setTabCompleter(new TabCompleteGetWarnings());
        this.getCommand("warn").setExecutor(new CommandWarn());
        this.getCommand("warn").setTabCompleter(new TabCompleteWarn());
        this.getCommand("getWarnTypes").setExecutor(new CommandGetWarnTypes());
        this.getCommand("getWarnTypes").setTabCompleter(new TabCompleteGetWarnTypes());
        this.getCommand("editWarn").setExecutor(new CommandEditWarn());
        this.getCommand("editWarn").setTabCompleter(new TabCompleteEditWarn());
        this.getCommand("deleteWarn").setExecutor(new CommandDeleteWarn());
        this.getCommand("getHeat").setExecutor(new CommandGetHeat());
        this.getCommand("getBanTime").setExecutor(new CommandGetBanTime());
    }
}
