package me.stoworm.utils;

import me.stoworm.Main;

public class ConfigUtils 
{
    private Main plugin = null;

    public ConfigUtils()
    {
        super();
    }

    public ConfigUtils(Main plugin)
    {
        this.plugin = plugin;
    }

    public void loadConfig()
    {

        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
        
    }

    public boolean isInteger(String input)
    { 
        try
        {
            Integer.parseInt(input) ;  
        }
        catch (NumberFormatException e)
        {
            return false;
        }

        return true;
    }

    public boolean setTimer(String input)
    {
        if (!isInteger(input))
            return false;

        plugin.getConfig().set("Timer", input);
        plugin.saveConfig();

        return true;
    }

    public boolean setTimer(int input)
    {
        plugin.getConfig().set("Timer", input);
        plugin.saveConfig();

        return true;
    }
    
}
