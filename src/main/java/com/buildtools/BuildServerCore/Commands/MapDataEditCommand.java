package com.buildtools.BuildServerCore.Commands;

import com.buildtools.BuildServerCore.CustomClasses.Component;
import com.buildtools.BuildServerCore.CustomClasses.ComponentWorld;
import com.buildtools.BuildServerCore.Main;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class MapDataEditCommand implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){

        if(!commandSender.isOp()){
            Component.messageToPublicChatFormat(ChatColor.AQUA, "WorldData", "Failed to edit world data: Not OP");
            return true;
        }

        if(!(Main.worldComponent.getWorldType(strings[0]).toString().contains("ACTIVE"))){
            Component.messageToPublicChatFormat(ChatColor.AQUA, "WorldData", "Unable to edit world. Make sure it's active and is valid.");
        }

        World world = Main.plugin.getServer().getWorld(strings[0]);
        Map<String, String> data = Main.worldComponent.getMapDataFromActive(world.getName());
        try {
            FileWriter cfgwriter = new FileWriter(new File(world.getWorldFolder() + "/buildinfo.cfg"));

            data.remove(strings[1]);
            data.put(strings[1],strings[2]);
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
            Component.messageToPublicChatFormat(ChatColor.AQUA, "WorldData", "Editied Buildinfo file successfully.");
        } catch (IOException err){
            Component.messageToPublicChatFormat(ChatColor.AQUA, "WorldData", "An IOException occurred during the editing of 'buildinfo.cfg'. Changes have not been applied.");

        }


        return true;
    }
}
