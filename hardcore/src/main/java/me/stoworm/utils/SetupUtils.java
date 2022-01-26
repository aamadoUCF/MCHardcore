package me.stoworm.utils;

import me.stoworm.Main;
import me.stoworm.commands.Options;
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
        plugin.getCommand("options").setExecutor(new Options());
        //plugin.getCommand("start").setExecutor(new Start());
        plugin.getCommand("setTimer").setExecutor(new SetTimer());
    }    
}
