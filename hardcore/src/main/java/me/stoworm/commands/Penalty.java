package me.stoworm.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.stoworm.Main;
import me.stoworm.utils.ChatUtils;
import me.stoworm.utils.GameState;

public class Penalty implements CommandExecutor
{
    private Main plugin;

    public Penalty(Main plugin)
    {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String [] args)
    {
        if (label.equalsIgnoreCase("penalty"))
        {
            
            if (!(sender.isOp()) || !(sender.hasPermission("hc.admin")))
            {
                sender.sendMessage(ChatUtils.prefix + ChatColor.RED + "You do not have permission to execute this command!");
                return false;
            }

            if (Main.gameState != GameState.INGAME)
            {
                sender.sendMessage(ChatUtils.prefix + ChatColor.RED + "You can only use this command in-game.");

                Bukkit.broadcastMessage(ChatUtils.prefix);

                return false;
            }

            Main.timer -= Main.penalty;

        }

        return true;
    }   
}
