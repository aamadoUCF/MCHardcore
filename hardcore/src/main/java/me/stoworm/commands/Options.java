package me.stoworm.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.stoworm.Main;
import me.stoworm.utils.ChatUtils;
import me.stoworm.utils.GamePhase;
import net.md_5.bungee.api.ChatColor;

public class Options implements CommandExecutor
{
    private ChatUtils chatUtils = new ChatUtils();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String [] args)
    {

        if (label.equalsIgnoreCase("options"))
        {
            if (Main.gamePhase != GamePhase.PREGAME)
            {
                sender.sendMessage(chatUtils.prefix + ChatColor.RED + "You can only use this command before the game starts.");
                return true;
            }

            if (!(sender instanceof Player))
            {
                sender.sendMessage(chatUtils.prefix + ChatColor.RED + "You must be a player to use this command.");
                return false;
            }

            Player p = (Player) sender;

            if (!(p.hasPermission("hc.admin") || p.isOp()))
            {
                p.sendMessage(chatUtils.prefix + ChatColor.RED + "You do not have permission to use this command.");
                return false;
            }

            p.openInventory(Main.optionsInv);
        }
        

        return true;
    }
    
}
