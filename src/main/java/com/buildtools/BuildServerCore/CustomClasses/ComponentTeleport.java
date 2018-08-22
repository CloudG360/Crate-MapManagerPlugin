package com.buildtools.BuildServerCore.CustomClasses;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ComponentTeleport extends Component {

    public ComponentTeleport() {
        componentID = "Teleport";
        componentColour = ChatColor.RED;
    }


    public void teleportToPosition(Player player, Location location){
        World locWorld=location.getWorld();
        int locX = location.getBlockX();
        int locY = location.getBlockY() -1;
        int locZ = location.getBlockZ();

        if(locWorld.getBlockAt(new Location(locWorld, locX, locY, locZ)).getType() == Material.AIR){

            locWorld.getBlockAt(new Location(locWorld, locX, locY-1, locZ)).setType(Material.COAL_BLOCK);
            locWorld.getBlockAt(new Location(locWorld, locX+1, locY-1, locZ)).setType(Material.STONE);
            locWorld.getBlockAt(new Location(locWorld, locX-1, locY-1, locZ)).setType(Material.STONE);

            locWorld.getBlockAt(new Location(locWorld, locX, locY-1, locZ+1)).setType(Material.STONE);
            locWorld.getBlockAt(new Location(locWorld, locX-1, locY-1, locZ+1)).setType(Material.STONE);
            locWorld.getBlockAt(new Location(locWorld, locX+1, locY-1, locZ+1)).setType(Material.STONE);

            locWorld.getBlockAt(new Location(locWorld, locX, locY-1, locZ-1)).setType(Material.STONE);
            locWorld.getBlockAt(new Location(locWorld, locX+1, locY-1, locZ-1)).setType(Material.STONE);
            locWorld.getBlockAt(new Location(locWorld, locX-1, locY-1, locZ-1)).setType(Material.STONE);
        }

        player.teleport(location);
        messageToPlayerChat("Teleported you to the position "+location.getBlockX()+","+location.getBlockY()+","+location.getBlockZ()+","+" in the world "+location.getWorld(), player);
    }

    public void teleportToEntity(Player player, Entity entity){
        World locWorld=entity.getWorld();
        int locX = entity.getLocation().getBlockX();
        int locY = entity.getLocation().getBlockY() -1;
        int locZ = entity.getLocation().getBlockZ();

        if(locWorld.getBlockAt(new Location(locWorld, locX, locY, locZ)).getType() == Material.AIR){

            locWorld.getBlockAt(new Location(locWorld, locX, locY-1, locZ)).setType(Material.COAL_BLOCK);
            locWorld.getBlockAt(new Location(locWorld, locX+1, locY-1, locZ)).setType(Material.STONE);
            locWorld.getBlockAt(new Location(locWorld, locX-1, locY-1, locZ)).setType(Material.STONE);

            locWorld.getBlockAt(new Location(locWorld, locX, locY-1, locZ+1)).setType(Material.STONE);
            locWorld.getBlockAt(new Location(locWorld, locX-1, locY-1, locZ+1)).setType(Material.STONE);
            locWorld.getBlockAt(new Location(locWorld, locX+1, locY-1, locZ+1)).setType(Material.STONE);

            locWorld.getBlockAt(new Location(locWorld, locX, locY-1, locZ-1)).setType(Material.STONE);
            locWorld.getBlockAt(new Location(locWorld, locX+1, locY-1, locZ-1)).setType(Material.STONE);
            locWorld.getBlockAt(new Location(locWorld, locX-1, locY-1, locZ-1)).setType(Material.STONE);
        }

        player.teleport(entity);
        messageToPlayerChat("Teleported you to " + entity.getName(), player);

    }


}
