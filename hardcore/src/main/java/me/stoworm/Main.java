package me.stoworm;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import me.stoworm.utils.ChatUtils;


public class Main extends JavaPlugin
{

    public void onEnable()
    {
        Bukkit.getLogger().info(ChatUtils.prefix + ChatColor.GREEN + "has been enabled.");
    }

    public void onDisable()
    {
        Bukkit.getLogger().info(ChatUtils.prefix + ChatColor.RED + "has been disabled.");
    }

}