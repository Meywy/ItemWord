package dev.meywy.itemWord;

import dev.meywy.itemWord.commands.IWordCMD;
import dev.meywy.itemWord.listeners.WordListner;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ItemWord extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        Bukkit.getPluginManager().registerEvents(new WordListner(this), this);

        getCommand("iword").setExecutor(new IWordCMD(this));
        getCommand("iword").setTabCompleter(new IWordCMD(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
