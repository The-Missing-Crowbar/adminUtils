package com.themissingcrowbar.adminUtils;

import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {
    @Override
    public void onDisable() {
        getLogger().info("Initializing AdminUtils");
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }
}
