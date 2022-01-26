package me.stoworm.events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import me.stoworm.Main;
import me.stoworm.utils.ChatUtils;
import me.stoworm.utils.ConfigUtils;
import me.stoworm.utils.GamePhase;
import me.stoworm.utils.InventoryUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.md_5.bungee.api.ChatColor;

public class Events implements Listener
{

    public Main plugin;

    public Events(Main plugin)
    {
        this.plugin = plugin;
    }

    InventoryUtils invUtils = new InventoryUtils();
    ChatUtils chatUtils = new ChatUtils();
    ConfigUtils configUtils = new ConfigUtils();

    @EventHandler
    public void onBonusDeath(PlayerDeathEvent e)
    {
        Player p = e.getPlayer();

        if (Main.gamePhase != GamePhase.INGAME)
            return;

        if (p.getGameMode() != GameMode.SURVIVAL)
            return;

        Bukkit.broadcast(Component.text(chatUtils.prefix + ChatColor.RED + ChatColor.ITALIC + p.getName() + ChatColor.GRAY + " has died. Adding " + 
                                        ChatColor.RED + ChatColor.ITALIC + Main.bonusInMinutes + " minutes " + ChatColor.GRAY + "to the timer."));

        Main.timerInMinutes += Main.bonusInMinutes;

        Bukkit.broadcast(Component.text(ChatColor.GRAY + "There are now " + ChatColor.RED + ChatColor.ITALIC + Main.timerInMinutes + " minutes " +
                        ChatColor.GRAY + "remaining."));
        
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        InventoryView inv = e.getView();

        if (!(e.getWhoClicked() instanceof Player))
            return;
        
        if (!(inv.title().equals(Component.text(ChatColor.DARK_BLUE + "Click an option to change value"))))
            return;

        Player p = (Player) e.getWhoClicked();

        e.setCancelled(true);

        if (e.getCurrentItem() == null || e.getCurrentItem().getItemFlags() == null || e.getCurrentItem().getItemMeta().displayName() == null)
            return;

        ItemStack currentItem = e.getCurrentItem();

        if (currentItem.getItemMeta().displayName().equals(Component.text(ChatColor.GOLD + "Timer")))
        {
            p.closeInventory();

            p.setGameMode(GameMode.CREATIVE);

            // Index 0 is the Timer Input
            invUtils.addItem(Main.inputInv.get(0), Material.BOOK, String.valueOf(Main.timerInMinutes), null, null, 0);
            p.openInventory(Main.inputInv.get(0));
        }

    }

    @EventHandler
    public void onAnvilInput(PrepareAnvilEvent e)
    {
        HumanEntity p = e.getView().getPlayer();

        p.sendMessage("Triggered");

        if (!(e.getView().title().equals(Component.text(ChatColor.DARK_BLUE + "Change Timer Value"))))
        {
            ItemStack result = e.getResult();

            int input = Integer.parseInt(PlainTextComponentSerializer.plainText().serialize(result.displayName()));
        
            Main.timerInMinutes = input;

            p.closeInventory();

            if (p.getGameMode() == GameMode.CREATIVE)
                p.setGameMode(GameMode.SURVIVAL);

            configUtils.setTimer(input);        

            p.sendMessage(Component.text(chatUtils.prefix + ChatColor.GRAY + "Set timer to " + ChatColor.GREEN + ChatColor.ITALIC + input));
        }
    }

    // @EventHandler
    // public void onInputInventoryClick(InventoryClickEvent e)
    // {
    //     InventoryView inv = e.getView();

    //     if (!(e.getWhoClicked() instanceof Player))
    //         return;
        
    //     if (!(inv.title().equals(Component.text(ChatColor.DARK_BLUE + "Change Timer Value"))))
    //         return;

    //     Player p = (Player) e.getWhoClicked();

    //     if (e.getCurrentItem().getItemFlags() == null || e.getCurrentItem().getItemMeta().displayName() == null)
    //     return;
        
    //     p.sendMessage(e.getSlot()  +"");
        
    //     if (e.getSlot() != 2)
    //         return;

    //     ItemStack currentItem = e.getCurrentItem();

    //     int input = Integer.parseInt(PlainTextComponentSerializer.plainText().serialize(currentItem.displayName()));
        
    //     Main.timerInMinutes = input;

    //     p.closeInventory();

    //     if (p.getGameMode() == GameMode.CREATIVE)
    //         p.setGameMode(GameMode.SURVIVAL);

    //     configUtils.setTimer(input);        

    //     p.sendMessage(Component.text(chatUtils.prefix + ChatColor.GRAY + "Set timer to " + ChatColor.GREEN + ChatColor.ITALIC + input));
    // }


}
