package me.stoworm.utils;

import org.bukkit.ChatColor;

public class ChatUtils 
{

    public static String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.RED + "HC" + ChatColor.DARK_GRAY + "] " + ChatColor.RESET;

    public static String inGameError = prefix + ChatUtils.prefix + ChatColor.RED + "You can only use this command while the game is running.";
    
}
