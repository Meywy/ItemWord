package dev.meywy.itemWord.util;

import org.bukkit.ChatColor;

public class Message {

    public static String colorize(String name) {
        return ChatColor.translateAlternateColorCodes('&', name);
    }

    private final String prefix = colorize("&7[&bItemWord&7]&f ");

    public String getPrefix() {
        return prefix;
    }
}
