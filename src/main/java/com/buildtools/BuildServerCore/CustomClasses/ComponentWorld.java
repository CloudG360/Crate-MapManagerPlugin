package com.buildtools.BuildServerCore.CustomClasses;

import com.buildtools.BuildServerCore.Main;
import com.google.common.io.Files;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.generator.ChunkGenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComponentWorld extends Component {

    public Map<String, BuildWorld> worldList;

    public ComponentWorld(){
        worldList = new HashMap<String, BuildWorld>();
        componentID = "WorldManager";
        componentColour = ChatColor.BLUE;
    }

    public void createWorld(String name, ChunkGenerator generator, String author) {
        createWorld(name, generator, author, "Standard");
    }

    public void createWorld(String name, ChunkGenerator generator, String author, String category){
        messageToPublicChat("Creating world '"+name+"'");
        World world = Main.plugin.getServer().createWorld(new WorldCreator(name).generator(generator));
        File file = world.getWorldFolder();
        messageToPublicChat("Creating world background data");
        try {
            new File(file.getPath()+"/buildserver/").mkdirs();
            new File(file.getPath()+"/buildserver/worldinfo.cfg").createNewFile();
            FileWriter fwrite = new FileWriter(new File(file.getPath()+"/buildserver/worldinfo.cfg"));
            fwrite.write("name=" + name + "\nauthor=" + author + "\ngenerator=" + generator +"\ncategory="+category);
            fwrite.close();
        } catch (IOException err){
            ComponentWorld.messageToPublicChatFormat(ChatColor.BLUE, "WorldManager", "An IOException occured during the handling of '"+name+"' #CW-001");
        }

        messageToPublicChat("Created ActiveWorld");
        messageToPublicChat("Generating Build Structures");
        world.setSpawnLocation(new Location(world, 0, 70, 0));

        BuildWorld bWorld = new BuildWorld(world, name, category, author, true, false);

        worldList.put(name, bWorld);


    }

    public void loadWorld(String id){
        if(Main.plugin.getServer().getWorld(id) != null){
            try {
                ComponentWorld.messageToPublicChatFormat(ChatColor.BLUE, "WorldManager", "Loading world '" + id + "'");
                Files.copy(new File("/maps/" + id), new File(""));
                World world = Main.plugin.getServer().createWorld(new WorldCreator(id));
                world.setSpawnLocation(new Location(world, 0, 70, 0));
                ComponentWorld.messageToPublicChatFormat(ChatColor.BLUE, "WorldManager", "Sucessfully loaded world '" + id + "'");
            } catch (IOException err){
                ComponentWorld.messageToPublicChatFormat(ChatColor.BLUE, "WorldManager", "An IOException occured during the handling of '"+id+"' #CW-002");
            }

        }
    }
}
