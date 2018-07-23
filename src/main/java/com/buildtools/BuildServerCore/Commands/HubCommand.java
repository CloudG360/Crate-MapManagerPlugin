package com.buildtools.BuildServerCore.Commands;

import com.buildtools.BuildServerCore.CustomClasses.ComponentTeleport;
import com.buildtools.BuildServerCore.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HubCommand implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){
        if(commandSender instanceof Player){
            Main.teleportComponent.teleportToPosition((Player) commandSender, Main.plugin.getServer().getWorlds().get(0).getSpawnLocation());
        } else {
            ComponentTeleport.messageToPublicChatFormat(ChatColor.BLUE, "Teleport", "You aren't a player. Please log in.");
        }

        return true;
    }
}
