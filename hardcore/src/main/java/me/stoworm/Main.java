package me.stoworm;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
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

    public static ArrayList<Player> playersAlive = new ArrayList<>();
    public static ArrayList<Player> playersDead = new ArrayList<>();
    public static ArrayList<Player> playersSafe = new ArrayList<>();

    public static boolean overtime = false;

    public void onEnable()
    {
        configUtils.loadConfig();

        setupUtils.setupCommands();
        setupUtils.setupEvents();

        Bukkit.getLogger().info(ChatUtils.prefix + ChatColor.GREEN + "has been enabled.");
    }

    public void onDisable()
    {
        Bukkit.getLogger().info(ChatUtils.prefix + ChatColor.RED + "has been disabled.");
    }

    // TODO
    // CUSTOM DEATH MESSAGE (Stored in config (?))
    // Stats (% Dmg from dragon, etc)
    // Cain timer announce command
    // Penalty command

}