package dev.meywy.itemWord.commands;

import dev.meywy.itemWord.ItemWord;
import dev.meywy.itemWord.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class IWordCMD implements CommandExecutor, TabCompleter {

    private final ItemWord main;
    public IWordCMD(ItemWord main) {
        this.main = main;
    }

    public Message message = new Message();

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 1) { return StringUtil.copyPartialMatches(args[0], Arrays.asList("setamount"), new ArrayList<>()); }
        return new ArrayList<>();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage(message.getPrefix() + Message.colorize("&cYou need to be player to do that!"));
            return true;
        };

        String permissionOne = main.getConfig().getString("permissions.admin");
        String permissionTwo = main.getConfig().getString("permissions.receive");
        if (permissionOne == null || permissionTwo == null) {
            Bukkit.getLogger().info("Permissions could not be loaded");
            return true;
        }

        boolean hasPermission = player.hasPermission(permissionOne) && player.hasPermission(permissionTwo);
        if (!hasPermission) { return true; }

        if (args.length != 2) {
            player.sendMessage(Message.colorize(
                    "        &7(&d!&7) " + "&bItemWord" + " &7(&d!&7)" +
                            "\n&f❄ iword&7 - Shows help menu." +
                            "\n&f❄ iword setamount <number>&7 - Set custom amount of items given to player."
            ));
            return true;
        }

        try {
            int amount = Integer.parseInt(args[1]);
            player.sendMessage(Message.colorize(
                    "        &7(&d!&7) " + "&bItemWord SetAmount" + " &7(&d!&7)" +
                            "\n&f❄ &7New amount of items successfully set to &b" + amount + "&7."
            ));
            main.getConfig().set("item.amount", amount);
            main.saveConfig();
        } catch (NumberFormatException exc) {
            player.sendMessage(Message.colorize(
                    "        &7(&d!&7) " + "&bItemWord SetAmount" + " &7(&d!&7)" +
                            "\n&f❄ setamount&7 - Shows help menu for setamount." +
                            "\n&f❄ setamount <number>&7 - Set custom amount of items given to player."
            ));
        }



        return true;

    }
}
