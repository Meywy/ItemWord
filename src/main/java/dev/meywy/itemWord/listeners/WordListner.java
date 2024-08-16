package dev.meywy.itemWord.listeners;

import dev.meywy.itemWord.ItemWord;
import dev.meywy.itemWord.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import static dev.meywy.itemWord.util.Message.colorize;

public class WordListner implements Listener {

    private ItemWord main;

    public WordListner(ItemWord main) {
        this.main = main;
    }

    public Message Message = new Message();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        String message = event.getMessage().toUpperCase();

        String permissionOne = main.getConfig().getString("permissions.admin");
        String permissionTwo = main.getConfig().getString("permissions.receive");

        if (permissionOne == null || permissionTwo == null) {
            Bukkit.getLogger().info("Permissions could not be loaded");
            return;
        }

        boolean hasPermission = player.hasPermission(permissionOne) && player.hasPermission(permissionTwo);
        if (!hasPermission) { return; }

        int itemAmount = main.getConfig().getInt("item.amount");
        String itemMessage = main.getConfig().getString("item.message");

        boolean noExistingItem = main.getConfig().getBoolean("no-existing-item.enable");
        String noExistingItemType = main.getConfig().getString("no-existing-item.type").toUpperCase();
        int noExistingItemAmount = main.getConfig().getInt("no-existing-item-amount.item-amount");
        String noExistingItemMessage = main.getConfig().getString("no-existing-item-message");

        try {
            event.setCancelled(true);
            Material material = Material.valueOf(message);
            ItemStack item = new ItemStack(material, itemAmount);

            player.sendMessage(
                    (Message.getPrefix()) + colorize(itemMessage + " " + material.toString()));
            player.getInventory().addItem(item);

        } catch (IllegalArgumentException e) {
            event.setCancelled(false);
            if (!noExistingItem) { return; }
            try {
                Material material = Material.valueOf(noExistingItemType);
                ItemStack item = new ItemStack(material, noExistingItemAmount);
                player.sendMessage(
                        (Message.getPrefix()) + colorize(noExistingItemMessage +  " " + material.toString()));
                player.getInventory().addItem(item);
            } catch (IllegalArgumentException err) {
                JavaPlugin.getPlugin(ItemWord.class).getLogger().warning("ERROR:" + err.getMessage());
            }
        }

    }

}
