package me.stoworm.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.stoworm.Main;
import me.stoworm.utils.ChatUtils;

public class Options implements CommandExecutor
{
    private Main plugin;

    public Options(Main plugin)
    {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String [] args)
    {
        if (label.equalsIgnoreCase("Options"))
        {
            
            sender.sendMessage(ChatColor.GRAY + "-=-=-=-=-=" + ChatUtils.prefix + ChatColor.GRAY + "-=-=-=-=-=-");
            sender.sendMessage(ChatColor.GREEN + "Timer");
            sender.sendMessage(ChatColor.GRAY + "- " + ChatColor.GREEN + "Length" + ChatColor.GRAY + ": " + ChatColor.RESET + plugin.configUtils.getConfigValue("timer.length"));
            sender.sendMessage(ChatColor.GRAY + "- " + ChatColor.GREEN + "Bonus" + ChatColor.GRAY + ": " + ChatColor.RESET + plugin.configUtils.getConfigValue("timer.bonus"));
            sender.sendMessage(ChatColor.GRAY + "- " + ChatColor.GREEN + "Penalty" + ChatColor.GRAY + ": " + ChatColor.RESET + plugin.configUtils.getConfigValue("timer.penalty"));
            sender.sendMessage("");
            sender.sendMessage(ChatColor.GOLD + "Time Remaining" + ChatColor.GRAY + ": " + ChatColor.RESET + Main.timer);
            sender.sendMessage(ChatColor.GOLD + "Game State" + ChatColor.GRAY + ": " + ChatColor.RED + Main.gameState);
            sender.sendMessage(ChatColor.GRAY + "-=-=-=-=-=-=-=-=-=-=-=-=-");

        }

        return true;
    }   
}
