package com.buildtools.BuildServerCore.Commands;

import com.buildtools.BuildServerCore.CustomClasses.Component;
import com.buildtools.BuildServerCore.CustomClasses.ComponentTeleport;
import com.buildtools.BuildServerCore.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpeedCommand implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){
        if(commandSender instanceof Player){

            Player player = (Player) commandSender;

            if(strings[0].equals("fly")) {
                player.setFlySpeed(Float.valueOf(strings[1]));
                Component.messageToPublicChatFormat(ChatColor.DARK_GREEN, "Speed", "Updated your Flight Speed to "+ChatColor.YELLOW+strings[1]);
            }
            if(strings[0].equals("foot")) {
                player.setWalkSpeed(Float.valueOf(strings[1]));
                Component.messageToPublicChatFormat(ChatColor.DARK_GREEN, "Speed", "Updated your Ground Speed to "+ChatColor.YELLOW+strings[1]);
            }

        } else {
            Component.messageToPublicChatFormat(ChatColor.DARK_GREEN, "Speed", "You aren't a player. Please log in.");
        }

        return true;
    }
}
