package com.buildtools.BuildServerCore.CustomClasses;

import com.buildtools.BuildServerCore.Main;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;

public class ComponentUI extends Component {

    public Map<String, Inventory> uis;

    public ComponentUI() {
        componentID = "UI";
        componentColour = ChatColor.BLUE;


    }


    public Inventory uiMapOptions(String map){


        Inventory menu = Bukkit.createInventory(null, 27, "Map Manager - "+map);

        short pane_damage = 7;

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.BLACK+"ID:25cbd10b-6327-4879-8876-ebdb017c9873");

        for(int i=0; i <27; i++) {
            if (i<9||i>18){
                ItemStack iStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, pane_damage);
                ItemMeta meta = iStack.getItemMeta();
                meta.setLore(lore);
                iStack.setItemMeta(meta);
                menu.setItem(i, iStack);
            }
        }

        if(Main.worldComponent.getWorldType(map).toString().contains("ACTIVE")) {
            lore = new ArrayList<>();
            lore.add(map);
            ItemStack iStack = new ItemStack(Material.COMPASS, 1);
            ItemMeta meta = iStack.getItemMeta();
            meta.setDisplayName(ChatColor.BOLD+""+ChatColor.GREEN+map);
            meta.setLore(lore);
            iStack.setItemMeta(meta);
            menu.setItem(11, iStack);
        } else {
            ItemStack DisableMap = new ItemStack(Material.BARRIER, 1);
            ItemMeta meta = DisableMap.getItemMeta();
            meta.setDisplayName(ChatColor.BOLD + "" + ChatColor.RED + "Load the map to TP to it.");
            DisableMap.setItemMeta(meta);
            menu.setItem(11, DisableMap);
        }

        if(Main.worldComponent.getWorldType(map).toString().contains("ACTIVE")) {
            ItemStack EnableMap = new ItemStack(Material.BOOK_AND_QUILL, 1);
            ItemMeta meta = EnableMap.getItemMeta();
            meta.setDisplayName(ChatColor.BOLD+""+ChatColor.GREEN+"Open Map");
            EnableMap.setItemMeta(meta);
            menu.setItem(13, EnableMap);
        } else if(Main.worldComponent.getWorldType(map).toString().contains("INACTIVE")) {

            ItemStack DisableMap = new ItemStack(Material.BOOK, 1);
            ItemMeta meta = DisableMap.getItemMeta();
            meta.setDisplayName(ChatColor.BOLD+""+ChatColor.GREEN+"Close Map");
            DisableMap.setItemMeta(meta);
            menu.setItem(13, DisableMap);
        } else {
            ItemStack DisableMap = new ItemStack(Material.BARRIER, 1);
            ItemMeta meta = DisableMap.getItemMeta();
            meta.setDisplayName(ChatColor.BOLD + "" + ChatColor.RED + "Invalid Map. Unable to load it.");
            DisableMap.setItemMeta(meta);
            menu.setItem(13, DisableMap);
        }
        if(Main.worldComponent.getWorldType(map) == ComponentWorld.worldType.INVALID){
            ItemStack BackupMap = new ItemStack(Material.BARRIER, 1);
            ItemMeta meta = BackupMap.getItemMeta();
            meta.setDisplayName(ChatColor.BOLD + "" + ChatColor.RED + "Invalid Map. Unable to back it up.");
            BackupMap.setItemMeta(meta);
            menu.setItem(15, BackupMap);
        } else {
            ItemStack BackupMap = new ItemStack(Material.PAPER, 1);
            ItemMeta meta = BackupMap.getItemMeta();
            meta.setDisplayName(ChatColor.BOLD+""+ChatColor.GREEN+"Backup Map");
            BackupMap.setItemMeta(meta);
            menu.setItem(15, BackupMap);
        }


        return menu;

    }

    public Inventory uiMapFilterOptions(){


        Inventory menu = Bukkit.createInventory(null, 27, "Filter");

        short pane_damage = 7;

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.BLACK+"ID:f81ac688-7ebd-4d0d-b262-0ccf42be401b");

        for(int i=0; i <27; i++) {
            if (i<9||i>27){
                ItemStack iStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, pane_damage);
                ItemMeta meta = iStack.getItemMeta();
                meta.setLore(lore);
                iStack.setItemMeta(meta);
                menu.setItem(i, iStack);
            }
        }


            ItemStack EnableMap = new ItemStack(Material.BOOK_AND_QUILL, 1);
            ItemMeta meta = EnableMap.getItemMeta();
            meta.setDisplayName(ChatColor.BOLD+""+ChatColor.GREEN+"Open Maps");
            EnableMap.setItemMeta(meta);
            menu.setItem(11, EnableMap);

            ItemStack DisableMap = new ItemStack(Material.BOOK, 1);
            meta = DisableMap.getItemMeta();
            meta.setDisplayName(ChatColor.BOLD+""+ChatColor.GREEN+"Closed Maps");
            DisableMap.setItemMeta(meta);
            menu.setItem(13, DisableMap);


            ItemStack BackupMap = new ItemStack(Material.PAPER, 1);
             meta = BackupMap.getItemMeta();
            meta.setDisplayName(ChatColor.BOLD+""+ChatColor.GREEN+"Backup-only Maps");
            BackupMap.setItemMeta(meta);
            menu.setItem(15, BackupMap);



        return menu;

    }

    public Inventory uiMapManager(String filter){
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
        meta.setDisplayName(ChatColor.BOLD+""+ChatColor.GREEN+"Backup Map");
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        backupMapPaper.setItemMeta(meta);
        menu.setItem(0, backupMapPaper);

        ItemStack configCompass = new ItemStack(Material.COMPASS, 1);
        meta=configCompass.getItemMeta();
        meta.setDisplayName(ChatColor.BOLD+""+ChatColor.GREEN+"Settings");
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        configCompass.setItemMeta(meta);
        menu.setItem(4, configCompass);

        ItemStack filterHopper = new ItemStack(Material.HOPPER, 1);
        meta=filterHopper.getItemMeta();
        meta.setDisplayName(ChatColor.BOLD+""+ChatColor.GREEN+"Filter");
        filterHopper.setItemMeta(meta);
        menu.setItem(8, filterHopper);

        ItemStack lastPageArrow = new ItemStack(Material.ARROW, 1);
        meta=lastPageArrow.getItemMeta();
        meta.setDisplayName(ChatColor.BOLD+""+ChatColor.GREEN+"Last Page");
        lastPageArrow.setItemMeta(meta);
        menu.setItem(45, lastPageArrow);

        ItemStack createMapEmerald = new ItemStack(Material.EMERALD, 1);
        meta=createMapEmerald.getItemMeta();
        meta.setDisplayName(ChatColor.BOLD+""+ChatColor.GREEN+"Create Map");
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        createMapEmerald.setItemMeta(meta);
        menu.setItem(49, createMapEmerald);

        ItemStack nextPageArrow = new ItemStack(Material.ARROW, 1);
        meta=nextPageArrow.getItemMeta();
        meta.setDisplayName(ChatColor.BOLD+""+ChatColor.GREEN+"Next Page");
        nextPageArrow.setItemMeta(meta);
        menu.setItem(53, nextPageArrow);

        int index=0;

        if(filter.equals("maptype#closed")){
            for (File map: new File("./maps").listFiles()) {
                ItemStack itemStack;
                Map<String,String> mapData;
                lore = new ArrayList<>();
                switch (Main.worldComponent.getWorldType(map.getName())) {
                    case INACTIVE:
                        itemStack = new ItemStack(Material.BOOK, 1);
                        meta = itemStack.getItemMeta();
                        meta.setDisplayName(map.getName());
                        mapData = Main.worldComponent.getMapDataFromArchived(map.getName());

                        lore = new ArrayList<>();
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Category: "+ ChatColor.RESET + mapData.get("category"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Author: "+ ChatColor.RESET + mapData.get("author"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Generator: "+ ChatColor.RESET + mapData.get("generator"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Has Backup: "+ ChatColor.RESET + "TRUE");
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Whitelist Enabled: "+ ChatColor.RESET + mapData.get("whitelistEnabled"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Whitelist Users: "+ ChatColor.RESET + mapData.get("whitelist"));
                        meta.setLore(lore);

                        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
                        itemStack.setItemMeta(meta);
                        menu.setItem(9+index, itemStack);
                        index++;
                        break;

                    case INACTIVE_NOBACKUP:
                        itemStack = new ItemStack(Material.BOOK, 1);
                        meta = itemStack.getItemMeta();
                        meta.setDisplayName(map.getName());
                        mapData = Main.worldComponent.getMapDataFromArchived(map.getName());

                        lore = new ArrayList<>();
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Category: "+ ChatColor.RESET + mapData.get("category"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Author: "+ ChatColor.RESET + mapData.get("author"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Generator: "+ ChatColor.RESET + mapData.get("generator"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Has Backup: "+ ChatColor.RESET + "FALSE");
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Whitelist Enabled: "+ ChatColor.RESET + mapData.get("whitelistEnabled"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Whitelist Users: "+ ChatColor.RESET + mapData.get("whitelist"));
                        meta.setLore(lore);

                        itemStack.setItemMeta(meta);
                        menu.setItem(9+index, itemStack);
                        index++;
                        break;


                    case INVALID:

                        itemStack = new ItemStack(Material.BARRIER, 1);
                        meta = itemStack.getItemMeta();
                        meta.setDisplayName(map.getName());

                        lore = new ArrayList<>();
                        lore.add(ChatColor.BOLD + " " + ChatColor.RED + "!!! Error !!!: "+ ChatColor.RESET + "The map is invalid/is a standard Minecraft map. DO NOT open this.");
                        meta.setLore(lore);

                        itemStack.setItemMeta(meta);
                        menu.setItem(9+index, itemStack);
                        index++;
                        break;

                }


            }
        } else if(filter.equals("maptype#open")){
            for (World map: Main.plugin.getServer().getWorlds()) {
                ItemStack itemStack;
                Map<String,String> mapData;
                lore = new ArrayList<>();
                switch (Main.worldComponent.getWorldType(map.getName())) {
                    case ACTIVE:
                        itemStack = new ItemStack(Material.BOOK_AND_QUILL, 1);
                        meta = itemStack.getItemMeta();
                        meta.setDisplayName(map.getName());
                        mapData = Main.worldComponent.getMapDataFromActive(map.getName());

                        lore = new ArrayList<>();
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Category: "+ ChatColor.RESET + mapData.get("category"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Author: "+ ChatColor.RESET + mapData.get("author"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Generator: "+ ChatColor.RESET + mapData.get("generator"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Has Backup: "+ ChatColor.RESET + "TRUE");
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Whitelist Enabled: "+ ChatColor.RESET + mapData.get("whitelistEnabled"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Whitelist Users: "+ ChatColor.RESET + mapData.get("whitelist"));
                        meta.setLore(lore);

                        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
                        itemStack.setItemMeta(meta);
                        menu.setItem(9+index, itemStack);
                        index++;
                        break;
                    case ACTIVE_NOBACKUP:
                        itemStack = new ItemStack(Material.BOOK_AND_QUILL, 1);
                        meta = itemStack.getItemMeta();
                        meta.setDisplayName(map.getName());
                        mapData = Main.worldComponent.getMapDataFromActive(map.getName());

                        lore = new ArrayList<>();
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Category: "+ ChatColor.RESET + mapData.get("category"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Author: "+ ChatColor.RESET + mapData.get("author"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Generator: "+ ChatColor.RESET + mapData.get("generator"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Has Backup: "+ ChatColor.RESET + "FALSE");
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Whitelist Enabled: "+ ChatColor.RESET + mapData.get("whitelistEnabled"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Whitelist Users: "+ ChatColor.RESET + mapData.get("whitelist"));
                        meta.setLore(lore);

                        itemStack.setItemMeta(meta);
                        menu.setItem(9+index, itemStack);
                        index++;
                        break;
                    case ACTIVE_NOARCHIVE:
                        itemStack = new ItemStack(Material.BOOK_AND_QUILL, 1);
                        meta = itemStack.getItemMeta();
                        meta.setDisplayName(map.getName());
                        mapData = Main.worldComponent.getMapDataFromActive(map.getName());

                        lore = new ArrayList<>();
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Category: "+ ChatColor.RESET + mapData.get("category"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Author: "+ ChatColor.RESET + mapData.get("author"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Generator: "+ ChatColor.RESET + mapData.get("generator"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Has Backup: "+ ChatColor.RESET + "FALSE");
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Whitelist Enabled: "+ ChatColor.RESET + mapData.get("whitelistEnabled"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Whitelist Users: "+ ChatColor.RESET + mapData.get("whitelist"));
                        meta.setLore(lore);

                        itemStack.setItemMeta(meta);
                        menu.setItem(9+index, itemStack);
                        index++;
                        break;

                }


            }
        } if (filter.equals("maptype#backuponly")){
            for (File map: new File("./backups").listFiles()) {
                ItemStack itemStack;
                Map<String,String> mapData;
                lore = new ArrayList<>();
                switch (Main.worldComponent.getWorldType(map.getName())) {
                    case INACTIVE_NOARCHIVE:
                        itemStack = new ItemStack(Material.PAPER, 1);
                        meta = itemStack.getItemMeta();
                        meta.setDisplayName(map.getName());
                        mapData = Main.worldComponent.getMapDataFromBackup(map.getName());

                        lore = new ArrayList<>();
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Category: "+ ChatColor.RESET + mapData.get("category"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Author: "+ ChatColor.RESET + mapData.get("author"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Generator: "+ ChatColor.RESET + mapData.get("generator"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Has Backup: "+ ChatColor.RESET + "TRUE");
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Whitelist Enabled: "+ ChatColor.RESET + mapData.get("whitelistEnabled"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Whitelist Users: "+ ChatColor.RESET + mapData.get("whitelist"));
                        meta.setLore(lore);

                        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
                        itemStack.setItemMeta(meta);
                        menu.setItem(9+index, itemStack);
                        index++;
                        break;
                }


            }
        } else {
            for (File map: new File("./maps").listFiles()) {
                ItemStack itemStack;
                Map<String,String> mapData;
                lore = new ArrayList<>();
                switch (Main.worldComponent.getWorldType(map.getName())) {
                    case ACTIVE:
                        itemStack = new ItemStack(Material.BOOK_AND_QUILL, 1);
                        meta = itemStack.getItemMeta();
                        meta.setDisplayName(map.getName());
                        mapData = Main.worldComponent.getMapDataFromActive(map.getName());

                        lore = new ArrayList<>();
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Category: "+ ChatColor.RESET + mapData.get("category"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Author: "+ ChatColor.RESET + mapData.get("author"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Generator: "+ ChatColor.RESET + mapData.get("generator"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Has Backup: "+ ChatColor.RESET + "TRUE");
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Whitelist Enabled: "+ ChatColor.RESET + mapData.get("whitelistEnabled"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Whitelist Users: "+ ChatColor.RESET + mapData.get("whitelist"));
                        meta.setLore(lore);

                        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
                        itemStack.setItemMeta(meta);
                        menu.setItem(9+index, itemStack);
                        index++;
                        break;
                    case ACTIVE_NOBACKUP:
                        itemStack = new ItemStack(Material.BOOK_AND_QUILL, 1);
                        meta = itemStack.getItemMeta();
                        meta.setDisplayName(map.getName());
                        mapData = Main.worldComponent.getMapDataFromActive(map.getName());

                        lore = new ArrayList<>();
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Category: "+ ChatColor.RESET + mapData.get("category"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Author: "+ ChatColor.RESET + mapData.get("author"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Generator: "+ ChatColor.RESET + mapData.get("generator"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Has Backup: "+ ChatColor.RESET + "FALSE");
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Whitelist Enabled: "+ ChatColor.RESET + mapData.get("whitelistEnabled"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Whitelist Users: "+ ChatColor.RESET + mapData.get("whitelist"));
                        meta.setLore(lore);

                        itemStack.setItemMeta(meta);
                        menu.setItem(9+index, itemStack);
                        index++;
                        break;
                    case INACTIVE:
                        itemStack = new ItemStack(Material.BOOK, 1);
                        meta = itemStack.getItemMeta();
                        meta.setDisplayName(map.getName());
                        mapData = Main.worldComponent.getMapDataFromArchived(map.getName());

                        lore = new ArrayList<>();
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Category: "+ ChatColor.RESET + mapData.get("category"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Author: "+ ChatColor.RESET + mapData.get("author"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Generator: "+ ChatColor.RESET + mapData.get("generator"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Has Backup: "+ ChatColor.RESET + "TRUE");
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Whitelist Enabled: "+ ChatColor.RESET + mapData.get("whitelistEnabled"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Whitelist Users: "+ ChatColor.RESET + mapData.get("whitelist"));
                        meta.setLore(lore);

                        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);

                        itemStack.setItemMeta(meta);
                        menu.setItem(9+index, itemStack);
                        index++;
                        break;

                    case INACTIVE_NOBACKUP:
                        itemStack = new ItemStack(Material.BOOK, 1);
                        meta = itemStack.getItemMeta();
                        meta.setDisplayName(map.getName());
                        mapData = Main.worldComponent.getMapDataFromArchived(map.getName());

                        lore = new ArrayList<>();
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Category: "+ ChatColor.RESET + mapData.get("category"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Author: "+ ChatColor.RESET + mapData.get("author"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Generator: "+ ChatColor.RESET + mapData.get("generator"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Has Backup: "+ ChatColor.RESET + "FALSE");
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Whitelist Enabled: "+ ChatColor.RESET + mapData.get("whitelistEnabled"));
                        lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Whitelist Users: "+ ChatColor.RESET + mapData.get("whitelist"));
                        meta.setLore(lore);

                        itemStack.setItemMeta(meta);
                        menu.setItem(9+index, itemStack);
                        index++;
                        break;


                    case INVALID:
                        itemStack = new ItemStack(Material.BARRIER, 1);
                        meta = itemStack.getItemMeta();
                        meta.setDisplayName(map.getName());

                        lore = new ArrayList<>();
                        lore.add(ChatColor.BOLD + " " + ChatColor.RED + "!!! Error !!!: "+ ChatColor.RESET + "The map is invalid/is a standard Minecraft map. DO NOT open this.");
                        meta.setLore(lore);

                        itemStack.setItemMeta(meta);
                        menu.setItem(9+index, itemStack);
                        index++;
                        break;

                }


            }
        }

        return menu;
    }


}
