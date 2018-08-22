package com.buildtools.BuildServerCore;

import com.buildtools.BuildServerCore.Commands.*;
import com.buildtools.BuildServerCore.CustomClasses.ComponentTeleport;
import com.buildtools.BuildServerCore.CustomClasses.ComponentUI;
import com.buildtools.BuildServerCore.CustomClasses.ComponentWorld;
import com.buildtools.BuildServerCore.CustomClasses.Generators.FlatWorld;
import com.buildtools.BuildServerCore.CustomClasses.Generators.VoidWorld;
import com.buildtools.BuildServerCore.Events.EventHandler;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class Main extends JavaPlugin{

    public static Main plugin;

    public static ComponentWorld worldComponent;
    public static ComponentTeleport teleportComponent;
    public static ComponentUI uiComponent;

    public static Map<String, ChunkGenerator> translationTable;

    @Override
    public void onEnable() {
        plugin=this;
        worldComponent = new ComponentWorld();
        teleportComponent = new ComponentTeleport();
        uiComponent = new ComponentUI();

        translationTable = new HashMap<String, ChunkGenerator>();
        translationTable.put("void", new VoidWorld());
        translationTable.put("flat", new FlatWorld());

        plugin.getCommand("createmap").setExecutor(new CreateWorldCommand());
        plugin.getCommand("enablemap").setExecutor(new MapEnableCommand());
        plugin.getCommand("disablemap").setExecutor(new MapDisableCommand());
        plugin.getCommand("tptoworld").setExecutor(new WorldTPCommand());
        plugin.getCommand("map").setExecutor(new MapManagerCommand());
        plugin.getCommand("hub").setExecutor(new HubCommand());
        plugin.getCommand("speed").setExecutor(new SpeedCommand());

        getServer().getPluginManager().registerEvents(new EventHandler(), plugin);
        getServer().getLogger().log(Level.INFO, "Launched plugin");
    }

    @Override
    public void onDisable() {
        for (World world: Bukkit.getWorlds()) {
            if(!(world.getName().equals( Bukkit.getWorlds().get(0).getName() ))){
                Main.worldComponent.unloadMap(world.getName());
            }
        }
    }
}
