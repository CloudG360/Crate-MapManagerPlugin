package com.buildtools.BuildServerCore.CustomClasses;

import com.buildtools.BuildServerCore.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class Component {

    public String componentID;
    public ChatColor componentColour;

    public Component(){
        componentID="Generic";
        componentColour=ChatColor.DARK_GREEN;
    }

    public void messageToPublicChat(String message){
        String messageOut = componentColour + componentID + "> "+ ChatColor.GRAY + message;
        for(Player player:Main.plugin.getServer().getOnlinePlayers()){
            player.sendMessage(messageOut);
        }
    }
    public static void messageToPublicChatFormat(ChatColor compColour, String compID, String message){
        String messageOut = compColour + compID + "> "+ ChatColor.GRAY + message;
        for(Player player:Main.plugin.getServer().getOnlinePlayers()){
            player.sendMessage(messageOut);
        }
    }
    public void messageToPlayerChat(String message, Player player){
        String messageOut = componentColour + componentID + "> "+ ChatColor.GRAY + message;
        player.sendMessage(messageOut);
    }

}
