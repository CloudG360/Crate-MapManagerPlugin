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
        world.save();

        messageToPublicChat("Generating Buildinfo file");
        try {
            new File(world.getWorldFolder() + "/buildinfo.cfg").createNewFile();
            FileWriter cfgwriter = new FileWriter(new File(world.getWorldFolder() + "buildinfo.cfg"));
            cfgwriter.write("name="+name+"\nauthor="+author+"\ncategory="+category+"\ngenerator="+generator);
            messageToPublicChat("Generated Buildinfo file");
        } catch (IOException err){
            messageToPublicChat("An IOException occured during the creation of 'buildinfo.cfg'. Please manually delete the save file as it will no longer be loaded.");
            Main.plugin.getServer().unloadWorld(world, true);
            return;
        }

        Main.plugin.getServer().unloadWorld(world, true);

        try{
            if(!new File("./maps").exists()){
                new File("./maps").mkdir();
            }
            if(new File("./maps/"+name).exists()){
                FileUtils.deleteDirectory(new File(".maps/"+name));
                messageToPublicChat("Cleaning up old directories.");
            }
            new File(".maps/"+name).mkdir();
            FileUtils.copyDirectory(world.getWorldFolder(), new File(".maps/"+name));
        } catch (IOException err){
            messageToPublicChat("An IOException occured while moving the map to the 'maps' directory. Please manually delete the save file as it will no longer be loaded.");
            return;
        }

        messageToPublicChat("Map" + name+ " has been created.");

    }

    public void unloadMap(String name){
        messageToPublicChat("Unloading map " + name);
        World world = Main.plugin.getServer().getWorld(name);
        world.save();

        //Move Players out.

        Main.plugin.getServer().unloadWorld(world, true);
        messageToPublicChat("Moving Up-to-date copy to map library ");
        //move to /maps
        

    }




}
