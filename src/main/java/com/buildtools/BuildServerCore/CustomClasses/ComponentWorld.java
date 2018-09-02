package com.buildtools.BuildServerCore.CustomClasses;

import com.buildtools.BuildServerCore.CustomClasses.Generators.FlatWorld;
import com.buildtools.BuildServerCore.CustomClasses.Generators.VoidWorld;
import com.buildtools.BuildServerCore.Main;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

public class ComponentWorld extends Component {

    public ComponentWorld() {
        componentID = "World Manager";
        componentColour = ChatColor.DARK_GREEN;
    }

    public enum worldType{
        ACTIVE,
        INACTIVE,
        ACTIVE_NOBACKUP,
        INACTIVE_NOBACKUP,
        ACTIVE_NOARCHIVE,
        INACTIVE_NOARCHIVE,
        ACTIVE_NOBACKUP_NOARCHIVE,
        INVALID
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
            cfgwriter.write("name="+name+"\nauthor="+author+"\ncategory="+category+"\ngenerator="+generator+"\nwhitelistEnabled=false"+"\nwhitelist=null");
            cfgwriter.close();
            messageToPublicChat("Generated Buildinfo file");
        } catch (IOException err){
            messageToPublicChat("An IOException occurred during the creation of 'buildinfo.cfg'. Please manually delete the save file as it will no longer be loaded.");
            Main.plugin.getServer().unloadWorld(world, true);
            return;
        }

        world.save();
        Main.plugin.getServer().unloadWorld(world, true);

        try{
            if(!new File("./maps").exists()){
                new File("./maps").mkdir();
            }
            if(new File("./maps/"+name).exists()){
                deleteWorld(new File("./maps/"+name));
                messageToPublicChat("Cleaning up old directories.");
            }
            new File("./maps/"+name).mkdir();
            FileUtils.copyDirectory(world.getWorldFolder(), new File("./maps/"+name));
        } catch (IOException err){
            messageToPublicChat("An IOException occurred while moving the map to the 'maps' directory. Please manually delete the save file as it will no longer be loaded.");
            return;
        }

