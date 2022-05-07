package me.stoworm.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.stoworm.Main;
import me.stoworm.utils.ChatUtils;
import me.stoworm.utils.GameState;
import me.stoworm.utils.GameUtils;

public class Spectate implements CommandExecutor
{
    GameUtils gameUtils = new GameUtils();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String [] args)
    {
        if (label.equalsIgnoreCase("spectate") || label.equalsIgnoreCase("spec"))
        {
            if (!(sender instanceof Player))
            {
                sender.sendMessage(ChatUtils.prefix + "You must be a player to perform this command. (stinky yeen)");
                return false;
            }
    
            Player p = (Player) sender;
    
            if (Main.gameState != GameState.INGAME)
            {
                p.sendMessage(ChatUtils.inGameError);
                return false;
            }
    
            
            if (!(gameUtils.inArray(Main.playersDead, p.getName())))
            {
                p.sendMessage(ChatUtils.prefix + ChatColor.RED + "Only dead players may use this command!");
                return false;
            }
    
            // Tell them theyre stupid.
            if (args.length <= 0 || args.length >= 2)
            {
                sender.sendMessage(ChatUtils.prefix + ChatColor.RED + "Incorrect arguments. Correct usage: /spec <alivePlayer>");
                return false;
            }
            
            for (Player alive : Main.playersAlive)
            {
                if (alive.getName().toLowerCase().contains(args[1].toLowerCase()))
                {
                    p.teleport(alive.getLocation());
                    p.sendMessage(ChatUtils.prefix + ChatColor.GRAY + "Teleporting you to " + ChatColor.GOLD + alive.getName() + ChatColor.GRAY + "...");
                    return true;
                }
            }

            p.sendMessage(ChatUtils.prefix + ChatColor.RED + "That player must be online and alive.");
            return false;

        }
        return true;
    }
}
