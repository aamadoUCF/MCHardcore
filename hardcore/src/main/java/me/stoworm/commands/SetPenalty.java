package me.stoworm.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.stoworm.Main;
import me.stoworm.utils.ChatUtils;
import me.stoworm.utils.GameState;

public class SetPenalty implements CommandExecutor
{
    private Main plugin;

    public SetPenalty(Main plugin)
    {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String [] args)
    {

        String timeInput;

        if (label.equalsIgnoreCase("setPenalty"))
        {
            if (Main.gameState == GameState.INGAME)
            {
                sender.sendMessage(ChatUtils.prefix + ChatColor.RED + "You cannot modify game settings while the game is running!");
                return false;
            }

            if (!(sender.isOp()) || !(sender.hasPermission("hc.admin")))
            {
                sender.sendMessage(ChatUtils.prefix + ChatColor.RED + "You do not have permission to execute this command!");
                return false;
            }

            // Tell them theyre stupid.
            if (args.length <= 0)
            {
                sender.sendMessage(ChatUtils.prefix + ChatColor.RED + "Incorrect arguments. Correct usage: /setPenalty <timeInMinutes>");
                return false;
            }

            timeInput = args[0];

            if (!(plugin.configUtils.setConfigValue(timeInput, "timer.penalty")))
            {
                sender.sendMessage(ChatUtils.prefix + ChatColor.RED + "Please enter a number.");
                return false;
            }

            sender.sendMessage(ChatUtils.prefix + ChatColor.GREEN + "Sucess! " + ChatColor.GRAY + "Timer penalty set to " + ChatColor.GREEN + 
                                timeInput + ChatColor.GRAY + " minutes.");

        }

        return true;
    }
}
