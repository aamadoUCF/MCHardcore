package me.stoworm.utils;

import me.stoworm.Main;
import me.stoworm.commands.SetBonus;
import me.stoworm.commands.SetPenalty;
import me.stoworm.commands.SetTimer;

public class SetupUtils 
{
    private Main plugin;
    
    public SetupUtils(Main plugin)
    {
        this.plugin = plugin;
    }

    public void setupCommands()
    {
        plugin.getCommand("setTimer").setExecutor(new SetTimer(plugin));
        plugin.getCommand("setBonus").setExecutor(new SetBonus(plugin));
        plugin.getCommand("setPenalty").setExecutor(new SetPenalty(plugin));
    }
}
