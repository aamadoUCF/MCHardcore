package me.stoworm.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.stoworm.Main;
import me.stoworm.utils.ChatUtils;
import me.stoworm.utils.ConfigUtils;
import me.stoworm.utils.GamePhase;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;

public class SetTimer implements CommandExecutor
{

    private ConfigUtils configUtils = new ConfigUtils();
    private ChatUtils chatUtils = new ChatUtils();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String [] args)
    {
        String timeInput;

        if (label.equalsIgnoreCase("setTimer"))
        {
            if (sender.hasPermission("hc.admin") || sender.isOp())
            {

                // Timer should only be modified during PREGAME
                if (!(Main.gamePhase == GamePhase.PREGAME))
                {
                    sender.sendMessage(Component.text());
                    return false;
                }

                // Tell them they're dumb.
                if (args.length <= 0)
                {
                    sender.sendMessage(Component.text(chatUtils.prefix + ChatColor.RED + "Invalid arguments. Correct usage: /setTime <timeInMinutes>"));
                    return false;
                }

                timeInput = args[0];

                configUtils.setTimer(timeInput);

                sender.sendMessage(Component.text(chatUtils.prefix + ChatColor.GRAY + "Timer set to " +
                                                ChatColor.GREEN + ChatColor.ITALIC + timeInput + ChatColor.GRAY + " minutes."));
            }     
        }

        return true;
    }
    
}
