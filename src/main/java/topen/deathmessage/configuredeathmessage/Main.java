package topen.deathmessage.configuredeathmessage;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public final class Main extends JavaPlugin {

    public static File getDataFolder;

    @Override
    public void onEnable() {
        getDataFolder = getDataFolder();
        getCommand("topen").setExecutor(new Command());
        getCommand("topen").setTabCompleter(new TabCompleter());
        Bukkit.getPluginManager().registerEvents(new Listener(), this);
        try {
            Configure.setup();
            Listener.setup();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Plugin Topen has successfully been enabled and loaded!");
    }

    @Override
    public void onDisable() {
        try {
            Configure.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Plugin Topen has successfully been disabled!");
    }
}
