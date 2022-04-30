package me.stoworm.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.stoworm.Main;
import me.stoworm.utils.ChatUtils;
import me.stoworm.utils.GameState;

public class StartTimer implements CommandExecutor 
{
    private Main plugin;
    boolean doneFlag = false;
    int task;

    public StartTimer(Main plugin)
    {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String [] args)
    {
        if (label.equalsIgnoreCase("startTimer"))
        {
            if (Main.gameState == GameState.INGAME || Main.gameState == GameState.POSTGAME)
            {
                sender.sendMessage(ChatUtils.prefix + ChatColor.RED + "You cannot start the game right now.");
                return false;
            }

            if (!(sender.isOp()) || !(sender.hasPermission("hc.admin")))
            {
                sender.sendMessage(ChatUtils.prefix + ChatColor.RED + "You do not have permission to execute this command!");
                return false;
            }

            // Set the timer values.
            Main.timer = Integer.parseInt(plugin.configUtils.getConfigValue("timer.length"));
            Main.penalty = Integer.parseInt(plugin.configUtils.getConfigValue("timer.penalty"));
            Main.bonusTime = Integer.parseInt(plugin.configUtils.getConfigValue("timer.bonus"));

            // Convert Minutes to Seconds.
            Main.timer *= 60;

            Main.gameState = GameState.INGAME;

            Bukkit.broadcastMessage(ChatUtils.prefix + ChatColor.GOLD + "Give it up for our contestants: ");
            // Add all players to alive list.
            for (Player p : plugin.getServer().getOnlinePlayers())
            {
                Main.playersAlive.add(p);

                sender.sendMessage(ChatColor.GRAY + "- " + ChatColor.GOLD + p.getDisplayName());
            }

            
            Bukkit.broadcastMessage(ChatUtils.prefix + ChatColor.GREEN + "The game has begun! Good luck.");
            Bukkit.broadcastMessage(ChatColor.GRAY + "There are " + ChatColor.GREEN + (Main.timer / 60 ) + ChatColor.GRAY + " minutes on the clock.");


            // Timer loop
            task = plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {

                @Override
                public void run()
                {
                    if (doneFlag)
                        plugin.getServer().getScheduler().cancelTask(task);

                    Main.timer -= 1;

                    // Announce timer updates.

                    // This invokes every hour.
                    if ((Main.timer % 3600) == 0 && Main.timer != 0)
                    {
                        Bukkit.broadcastMessage(ChatUtils.prefix + ChatColor.GRAY + "There are " + ChatColor.GREEN + (Main.timer / 3600) + 
                        ChatColor.GRAY + " hours remaining.");
                    }

                    // This invokes every 15 mins as long as there is under 1 hr left.
                    if ((Main.timer % 900) == 0 && (Main.timer < 3600) && Main.timer != 0)
                    {
                        Bukkit.broadcastMessage(ChatUtils.prefix + ChatColor.GRAY + "There are " + ChatColor.YELLOW + (Main.timer / 60) + 
                        ChatColor.GRAY + " minutes remaining.");
                    }

                    if (Main.timer == 600 || Main.timer == 300)
                    {
                        Bukkit.broadcastMessage(ChatUtils.prefix + ChatColor.GRAY + "There are " + ChatColor.RED + (Main.timer / 60) + 
                        ChatColor.GRAY + " minutes remaining.");
                    }

                    if (Main.timer == 60 || Main.timer == 30 || Main.timer == 15)
                    {
                        Bukkit.broadcastMessage(ChatUtils.prefix + ChatColor.GRAY + "There are " + ChatColor.DARK_RED + (Main.timer) + 
                        ChatColor.GRAY + " seconds remaining.");
                    }

                    if (Main.timer <= 10 && Main.timer >= 0)
                    {
                        Bukkit.broadcastMessage(ChatUtils.prefix + ChatColor.GRAY + ChatColor.MAGIC + "ASDAS The cftgvhb" + ChatColor.DARK_RED + ChatColor.BOLD + (Main.timer) + 
                        ChatColor.GRAY + " SECOND" + ((Main.timer == 1) ? "" : "S") + " LEFT!");
                    }

                    // Kill all the alive players muhahaha
                    if (Main.timer == 0)
                    {
                        doneFlag = true;

                        for (Player p : Main.playersAlive)
                            p.damage(999999);

                        Bukkit.broadcastMessage(ChatUtils.prefix + ChatColor.DARK_PURPLE + "You all have succumbed to defeat.");

                        Main.gameState = GameState.PREGAME;
                    }
                }

            }, 0, 20L*1L);

        }

        return false;
    }
}
