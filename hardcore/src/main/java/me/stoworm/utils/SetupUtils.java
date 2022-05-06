package me.stoworm.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.stoworm.Main;
import me.stoworm.commands.Options;
import me.stoworm.commands.Penalty;
import me.stoworm.commands.SetBonus;
import me.stoworm.commands.SetPenalty;
import me.stoworm.commands.SetTimer;
import me.stoworm.commands.StartTimer;
import me.stoworm.events.DeadEvents;
import me.stoworm.events.GameEvents;

public class SetupUtils 
{
    private Main plugin;
    
    public SetupUtils()
    {
        super();
    }

    public SetupUtils(Main plugin)
    {
        this.plugin = plugin;
    }

    public void setupCommands()
    {
        plugin.getCommand("setTimer").setExecutor(new SetTimer(plugin));
        plugin.getCommand("setBonus").setExecutor(new SetBonus(plugin));
        plugin.getCommand("setPenalty").setExecutor(new SetPenalty(plugin));
        plugin.getCommand("options").setExecutor(new Options(plugin));
        plugin.getCommand("startTimer").setExecutor(new StartTimer(plugin));
        plugin.getCommand("penalty").setExecutor(new Penalty(plugin));

        Bukkit.getLogger().info(ChatUtils.prefix + ChatColor.GREEN + "Loaded commands.");
    
        return;
    }

    public void setupEvents()
    {
        plugin.getServer().getPluginManager().registerEvents(new GameEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new DeadEvents(), plugin);
        
        Bukkit.getLogger().info(ChatUtils.prefix + ChatColor.GREEN + "Loaded events.");

        return;
    }

    public void spectatorMode(Player p)
    {
        if (Main.gameState != GameState.INGAME || Main.gameState == GameState.POSTGAME)
            return;

        p.setGameMode(GameMode.CREATIVE);

        hideDeadPlayer(p);

        p.sendMessage(ChatUtils.prefix + ChatColor.GRAY + "Welcome to 'spectator' mode. Alive players cannot see you right now. You are in creative mode, " +
        "but you cannot interact with the world. Right click the ghast tear to bring up a menu to teleport to current alive players.");

        p.getInventory().clear();

        ItemStack tear = new ItemStack(Material.GHAST_TEAR);

        p.getInventory().setItem(4, tear);

        return;
    }

    public void hideDeadPlayer(Player dead)
    {
        
        for (Player p : Main.playersAlive)
            p.hidePlayer(dead);

        return;
    }
}
