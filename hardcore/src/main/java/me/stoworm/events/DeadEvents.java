package me.stoworm.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import me.stoworm.Main;
import me.stoworm.utils.SetupUtils;

public class DeadEvents implements Listener
{

    private SetupUtils setup = new SetupUtils();

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e)
    {
        Player p = e.getPlayer();

        // This *shouldnt* happen... but yeah.
        if (Main.playersAlive.contains(p))
            return;

        setup.spectatorMode(p);

        return;
    }

    @EventHandler
    public void catchJoin(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();

        if (Main.playersAlive.contains(p))
            return;

        setup.spectatorMode(p);
    }

    @EventHandler
    public void noDrop(PlayerDropItemEvent e)
    {
        Player p = e.getPlayer();

        if (Main.playersAlive.contains(p))
            return;

        e.setCancelled(true);
    }

    @EventHandler
    public void noInteract(PlayerInteractEvent e)
    {
        Player p = e.getPlayer();

        if (Main.playersAlive.contains(p))
            return;

        e.setCancelled(true);
        return;
    }

    @EventHandler
    public void noBlockPlace(BlockPlaceEvent e)
    {
        Player p = e.getPlayer();

        if (Main.playersAlive.contains(p))
            return;

        e.setCancelled(true);
        return;
    }

    @EventHandler
    public void noBlockBreak(BlockBreakEvent e)
    {
        Player p = e.getPlayer();

        if (Main.playersAlive.contains(p))
            return;

        e.setCancelled(true);
        return;
    }

    @EventHandler
    public void noPickup(PlayerPickupItemEvent e)
    {
        Player p = e.getPlayer();
        if (Main.playersAlive.contains(p))
            return;

        e.setCancelled(true);
        return;
    }

    @EventHandler
    public void noHitting(EntityDamageByEntityEvent e)
    {
        if (!(e.getEntity() instanceof Player))
            if (!(e.getDamager() instanceof Player))
                return;

        Player p = (Player) e.getEntity();
        Player d = (Player) e.getDamager();

        Bukkit.broadcastMessage("p: " + p.getName());
        Bukkit.broadcastMessage("d: " + d.getName());
        


        if (Main.playersDead.contains(d))
        {
            Bukkit.broadcastMessage("d is dead");
            e.setCancelled(true);
        }

        return;
    }
}
