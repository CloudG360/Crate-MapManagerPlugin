package com.buildtools.BuildServerCore.Events;


import com.buildtools.BuildServerCore.CustomClasses.Component;
import com.buildtools.BuildServerCore.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EventHandler implements Listener {

    @org.bukkit.event.EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        event.setJoinMessage("");
        Component.messageToPublicChatFormat(ChatColor.DARK_GRAY , "Join", event.getPlayer().getDisplayName() + " joined the server.");
    }

    @org.bukkit.event.EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        event.setQuitMessage("");
        Component.messageToPublicChatFormat(ChatColor.DARK_GRAY , "Leave",event.getPlayer().getDisplayName() + " left the server.");
    }

    @org.bukkit.event.EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        if(event.getInventory().getType() == InventoryType.CHEST) {
            List<String> loreCheck = new ArrayList<>();
            loreCheck.add(ChatColor.BLACK + "ID:48037181-6541-41fa-ac1d-b5811f4e19b7");
            Component.messageToPublicChatFormat(ChatColor.DARK_RED, "Debug", "UI CLICK EVENT");
            if (event.getInventory().getItem(1).getItemMeta().getLore().equals(loreCheck)) {
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();
                event.getWhoClicked().openInventory(Main.uiComponent.uiMapOptions(event.getCurrentItem().getItemMeta().getDisplayName()));
            }

            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.BLACK + "ID:25cbd10b-6327-4879-8876-ebdb017c9873");
            if (event.getInventory().getItem(1).getItemMeta().getLore().equals(lore)) {
                ItemStack item = event.getCurrentItem();

                if (item.getItemMeta().getDisplayName().equals("Open Map")) {
                    event.getWhoClicked().closeInventory();
                    Main.worldComponent.loadMap(event.getInventory().getItem(0).getItemMeta().getDisplayName());
                } else if (item.getItemMeta().getDisplayName().equals("Close Map")) {
                    event.getWhoClicked().closeInventory();
                    Main.worldComponent.unloadMap(event.getInventory().getItem(0).getItemMeta().getDisplayName());
                } else if (item.getData().getItemType() == Material.COMPASS) {
                    event.getWhoClicked().closeInventory();
                    Main.teleportComponent.teleportToPosition(Main.plugin.getServer().getPlayer(event.getWhoClicked().getName()), Main.plugin.getServer().getWorld(event.getInventory().getItem(0).getItemMeta().getDisplayName()).getSpawnLocation());
                } else {
                    event.getWhoClicked().closeInventory();
                }

                event.setCancelled(true);
            }
        }
    }

    @org.bukkit.event.EventHandler
    public void onInventoryOpenEvent(InventoryOpenEvent event){
        List<String> loreCheck = new ArrayList<>();
        loreCheck.add(ChatColor.BLACK+"ID:48037181-6541-41fa-ac1d-b5811f4e19b7");
        if (event.getInventory().getItem(1).getItemMeta().getLore().equals(loreCheck)) {
            Component.messageToPublicChatFormat(ChatColor.DARK_RED, "Debug", "UI TEST POSITIVE");

            int index=0;

            for (World world: Main.plugin.getServer().getWorlds()){
                if(index > 35) {
                    break;
                }
                if(new File("./maps/"+world.getName()).exists()) {

                    ItemStack itemStack = new ItemStack(Material.BOOK_AND_QUILL, 1);
                    ItemMeta meta = itemStack.getItemMeta();
                    meta.setDisplayName(world.getName());
                    List<String> lore = new ArrayList<>();
                    lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Category: "+ ChatColor.WHITE + "PLACEHOLDER");
                    lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Author: "+ ChatColor.WHITE + "PLACEHOLDER");
                    meta.setLore(lore);
                    if(new File("./backups/"+world.getName()).exists()){
                        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
                    }
                    itemStack.setItemMeta(meta);

                    event.getInventory().setItem(9+index, itemStack);
                    index++;
                }

            }

            for (File unloadedMap: new File("./maps").listFiles()) {
                if(index > 35) {
                    break;
                }
                boolean match = false;
                for (World world:Main.plugin.getServer().getWorlds()) {
                    if(world.getName().equals(unloadedMap.getName())){
                        match=true;
                    }
                }
                if(!match){
                    ItemStack itemStack = new ItemStack(Material.BOOK, 1);
                    ItemMeta meta = itemStack.getItemMeta();
                    meta.setDisplayName(unloadedMap.getName());
                    List<String> lore = new ArrayList<>();
                    lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Category: "+ ChatColor.WHITE + "PLACEHOLDER");
                    lore.add(ChatColor.BOLD + " " + ChatColor.GREEN + "Author: "+ ChatColor.WHITE + "PLACEHOLDER");
                    meta.setLore(lore);
                    if(new File("./backups/"+unloadedMap.getName()).exists()){
                        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
                    }
                    itemStack.setItemMeta(meta);

                    event.getInventory().setItem(9+index, itemStack);
                    index++;
                }
            }

        }
    }

}
