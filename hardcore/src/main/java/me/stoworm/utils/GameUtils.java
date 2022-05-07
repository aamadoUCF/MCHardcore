package me.stoworm.utils;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class GameUtils 
{
    public boolean inArray(ArrayList<Player> array, String playerName)
    {
        for (Player arrPlayer : array)
        {
            if (arrPlayer.getDisplayName().equalsIgnoreCase(playerName))
            {
                return true;
            }
        }

        return false;
    }    
}
