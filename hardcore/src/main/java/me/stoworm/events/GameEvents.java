package me.stoworm.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import me.stoworm.Main;
import me.stoworm.utils.ChatUtils;
import me.stoworm.utils.GameState;
import me.stoworm.utils.GameUtils;

public class GameEvents implements Listener
{
    GameUtils gameUtils = new GameUtils();

    @EventHandler
    public void RemoveAliveStatus(PlayerDeathEvent e)
    {
        Player p = e.getEntity();
        Location ploc = p.getLocation();

        if (Main.gameState == GameState.INGAME)
        {
            // Remove them from alive players array
            if (gameUtils.inArray(Main.playersAlive, p.getName()))
            {
                Main.playersAlive.remove(p);
            }

            if (!(gameUtils.inArray(Main.playersDead, p.getName())))
            {
                Main.playersDead.add(p);
            }

            // Give everyone something to remember the fallen
            p.getWorld().strikeLightningEffect(ploc);


            if (Main.playersAlive.size() > 0)
            {
                // Add time to the timer :)
                Main.timer += Main.bonusTime;

                Bukkit.broadcastMessage(ChatUtils.prefix + ChatColor.RED + p.getDisplayName() + ChatColor.GRAY + " has fallen. " + ChatColor.RED + Main.playersAlive.size() +
                ChatColor.GRAY + " player" + ((Main.playersAlive.size() > 1) ? "s" : "") + " remaining." + 
                ((Main.playersAlive.size() > 1) ? ".." + ChatColor.RED : " Good luck... you'll need it."));

                int h = Main.timer / 3600;
                int m = h % 60;
                int s = Main.timer % 60;

                Bukkit.broadcastMessage(ChatColor.GREEN + Integer.toString(Main.bonusTime) + " minutes!");

                Bukkit.broadcastMessage(ChatUtils.prefix + ChatColor.GRAY + "Time Remaining: " + ChatColor.GREEN + h + "h " + m + "m " + s + "s");
                return;
            }

            if (Main.playersAlive.size() == 0)
            {
                Main.timer = 0;
                Bukkit.broadcastMessage(ChatUtils.prefix + ChatColor.RED + p.getDisplayName() + ChatColor.GRAY + " has fallen. " + ChatColor.RED + Main.playersAlive.size() +
                ChatColor.GRAY + " players remain.");

                if (Main.overtime)
                {
                    Bukkit.broadcastMessage(ChatUtils.prefix + ChatColor.DARK_PURPLE + ChatColor.ITALIC + "Your valiant effort was not enough. Game over.");

                    Main.gameState = GameState.PREGAME;
                }
            }
        }

        return;
    }

    @EventHandler
    public void noDamage(EntityDamageEvent e)
    {
        if (!(e.getEntity() instanceof Player))
            return;

        if (Main.gameState == GameState.PREGAME)
            e.setCancelled(true);
        
    }

    @EventHandler
    public void clearOnJoin(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();

        if (Main.gameState == GameState.INGAME)
            return;

        // Housekeeping stuff.
        p.getInventory().clear();
        p.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Welcome...");
        e.setJoinMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "+" + ChatColor.DARK_GRAY + "] " + ChatColor.GREEN + p.getDisplayName());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e)
    {
        Player p = e.getPlayer();

        e.setQuitMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "-" + ChatColor.DARK_GRAY + "] " + ChatColor.RED + p.getDisplayName());
    }

    @EventHandler
    public void onTeleportSafe(PlayerPortalEvent e)
    {
        Player p = e.getPlayer();

        if (e.getCause() == TeleportCause.END_PORTAL)
            Main.playersSafe.add(p);
        
        p.sendMessage(ChatUtils.prefix + ChatColor.GREEN + "You made it to the End. (somehow)");
    }
}