        messageToPublicChat("Map" + name+ " has been created.");

    }

    public void unloadMap(String name){
        World world;
        try {
            world = Main.plugin.getServer().getWorld(name);
        } catch (Exception err){
            messageToPublicChat("Error locating world "+name);
            return;
        }
        messageToPublicChat("Unloading map " + name);
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
                new File("./maps"+name).delete();
            }

            FileUtils.copyDirectory(world.getWorldFolder(), new File("./maps/"+name));
            deleteWorld(world.getWorldFolder());

        } catch (IOException err) {
            messageToPublicChat("An IOException occurred while creating a map library copy. The world is saved in the sever directory and can be manually fixed by moving it.");
        }
        messageToPublicChat("Unloaded map "+name+" successfully!");


    }

    public void backupMap(String name){
        World world;
        try {
            world = Main.plugin.getServer().getWorld(name);
        } catch (Exception err){
            messageToPublicChat("Error locating world "+name);
            return;
        }
        messageToPublicChat("Backing up map " + name);



        messageToPublicChat("[Stage 1/2] Saving Map.");
        world.save();
        messageToPublicChat("[Stage 1/2] Moving saved map to archive.");

        try {
            if(new File("./backups/"+name).exists()){
                new File("./backups/"+name).delete();
            }

            FileUtils.copyDirectory(world.getWorldFolder(), new File("./backups/"+name));

        } catch (IOException err) {
            messageToPublicChat("An IOException occurred while creating a backup library copy. The world is saved in the sever directory and can be manually fixed by moving it.");
        }
        messageToPublicChat("Backed up map "+name+" successfully!");


    }

    public void loadMap(String name){
        for(World world: Main.plugin.getServer().getWorlds()){
            if(world.getName().equals(name)){
                messageToPublicChat("World "+name+" already is open. Aborting.");
                return;
            }
        }
        if(!(new File("./maps/"+name).exists())){
            messageToPublicChat("The map "+name+" does not exist in the library.");
            return;
        }

        try {
            new File("./"+name).mkdir();
            FileUtils.copyDirectory(new File("./maps/" + name), new File("./"+name));
        } catch (IOException err){
            messageToPublicChat("An IOException occured when migrating the map "+name+" from the library to the active server. The save is safe in the library but has not been loaded.");
            return;
        }

        Main.plugin.getServer().createWorld(new WorldCreator(name).generator(Main.translationTable.get(getMapDataFromArchived(name).get("generator"))));

        World world = Main.plugin.getServer().getWorld(name);

        world.setMonsterSpawnLimit(0);
        world.setAmbientSpawnLimit(0);
        world.setAnimalSpawnLimit(0);
        world.setWaterAnimalSpawnLimit(0);
        messageToPublicChat("Loaded the map "+name+" successfully (/tptoworld "+name+")");


    }

    public Map<String, String> getMapDataFromArchived(String name) {
        if(new File("./maps/"+name).exists()){
            if (new File("./maps/"+name+"/buildinfo.cfg").exists()){
                try {
                    FileReader reader = new FileReader(new File("./maps/" + name + "/buildinfo.cfg"));
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    Iterator<String> lines = bufferedReader.lines().iterator();



                    Map<String, String> cfg = new HashMap<>();

                    while (lines.hasNext()){
                        String line =lines.next();
                        cfg.put(line.split("=")[0], line.split("=")[1]);
                    }

                    bufferedReader.close();

                    return cfg;

                } catch (IOException err) {
                    messageToPublicChat("An IO exception occured while reading the map data for "+name);
                }
            } else {
            }
        } else {
        }
        return new HashMap<String, String>();
    }

    public Map<String, String> getMapDataFromActive(String name) {
            if (new File("./"+name+"/buildinfo.cfg").exists()){
                try {
                    FileReader reader = new FileReader(new File("./"+name+"/buildinfo.cfg"));
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    Iterator<String> lines = bufferedReader.lines().iterator();



                    Map<String, String> cfg = new HashMap<>();

                    while (lines.hasNext()){
                        String line =lines.next();
                        cfg.put(line.split("=")[0], line.split("=")[1]);
                    }

                    bufferedReader.close();

                    return cfg;

                } catch (IOException err) {
                    messageToPublicChat("An IO exception occured while reading the map data for "+name);
                }
            } else {
            }
        return new HashMap<String, String>();
    }

    public Map<String, String> getMapDataFromBackup(String name) {
        if(new File("./backups/"+name).exists()){
            if (new File("./backups/"+name+"/buildinfo.cfg").exists()){
                try {
                    FileReader reader = new FileReader(new File("./backups/" + name + "/buildinfo.cfg"));
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    Iterator<String> lines = bufferedReader.lines().iterator();



                    Map<String, String> cfg = new HashMap<>();

                    while (lines.hasNext()){
                        String line =lines.next();
                        cfg.put(line.split("=")[0], line.split("=")[1]);
                    }

                    bufferedReader.close();

                    return cfg;

                } catch (IOException err) {
                    messageToPublicChat("An IO exception occured while reading the map data for "+name);
                }
            } else {
            }
        } else {
        }
        return new HashMap<String, String>();
    }

    public worldType getWorldType(String name){
        if(getMapDataFromArchived(name).size() != 0){
            if(getMapDataFromActive(name).size() != 0){
                if (getMapDataFromBackup(name).size() != 0){
                    return worldType.ACTIVE;
                } else {
                    return worldType.ACTIVE_NOBACKUP;
                }
            } else {
                if (getMapDataFromBackup(name).size() != 0){
                    return worldType.INACTIVE;
                } else {
                    return worldType.INACTIVE_NOBACKUP;
                }
            }
        } else if (getMapDataFromActive(name).size() != 0){
            if (getMapDataFromBackup(name).size() != 0){
                return worldType.ACTIVE_NOARCHIVE;
            } else {
                return worldType.ACTIVE_NOBACKUP_NOARCHIVE;
            }
        } else if (getMapDataFromBackup(name).size() != 0){
            return worldType.INACTIVE_NOARCHIVE;
        } else {
            return worldType.INVALID;
        }
    }

    public boolean deleteWorld(File path) {
        if(path.exists()) {
            File files[] = path.listFiles();
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return(path.delete());
    }




}
