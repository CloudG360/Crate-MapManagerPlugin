package com.buildtools.BuildServerCore.Events;


import com.buildtools.BuildServerCore.CustomClasses.Component;
import com.buildtools.BuildServerCore.CustomClasses.ComponentWorld;
import com.buildtools.BuildServerCore.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EventHandler implements Listener {

    @org.bukkit.event.EventHandler
    public void onBlockPlaced(BlockPlaceEvent event){

        if(event.getBlock().getWorld().getName().equals(Main.plugin.getServer().getWorlds().get(0).getName())){
            if(!event.getPlayer().isOp()){
                event.setCancelled(true);
                Component.messageToPlayerChatFormat(event.getPlayer(), ChatColor.DARK_RED, "Protection", "This world is protected. You have no permission to edit this map.");
            }
        }

        if(Main.worldComponent.getWorldType(event.getBlock().getWorld().getName()).toString().contains("ACTIVE")){
            if(Main.worldComponent.getMapDataFromActive(event.getBlock().getWorld().getName()).get("whitelist").equals("true")){
                if(!(Main.worldComponent.getMapDataFromActive(event.getBlock().getWorld().getName()).get("whitelistEnabled").contains(event.getPlayer().getUniqueId().toString()))){
                    event.setCancelled(true);
                    Component.messageToPlayerChatFormat(event.getPlayer(), ChatColor.DARK_RED, "Protection", "This world is protected. You have no permission to edit this map.");

                }
            }
        }
    }

    @org.bukkit.event.EventHandler
    public void onBlockBroke(BlockBreakEvent event){

        if(event.getBlock().getWorld().getName().equals(Main.plugin.getServer().getWorlds().get(0).getName())){
            if(!event.getPlayer().isOp()){
                event.setCancelled(true);
                Component.messageToPlayerChatFormat(event.getPlayer(), ChatColor.DARK_RED, "Protection", "This world is protected. You have no permission to edit this map.");
            }
        }

        if(Main.worldComponent.getWorldType(event.getBlock().getWorld().getName()).toString().contains("ACTIVE")){
            if(Main.worldComponent.getMapDataFromActive(event.getBlock().getWorld().getName()).get("whitelist").equals("true")){
                if(!(Main.worldComponent.getMapDataFromActive(event.getBlock().getWorld().getName()).get("whitelistEnabled").contains(event.getPlayer().getUniqueId().toString()))){
                    event.setCancelled(true);
                    Component.messageToPlayerChatFormat(event.getPlayer(), ChatColor.DARK_RED, "Protection", "This world is protected. You have no permission to edit this map.");

                }
            }
        }
    }


    @org.bukkit.event.EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        event.setJoinMessage("");
        event.getPlayer().teleport(Main.plugin.getServer().getWorlds().get(0).getSpawnLocation());
        Component.messageToPublicChatFormat(ChatColor.DARK_GRAY , "Join", event.getPlayer().getDisplayName() + " joined the server.");
    }

    @org.bukkit.event.EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        event.setQuitMessage("");
        Component.messageToPublicChatFormat(ChatColor.DARK_GRAY , "Leave",event.getPlayer().getDisplayName() + " left the server.");
    }

    @org.bukkit.event.EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){
        event.setCancelled(true);

        ChatColor worldcol;

        if (Main.worldComponent.getWorldType(event.getPlayer().getWorld().getName()) == ComponentWorld.worldType.INVALID){
            worldcol = ChatColor.BLUE;
        } else {
            if(Main.worldComponent.getMapDataFromActive(event.getPlayer().getWorld().getName()).get("whitelistEnabled").equals("true")){
                if(Main.worldComponent.getMapDataFromActive(event.getPlayer().getWorld().getName()).get("whitelist").contains(event.getPlayer().getUniqueId().toString())){
                    worldcol = ChatColor.DARK_GREEN;
                } else {
                    worldcol = ChatColor.RED;
                }
            } else {
                worldcol = ChatColor.DARK_GREEN;
            }
        }


        if(event.getPlayer().isOp()) {
            Component.messageToChatFormat(ChatColor.AQUA+""+ChatColor.MAGIC+ChatColor.BOLD+"O"+ChatColor.RESET+""+ ChatColor.RED +""+ChatColor.BOLD +  "OP> " + worldcol +ChatColor.BOLD + ""  + event.getPlayer().getWorld().getName(), worldcol, event.getPlayer().getDisplayName(), event.getMessage());
        } else {
            Component.messageToChatFormat(worldcol+""+ChatColor.BOLD + event.getPlayer().getWorld().getName(), worldcol, event.getPlayer().getDisplayName(),event.getMessage());

        }
    }

    @org.bukkit.event.EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        if(event.getInventory().getType() == InventoryType.CHEST) {
            List<String> lore= new ArrayList<>();
            lore.add(ChatColor.BLACK + "ID:48037181-6541-41fa-ac1d-b5811f4e19b7");
            if (event.getInventory().getItem(1).getItemMeta().getLore().equals(lore)) {
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();

                if(event.getCurrentItem().getData().getItemType().equals(Material.BOOK)) {
                    event.getWhoClicked().openInventory(Main.uiComponent.uiMapOptions(event.getCurrentItem().getItemMeta().getDisplayName()));
                    return;
                }
                if(event.getCurrentItem().getData().getItemType().equals(Material.BOOK_AND_QUILL)) {
                    event.getWhoClicked().openInventory(Main.uiComponent.uiMapOptions(event.getCurrentItem().getItemMeta().getDisplayName()));
                    return;
                }
                if(event.getCurrentItem().getData().getItemType().equals(Material.PAPER)) {
                    event.getWhoClicked().openInventory(Main.uiComponent.uiMapOptions(event.getCurrentItem().getItemMeta().getDisplayName()));
                    return;
                }
                if(event.getCurrentItem().getData().getItemType().equals(Material.BARRIER)) {
                    event.getWhoClicked().openInventory(Main.uiComponent.uiMapOptions(event.getCurrentItem().getItemMeta().getDisplayName()));
                    return;
                } if(event.getCurrentItem().getData().getItemType().equals(Material.HOPPER)) {
                    event.getWhoClicked().openInventory(Main.uiComponent.uiMapFilterOptions());
                    return;
                }
            }

            lore = new ArrayList<>();
            lore.add(ChatColor.BLACK + "ID:25cbd10b-6327-4879-8876-ebdb017c9873");
            if (event.getInventory().getItem(1).getItemMeta().getLore().equals(lore)) {
                ItemStack item = event.getCurrentItem();

                if (item.getData().getItemType() == Material.BOOK_AND_QUILL) {
                    event.getWhoClicked().closeInventory();
                    Main.worldComponent.loadMap(event.getInventory().getItem(11).getItemMeta().getLore().get(0));
                } else if (item.getData().getItemType() == Material.BOOK) {
                    event.getWhoClicked().closeInventory();
                    Main.worldComponent.unloadMap(event.getInventory().getItem(11).getItemMeta().getLore().get(0));
                } else if (item.getData().getItemType() == Material.COMPASS) {
                    event.getWhoClicked().closeInventory();
                    Main.teleportComponent.teleportToPosition(Main.plugin.getServer().getPlayer(event.getWhoClicked().getName()), Main.plugin.getServer().getWorld(event.getInventory().getItem(11).getItemMeta().getLore().get(0)).getSpawnLocation());
                } else {
                    event.getWhoClicked().closeInventory();
                }

                event.setCancelled(true);
            }

            lore = new ArrayList<>();
            lore.add(ChatColor.BLACK + "ID:f81ac688-7ebd-4d0d-b262-0ccf42be401b");

            if (event.getInventory().getItem(1).getItemMeta().getLore().equals(lore)) {
                ItemStack item = event.getCurrentItem();

                if (item.getData().getItemType() == Material.BOOK_AND_QUILL) {
                    event.setCancelled(true);
                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory(Main.uiComponent.uiMapManager("maptype#open"));
                } else if (item.getData().getItemType() == Material.BOOK) {
                    event.setCancelled(true);
                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory(Main.uiComponent.uiMapManager("maptype#closed"));
                } else if (item.getData().getItemType() == Material.PAPER) {
                    event.setCancelled(true);
                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory(Main.uiComponent.uiMapManager("maptype#backuponly"));
                } else {
                    event.setCancelled(true);
                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory(Main.uiComponent.uiMapManager("filter#none"));
                }

                event.setCancelled(true);
            }

        }
    }

}
