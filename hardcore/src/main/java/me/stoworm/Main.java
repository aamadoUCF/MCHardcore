package me.stoworm;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import me.stoworm.utils.ChatUtils;
import me.stoworm.utils.ConfigUtils;
import me.stoworm.utils.GameState;
import me.stoworm.utils.SetupUtils;


public class Main extends JavaPlugin
{
    public static int timer = -1;
    public static int bonusTime = -1;
    public static int penalty = -1;

    public static GameState gameState = GameState.PREGAME;

    public Main plugin = this;

    public ConfigUtils configUtils = new ConfigUtils(this);
    public SetupUtils setupUtils = new SetupUtils(this);

    public void onEnable()
    {
        configUtils.loadConfig();
        setupUtils.setupCommands();

        Bukkit.getLogger().info(ChatUtils.prefix + ChatColor.GREEN + "has been enabled.");
    }

    public void onDisable()
    {
        Bukkit.getLogger().info(ChatUtils.prefix + ChatColor.RED + "has been disabled.");
    }

}