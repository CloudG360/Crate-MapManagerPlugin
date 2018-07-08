package com.buildtools.BuildServerCore.CustomClasses;

import com.buildtools.BuildServerCore.Main;
import org.apache.commons.io.FileUtils;


import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class BuildWorld {

    private World world;

    private String id;
    private String category;
    private String author;

    private boolean isLoadedOnServer;
    private boolean isOpenWorld;

    public BuildWorld (World newWorld, String name, String cat, String creator, boolean loadOnServer, boolean publicBuildWorld) {

        world = newWorld;

        id = name;
        category = cat;
        author = creator;

        isLoadedOnServer = true;
        isOpenWorld = publicBuildWorld;

        world.save();

        if(new File(newWorld.getWorldFolder().getParent()+"/maps").exists()) {
            try {
                ComponentWorld.messageToPublicChatFormat(ChatColor.BLUE, "WorldManager", "Creating BuildWorld copy of '"+name+"'");
                Files.copy(newWorld.getWorldFolder().toPath(), new File(newWorld.getWorldFolder().getParent()+"/maps").toPath(), StandardCopyOption.REPLACE_EXISTING);
                Component.messageToPublicChatFormat(ChatColor.BLUE, "Debug", world.getWorldFolder().getPath()+"->"+new File(world.getWorldFolder().getParent()+"/maps").getPath());
            } catch (IOException err) {
                ComponentWorld.messageToPublicChatFormat(ChatColor.BLUE, "WorldManager", "An IOException occured during the handling of '"+name+"' #BW-001");
            }

        } else {
            ComponentWorld.messageToPublicChatFormat(ChatColor.BLUE, "WorldManager", "No '/maps' directory found. Creating directory.");
            new File(newWorld.getWorldFolder().getParent()+"/maps").mkdir();
            try {
                ComponentWorld.messageToPublicChatFormat(ChatColor.BLUE, "WorldManager", "Creating BuildWorld copy of '"+name+"'");
                Files.copy(newWorld.getWorldFolder().toPath(), new File(newWorld.getWorldFolder().getParent()+"/maps").toPath(), StandardCopyOption.REPLACE_EXISTING);
                Component.messageToPublicChatFormat(ChatColor.BLUE, "Debug", world.getWorldFolder().getPath()+"->"+new File(world.getWorldFolder().getParent()+"/maps").getPath());

            } catch (IOException err) {
                ComponentWorld.messageToPublicChatFormat(ChatColor.BLUE, "WorldManager", "An IOException occured during the handling of '"+name+"' #BW-002");
            }
        }

        if(!loadOnServer){
            unloadWorld();
        }

    }

    public void unloadWorld(){
        world.save();
        if(isLoadedOnServer) {

            ComponentWorld.messageToPublicChatFormat(ChatColor.BLUE, "WorldManager", "Forcing players out of world '" + id + "'");
            for(Player player: world.getPlayers()){
                Main.teleportComponent.teleportToPosition(player, Main.plugin.getServer().getWorlds().get(0).getSpawnLocation());
            }

            try {
                ComponentWorld.messageToPublicChatFormat(ChatColor.BLUE, "WorldManager", "Saving and unloading BuildWorld '" + id + "'");
                Component.messageToPublicChatFormat(ChatColor.BLUE, "Debug", world.getWorldFolder().getPath()+"->"+new File(world.getWorldFolder().getParent()+"/maps").getPath());
                Files.move(world.getWorldFolder().toPath(), new File(world.getWorldFolder().getParent()+"/maps").toPath(), StandardCopyOption.REPLACE_EXISTING);
                isLoadedOnServer = false;
            } catch (IOException err) {
                ComponentWorld.messageToPublicChatFormat(ChatColor.BLUE, "WorldManager", "An IOException occured during the handling of '" + id + "'#BW-003");

            }
        } else {
            ComponentWorld.messageToPublicChatFormat(ChatColor.BLUE, "WorldManager", "World '" + id + "' is already unloaded");

        }
    }


    public World getWorld() {
        return world;
    }

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isLoadedOnServer() {
        return isLoadedOnServer;
    }

    public boolean isOpenWorld() {
        return isOpenWorld;
    }
}
