package me.stoworm;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import me.stoworm.events.Events;
import me.stoworm.utils.ChatUtils;
import me.stoworm.utils.ConfigUtils;
import me.stoworm.utils.GamePhase;
import me.stoworm.utils.InventoryUtils;
import me.stoworm.utils.SetupUtils;
import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin
{

    private ConfigUtils configUtils = new ConfigUtils(this);
    private SetupUtils setupUtils = new SetupUtils(this);
    private ChatUtils chatUtils = new ChatUtils();
    private InventoryUtils invUtils = new InventoryUtils();

    public static GamePhase gamePhase = GamePhase.PREGAME;

    public static ArrayList<Player> alivePlayers;

    public static int timerInMinutes = -1;
    public static int bonusInMinutes = -1;

    public static Inventory optionsInv;
    public static ArrayList<Inventory> inputInv;

    public void onEnable()
    {
        // Register Events in Events class.
        getServer().getPluginManager().registerEvents(new Events(this), this);

        configUtils.loadConfig();
        setupUtils.setupCommands();

        timerInMinutes = this.getConfig().getInt("timer");
        bonusInMinutes = this.getConfig().getInt("bonus.time");

        optionsInv = invUtils.createOptionsGUI();
        inputInv = invUtils.createInputInventory();


        Bukkit.getLogger().info(chatUtils.prefix + ChatColor.GREEN + "has been enabled.");
    }

    public void onDisable()
    {
        Bukkit.getLogger().info(chatUtils.prefix + ChatColor.RED + "has been disabled.");
    }

}