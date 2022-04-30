package me.stoworm.utils;

import me.stoworm.Main;

public class ConfigUtils 
{
    private Main plugin = null;

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

    public boolean setConfigValue(String input, String configPath)
    {
        if (!isInteger(input))
            return false;

        plugin.getConfig().set(configPath, Integer.parseInt(input));
        plugin.saveConfig();

        return true;
    }

    public boolean setConfigValue(int input, String configPath)
    {
        plugin.getConfig().set(configPath, input);
        plugin.saveConfig();

        return true;
    }
    
    public String getConfigValue(String configPath)
    {
        return plugin.getConfig().get(configPath).toString();
    }

} 