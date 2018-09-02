package com.buildtools.BuildServerCore.Commands;

import com.buildtools.BuildServerCore.CustomClasses.Component;
import com.buildtools.BuildServerCore.Main;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MapWhitelistEditCommand implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){

        if(!commandSender.isOp()){
            Component.messageToPublicChatFormat(ChatColor.GOLD, "Whitelist", "Failed to edit whitelist: Not OP");
            return true;
        }

        if(!(Main.worldComponent.getWorldType(strings[0]).toString().contains("INACTIVE"))){
            Component.messageToPublicChatFormat(ChatColor.AQUA, "WorldData", "Unable to edit world. Make sure it's unloaded and is valid.");
        }

        World world = Main.plugin.getServer().getWorld(strings[0]);

        Map<String, String> data = Main.worldComponent.getMapDataFromActive(world.getName());

        if(strings[1].equals("on")){
            Component.messageToPublicChatFormat(ChatColor.GOLD, "Whitelist", "Enableing whitelist for " + strings[0]);
            data.replace("whitelistEnabled", "true");
        } else if (strings[1].equals("off")){
            Component.messageToPublicChatFormat(ChatColor.GOLD, "Whitelist", "Enableing whitelist for " + strings[0]);
            data.replace("whitelistEnabled", "false");
        } else if (strings[1].equals("add")) {
            Component.messageToPublicChatFormat(ChatColor.GOLD, "Whitelist", "Adding "+strings[2]+" to the whitelist for " + strings[0]);
            if(data.get("whitelist").equals("null")){
                data.replace("whitelist",Main.plugin.getServer().getOfflinePlayer(strings[2]).getUniqueId().toString());
            } else {
                data.replace("whitelist", data.get("whitelist")+","+Main.plugin.getServer().getOfflinePlayer(strings[2]).getUniqueId().toString());
            }

        } else if (strings[1].equals("remove")){
            Component.messageToPublicChatFormat(ChatColor.GOLD, "Whitelist", "Removing "+strings[2]+" from the whitelist for " + strings[0]);

            if(data.get("whitelist").equals("null")){
                Component.messageToPublicChatFormat(ChatColor.GOLD, "Whitelist", "The whitelist is empty!");
                return true;
            }

            if(data.get("whitelist").contains(Main.plugin.getServer().getOfflinePlayer(strings[2]).getUniqueId().toString())){
                List<String> whitelistData = Arrays.asList(data.get("whitelist").split(","));
                whitelistData.remove(Main.plugin.getServer().getOfflinePlayer(strings[2]).getUniqueId().toString());

                if(whitelistData.size() == 0){
                    data.replace("whitelist", "null");
                } else {
                    String finaldata = "";
                    for(String whitelistEntry: whitelistData){
                        finaldata = finaldata.concat(","+whitelistEntry);
                    }
                    finaldata= finaldata.substring(1);
                }

            } else {
                Component.messageToPublicChatFormat(ChatColor.GOLD, "Whitelist", "The whitelist for "+strings[0]+" already contains the player " + strings[2]);
                return true;
            }

            data.replace("whitelist", "false");
        } else {
            Component.messageToPublicChatFormat(ChatColor.GOLD, "Whitelist", "Error: Unable to complete whitelist action. Incorrect command.");
            return true;
        }

        try {
            FileWriter cfgwriter = new FileWriter(new File(world.getWorldFolder() + "/buildinfo.cfg"));


            String content = "";
            boolean f = true;
            for(Map.Entry<String, String> entry: data.entrySet()){
                if(f) {
                    content = content.concat(entry.getKey() + "=" + entry.getValue());
                    f = false;
                } else {
                    content = content.concat("\n" + entry.getKey() + "=" + entry.getValue());
                }
            }


            cfgwriter.write(content);


            cfgwriter.close();
            Component.messageToPublicChatFormat(ChatColor.GOLD, "Whitelist", "Action Complete.");
        } catch (IOException err){
            Component.messageToPublicChatFormat(ChatColor.GOLD, "Whitelist", "An IOException occured while updating the world data. No changes have been made.");
        }


        return true;
    }
}
