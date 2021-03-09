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
        this.getCommand("warn").setExecutor(new CommandWarn());
        this.getCommand("getWarnTypes").setExecutor(new CommandGetWarnTypes());
        this.getCommand("editWarn").setExecutor(new CommandEditWarn());
        this.getCommand("deleteWarn").setExecutor(new CommandDeleteWarn());
        Database.editWarn(1, "player", "KLJLJL");
    }
}
