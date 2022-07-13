package io.github.randomkiddo.worldborder;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override public void onEnable() {
        this.getCommand("border").setExecutor(new BorderCommand());
        this.getCommand("borderremove").setExecutor(new BorderRemoveCommand());
    }
}
