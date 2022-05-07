package me.stoworm.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.stoworm.Main;
import me.stoworm.utils.ChatUtils;
import me.stoworm.utils.GameState;

public class Broadcast implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command cmd, String label, String [] args)
    {
        if (label.equalsIgnoreCase("broadcast") || label.equalsIgnoreCase("bc"))
        {
            
            if (!(sender.isOp()) || !(sender.hasPermission("hc.admin")))
            {
                sender.sendMessage(ChatUtils.prefix + ChatColor.RED + "You do not have permission to execute this command!");
                return false;
            }

            if (Main.gameState != GameState.INGAME)
            {
                sender.sendMessage(ChatUtils.prefix + ChatColor.RED + "You can only use this command while the timer is running.");

                Bukkit.broadcastMessage(ChatUtils.prefix);

                return false;
            }

            int h = Main.timer / 3600;
            int m = h % 60;
            int s = Main.timer % 60;

            Bukkit.broadcastMessage(ChatUtils.prefix + ChatColor.GRAY + "Time Remaining: " + ChatColor.GREEN + h + "h " + m + "m " + s + "s");

        }

        return true;
    }   
}
