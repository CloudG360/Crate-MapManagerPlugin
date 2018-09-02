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

            float speed = Float.valueOf(strings[1]) / 10;

            if (speed > 1){
                Component.messageToPlayerChatFormat(player, ChatColor.YELLOW, "Speed", "Please choose a value between "+ChatColor.YELLOW+"0.0 "+ChatColor.GRAY+"and "+ChatColor.YELLOW+"10.0.");
                return true;
            }

            if(strings[0].equals("fly")) {
                player.setFlySpeed(speed);
                Component.messageToPlayerChatFormat(player, ChatColor.YELLOW, "Speed", "Updated your Flight Speed to "+ChatColor.YELLOW+String.valueOf(speed * 10));
            }
            if(strings[0].equals("foot")) {
                player.setWalkSpeed(speed);
                Component.messageToPlayerChatFormat(player, ChatColor.YELLOW, "Speed", "Updated your Ground Speed to "+ChatColor.YELLOW+String.valueOf(speed * 10));
            }

        } else {
            Component.messageToPublicChatFormat(ChatColor.YELLOW, "Speed", "You aren't a player. Please log in.");
        }

        return true;
    }
}
