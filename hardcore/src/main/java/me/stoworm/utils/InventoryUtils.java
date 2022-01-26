package me.stoworm.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.stoworm.Main;
import net.kyori.adventure.text.Component;

public class InventoryUtils 
{

    public Inventory createOptionsGUI()
    {
        Inventory inv = Bukkit.createInventory(null, 45, Component.text(ChatColor.DARK_BLUE + "Click an option to change value"));

        fillGlass(inv);

        addItem(inv, Material.ENDER_EYE, "Game Options", ChatColor.AQUA, null, 4);
        ItemStack clock = createItem(Material.CLOCK, "Timer", ChatColor.GOLD, "Duration of the match");
        ItemStack bonus = createItem(Material.CAKE, "Bonus Time per Death", ChatColor.GOLD, "Amount of time (in minutes) that is added for every death that occurs in-game.");

        addLoreLine(clock, "Current Timer: " + Main.timerInMinutes, ChatColor.GREEN);

        addLoreLine(bonus, "Enabled", ChatColor.GREEN);
        addLoreLine(bonus, "Current Bonus: " + Main.bonusInMinutes, ChatColor.GREEN);

        addItem(clock, inv, 10);
        addItem(bonus, inv, 28);

        return inv;
    }
    
    public ArrayList<Inventory> createInputInventory()
    {
        Inventory inv = Bukkit.createInventory(null, InventoryType.ANVIL, Component.text(ChatColor.DARK_BLUE + "Change Timer Value"));

        Inventory invBonus = Bukkit.createInventory(null, InventoryType.ANVIL, Component.text(ChatColor.DARK_BLUE + "Change Bonus Value"));

        ArrayList<Inventory> invs = new ArrayList<>();

        invs.add(inv);
        invs.add(invBonus);

        return invs;
    }

    public ItemStack createItem(Material itemMaterial, String displayName, ChatColor color, String lore)
    {
        ItemStack itemToAdd = new ItemStack(itemMaterial);
        ArrayList<Component> loreArray;

        // Convert string to lore.
        if (lore == null)        
            loreArray = new ArrayList<>();
        else
            loreArray = stringToLore(lore);

        ItemMeta itemMeta = itemToAdd.getItemMeta();
        itemMeta.displayName(Component.text(color + displayName));

        itemMeta.lore(loreArray);
        
        itemToAdd.setItemMeta(itemMeta);

        return itemToAdd;
    }

    public void fillGlass(Inventory inv)
    {
        for (int i = 0; i < inv.getSize(); i++)
        {
            if (inv.getItem(i) == new ItemStack(Material.AIR) || inv.getItem(i) == null)
            {
                inv.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));                            
            }
        }
    }

    public ItemStack addLoreLine(ItemStack item, String lineToAdd, ChatColor color)
    {
        ItemMeta itemMeta = item.getItemMeta();

        List<Component> lore = itemMeta.lore();

        lore.add(Component.text(color + lineToAdd));

        itemMeta.lore(lore);
        item.setItemMeta(itemMeta);

        return item;
    }

    public ItemStack removeLastLoreLine(ItemStack item)
    {
        ItemMeta itemMeta = item.getItemMeta();

        List<Component> lore = itemMeta.lore();

        lore.remove(lore.size() - 1);

        return item;
    }

    public void addItem(ItemStack item, Inventory inv, int slot)
    {
        inv.setItem(slot, item);

        return;
    }

    public ItemStack addItem(Inventory inv, Material itemMaterial, String displayName, ChatColor color, String lore, int slot)
    {

        ItemStack itemToAdd = new ItemStack(itemMaterial);
        ArrayList<Component> loreArray;

        // Convert string to lore.
        if (lore == null)        
            loreArray = new ArrayList<>();
        else
            loreArray = stringToLore(lore);

        ItemMeta itemMeta = itemToAdd.getItemMeta();

        if (color == null)
            itemMeta.displayName(Component.text(displayName));
        else
            itemMeta.displayName(Component.text(color + displayName));

        itemMeta.lore(loreArray);
        
        itemToAdd.setItemMeta(itemMeta);

        inv.setItem(slot, itemToAdd);

        return itemToAdd;

    }

    public ArrayList<Component> stringToLore(String lore)
    {
        String [] splitString = lore.split(" ");
        
        ArrayList<Component> componentArray = new ArrayList<>();

        int maxCharsPerLine = 20;
        int currentChars = 0;
        
        StringBuilder str = new StringBuilder();
        String temp;

        for (String word : splitString)
        {

            // make new lore line
            if (currentChars >= maxCharsPerLine)
            {

                currentChars = 0;
                
                // Remove hanging space at the end of the string we built.
                temp = str.substring(0, str.length() - 1);

                
                componentArray.add(Component.text(temp));

                // Clear string builder.
                str.setLength(0);
            }


            // Add text to current lore line
            str.append(word + " ");

            currentChars += word.length();
        }

        if (componentArray.size() == 0)
            componentArray.add(Component.text(str.substring(0, str.length() - 1)));

        return componentArray;
    }
}
