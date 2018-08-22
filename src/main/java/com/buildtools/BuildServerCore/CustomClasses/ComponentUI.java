package com.buildtools.BuildServerCore.CustomClasses;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ComponentUI extends Component {

    public Map<String, Inventory> uis;

    public ComponentUI() {
        componentID = "UI";
        componentColour = ChatColor.BLUE;

        uis = new HashMap<String, Inventory>();

        uis.put("test",uiMapManager());

    }


    public void openUI(Player player, String name){
        player.openInventory(uis.get(name));
        messageToPlayerChat("Opening '"+name+"' UI", player);
    }

    public Inventory uiMapOptions(String map){


        Inventory menu = Bukkit.createInventory(null, 27, "Map Manager - "+map);

        short pane_damage = 7;

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.BLACK+"ID:25cbd10b-6327-4879-8876-ebdb017c9873");

        for(int i=0; i <27; i++) {
            if (i<9||i>27){
                ItemStack iStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, pane_damage);
                ItemMeta meta = iStack.getItemMeta();
                meta.setLore(lore);
                iStack.setItemMeta(meta);
                menu.setItem(i, iStack);
            }
        }

        ItemStack iStack = new ItemStack(Material.COMPASS, 1);
        ItemMeta meta = iStack.getItemMeta();
        meta.setDisplayName(map);
        iStack.setItemMeta(meta);
        menu.setItem(0, iStack);

        ItemStack EnableMap = new ItemStack(Material.BOOK_AND_QUILL, 1);
        meta = EnableMap.getItemMeta();
        meta.setDisplayName("Open Map");
        EnableMap.setItemMeta(meta);
        menu.setItem(11, EnableMap);

        ItemStack DisableMap = new ItemStack(Material.BOOK, 1);
        meta = DisableMap.getItemMeta();
        meta.setDisplayName("Close Map");
        DisableMap.setItemMeta(meta);
        menu.setItem(13, DisableMap);

        ItemStack BackupMap = new ItemStack(Material.PAPER, 1);
        meta = BackupMap.getItemMeta();
        meta.setDisplayName("Backup Map");
        BackupMap.setItemMeta(meta);
        menu.setItem(15, BackupMap);

        return menu;

    }

    public Inventory uiMapManager(){
        //https://i.imgur.com/0w7zZWb.png

        Inventory menu = Bukkit.createInventory(null, 54, "Map Manager");
        short pane_damage = 7;

        //Populate Space with panes

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.BLACK+"ID:48037181-6541-41fa-ac1d-b5811f4e19b7");

        for(int i=0; i <54; i++) {
            if (i<9||i>45){
                ItemStack iStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, pane_damage);
                ItemMeta meta = iStack.getItemMeta();
                meta.setLore(lore);
                iStack.setItemMeta(meta);
                menu.setItem(i, iStack);
            }
        }

        ItemMeta meta = null;

        ItemStack backupMapPaper = new ItemStack(Material.PAPER, 1);
        meta=backupMapPaper.getItemMeta();
        meta.setDisplayName("Backup Map");
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        backupMapPaper.setItemMeta(meta);
        menu.setItem(0, backupMapPaper);

        ItemStack configCompass = new ItemStack(Material.COMPASS, 1);
        meta=configCompass.getItemMeta();
        meta.setDisplayName("Settings");
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        configCompass.setItemMeta(meta);
        menu.setItem(4, configCompass);

        ItemStack filterHopper = new ItemStack(Material.HOPPER, 1);
        meta=filterHopper.getItemMeta();
        meta.setDisplayName("Filter");
        filterHopper.setItemMeta(meta);
        menu.setItem(8, filterHopper);

        ItemStack lastPageArrow = new ItemStack(Material.ARROW, 1);
        meta=lastPageArrow.getItemMeta();
        meta.setDisplayName("Last Page");
        lastPageArrow.setItemMeta(meta);
        menu.setItem(45, lastPageArrow);

        ItemStack createMapEmerald = new ItemStack(Material.EMERALD, 1);
        meta=createMapEmerald.getItemMeta();
        meta.setDisplayName("Create Map");
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        createMapEmerald.setItemMeta(meta);
        menu.setItem(49, createMapEmerald);

        ItemStack nextPageArrow = new ItemStack(Material.ARROW, 1);
        meta=nextPageArrow.getItemMeta();
        meta.setDisplayName("Next Page");
        nextPageArrow.setItemMeta(meta);
        menu.setItem(53, nextPageArrow);



        return menu;
    }


}
