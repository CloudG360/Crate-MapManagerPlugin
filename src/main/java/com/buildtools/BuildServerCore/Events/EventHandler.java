package com.buildtools.BuildServerCore.Events;


import com.buildtools.BuildServerCore.CustomClasses.Component;
import com.buildtools.BuildServerCore.Main;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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

}
