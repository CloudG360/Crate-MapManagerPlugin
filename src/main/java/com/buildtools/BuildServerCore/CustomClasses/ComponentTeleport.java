package com.buildtools.BuildServerCore.CustomClasses;

import com.buildtools.BuildServerCore.Main;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ComponentTeleport extends Component {

    public ComponentTeleport() {
        componentID = "Teleport";
        componentColour = ChatColor.RED;
    }


    public void teleportToPosition(Player player, Location location){
        World locWorld=location.getWorld();

        if(Main.worldComponent.getWorldType(locWorld.getName()) != ComponentWorld.worldType.INVALID){
            if(Main.worldComponent.getMapDataFromActive(locWorld.getName()).get("whitelistEnabled").equals("true")){
                String[] playerList = Main.worldComponent.getMapDataFromActive(locWorld.getName()).get("whitelist").split(",");
                if(!(Arrays.asList(playerList).contains(player.getUniqueId().toString()))){
                    messageToPlayerChat(ChatColor.BOLD + " " +ChatColor.RED + "Error whilst teleporting to "+locWorld.getName()+": "+ChatColor.GOLD+" You are not whitelisted.", player);
                }
            }
        }

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


}
