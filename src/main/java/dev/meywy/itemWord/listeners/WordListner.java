package dev.meywy.itemWord.listeners;

import dev.meywy.itemWord.ItemWord;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

public class WordListner implements Listener {

    private ItemWord main;

    public WordListner(ItemWord main) {
        this.main = main;
    }



    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        String message = event.getMessage().toUpperCase();
        String dirtMessage = main.getConfig().getString("GetDirtMessage");
        String permission = main.getConfig().getString("Permission");
        boolean onDirt = main.getConfig().getBoolean("GetDirtOnNoExistingBlock");

        if (permission == null || !player.hasPermission(permission)) {
            return;
        }

        try {
            Material material = Material.valueOf(message);
            ItemStack item = new ItemStack(material, 1);

            player.sendMessage(
                    colorize("&7[&6WordItem&7] &7You received &a" + material.toString()));
            player.getInventory().addItem(item);

        } catch (IllegalArgumentException e) {
            if (!wrongAnswer)
            ItemStack dirt = new ItemStack(Material.DIRT, 1);
            player.sendMessage(
                    colorize("&7[&6WordItem&7] " + dirtMessage ));
            player.getInventory().addItem(dirt);
        }

    }

    private String colorize(String name) {
        return ChatColor.translateAlternateColorCodes('&', name);
    }

}
