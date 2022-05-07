package me.stoworm.events;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import me.stoworm.Main;
import me.stoworm.utils.ChatUtils;
import me.stoworm.utils.GameState;
import me.stoworm.utils.GameUtils;
import me.stoworm.utils.SetupUtils;

public class DeadEvents implements Listener
{

    private SetupUtils setup = new SetupUtils();
    private GameUtils gameUtils = new GameUtils();

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e)
    {
        Player p = e.getPlayer();

        // This *shouldnt* happen... but yeah.
        if (gameUtils.inArray(Main.playersAlive, p.getName()))
            return;

        setup.spectatorMode(p);

        return;
    }

    @EventHandler
    public void catchJoin(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();

        if (Main.gameState == GameState.PREGAME)
            return;

        if (gameUtils.inArray(Main.playersAlive, p.getName()))
            return;

        setup.spectatorMode(p);
    }

    @EventHandler
    public void noDrop(PlayerDropItemEvent e)
    {
        Player p = e.getPlayer();

        if (gameUtils.inArray(Main.playersAlive, p.getName()))
            return;

        e.setCancelled(true);
    }

    @EventHandler
    public void onSpecTp(PlayerInteractEvent e)
    {
        Player p = e.getPlayer();

        if (!(gameUtils.inArray(Main.playersDead, p.getName())))
            return;

        if (p.getItemInHand() == null || p.getItemInHand() == new ItemStack(Material.AIR))
            return;

        if (p.getItemInHand() != new ItemStack(Material.GHAST_TEAR))
            return;

        if (Main.gameState != GameState.INGAME)
            return;

        if (e.getAction() != Action.RIGHT_CLICK_AIR || e.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        Random r = new Random();
        int chance = r.nextInt(Main.playersAlive.size() - 1);

        Player randomPlayer = Main.playersAlive.get(chance);

        p.teleport(randomPlayer.getLocation());
        p.sendMessage(ChatUtils.prefix + ChatColor.GRAY + "Teleporting to " + ChatColor.GREEN + randomPlayer.getName() + ChatColor.GRAY + "...");

        return;
    }

    @EventHandler
    public void noInteract(PlayerInteractEvent e)
    {
        Player p = e.getPlayer();

        if (gameUtils.inArray(Main.playersAlive, p.getName()))
            return;

        e.setCancelled(true);
        return;
    }

    @EventHandler
    public void noBlockPlace(BlockPlaceEvent e)
    {
        Player p = e.getPlayer();

        if (gameUtils.inArray(Main.playersAlive, p.getName()))
            return;

        e.setCancelled(true);
        return;
    }

    @EventHandler
    public void noBlockBreak(BlockBreakEvent e)
    {
        Player p = e.getPlayer();

        if (gameUtils.inArray(Main.playersAlive, p.getName()))
            return;

        e.setCancelled(true);

        return;
    }

    @EventHandler
    public void noPickup(PlayerPickupItemEvent e)
    {
        Player p = e.getPlayer();
        if (gameUtils.inArray(Main.playersAlive, p.getName()))
            return;

        e.setCancelled(true);
        return;
    }

    @EventHandler
    public void noHitting(EntityDamageByEntityEvent e)
    {
        if (!(e.getEntity() instanceof Player))
            return;

        if (!(e.getDamager() instanceof Player))
            return;

        Player d = (Player) e.getDamager();       

        if (gameUtils.inArray(Main.playersDead, d.getName()))
            e.setCancelled(true);

        return;
    }

    @EventHandler
    public void onXpTarget(EntityTargetEvent e)
    {
        if (e.isCancelled())
            return;

        if (!(e.getTarget() instanceof  Player))
            return;

        Player p = (Player) e.getTarget();

        if (!(gameUtils.inArray(Main.playersDead, p.getName())))
            return;

        if (!(e.getEntity() instanceof ExperienceOrb))
            return;

        Vector v = p.getLocation().getDirection();
        v.multiply(0.0001);
        v.normalize();
        v.setY(0.01);

        e.getEntity().setVelocity(v);
        e.setCancelled(true);
    }

}
