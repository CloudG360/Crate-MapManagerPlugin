package com.buildtools.BuildServerCore.CustomClasses;

import com.buildtools.BuildServerCore.CustomClasses.Generators.FlatWorld;
import com.buildtools.BuildServerCore.CustomClasses.Generators.VoidWorld;
import com.buildtools.BuildServerCore.Main;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

public class ComponentWorld extends Component {

    public ComponentWorld() {
        componentID = "World Manager";
        componentColour = ChatColor.BLUE;
        componentColour = ChatColor.BLUE;
    }

    public void createMap(String name, String author, String category, String generator){
        messageToPublicChat("Beginning the creation of '"+name+"' using the generator:"+generator);

        World world = Main.plugin.getServer().createWorld(new WorldCreator(name).type(WorldType.FLAT).generator(Main.translationTable.get(generator)));
        world.setMonsterSpawnLimit(0);
        world.setAmbientSpawnLimit(0);
        world.setAnimalSpawnLimit(0);
        world.setWaterAnimalSpawnLimit(0);
        world.setSpawnLocation(0, 70, 0);
        world.save();

        messageToPublicChat("Generating Buildinfo file");
        try {
            new File(world.getWorldFolder() + "/buildinfo.cfg").createNewFile();
            FileWriter cfgwriter = new FileWriter(new File(world.getWorldFolder() + "/buildinfo.cfg"));
            cfgwriter.write("name="+name+"\nauthor="+author+"\ncategory="+category+"\ngenerator="+generator);
            messageToPublicChat("Generated Buildinfo file");
        } catch (IOException err){
            messageToPublicChat("An IOException occurred during the creation of 'buildinfo.cfg'. Please manually delete the save file as it will no longer be loaded.");
            Main.plugin.getServer().unloadWorld(world, true);
            return;
        }

        Main.plugin.getServer().unloadWorld(world, true);

        try{
            if(!new File("./maps").exists()){
                new File("./maps").mkdir();
            }
            if(new File("./maps/"+name).exists()){
                FileUtils.deleteDirectory(new File("./maps/"+name));
                messageToPublicChat("Cleaning up old directories.");
            }
            new File(".maps/"+name).mkdir();
            FileUtils.copyDirectory(world.getWorldFolder(), new File("./maps/"+name));
        } catch (IOException err){
            messageToPublicChat("An IOException occurred while moving the map to the 'maps' directory. Please manually delete the save file as it will no longer be loaded.");
            return;
        }

        messageToPublicChat("Map" + name+ " has been created.");

    }

    public void unloadMap(String name){
        messageToPublicChat("Unloading map " + name);
        World world = Main.plugin.getServer().getWorld(name);
        world.save();

        messageToPublicChat("Moving players out of "+name);
        for(Player player:world.getPlayers()){
            Main.teleportComponent.teleportToPosition(player, Main.plugin.getServer().getWorlds().get(0).getSpawnLocation());
            messageToPlayerChat("You have been moved out of the Map "+name, player);
        }

        messageToPublicChat("Unloading map world from server.");
        Main.plugin.getServer().unloadWorld(world, true);
        messageToPublicChat("Moving Up-to-date copy to map library.");

        try {
            if(new File("./maps/"+name).exists()){
                Thread.sleep(10000);
                FileUtils.deleteDirectory(new File("./maps/"+name));
            }
            FileUtils.moveDirectory(world.getWorldFolder(), new File("./maps/"));
        } catch (IOException err){
            messageToPublicChat("An IOException occurred while creating a map library copy. The world is saved in the sever directory and can be manually fixed by moving it.");
        } catch (InterruptedException err){
            messageToPublicChat("An Interrupted occurred while creating a map library copy. The world is saved in the sever directory and can be manually fixed by moving it.");

        }

        messageToPublicChat("Unloaded map "+name+" successfully!");


    }

    public void loadMap(String name){
        if(!(new File("./maps/"+name).exists())){
            messageToPublicChat("The map "+name+" does not exist in the library.");
            return;
        }

        try {
            FileUtils.copyDirectory(new File("./maps/" + name), new File("."));
        } catch (IOException err){
            messageToPublicChat("An IOException occured when migrating the map "+name+" from the library to the active server. The save is safe in the library but has not been loaded.");
            return;
        }

        Main.plugin.getServer().createWorld(new WorldCreator(name));
        messageToPublicChat("Loaded the map "+name+" successfully (/tptoworld "+name+")");


    }




}
