package com.buildtools.BuildServerCore;

import com.buildtools.BuildServerCore.Commands.CreateWorldCommand;
import com.buildtools.BuildServerCore.Commands.MapDisableCommand;
import com.buildtools.BuildServerCore.Commands.MapEnableCommand;
import com.buildtools.BuildServerCore.Commands.WorldTPCommand;
import com.buildtools.BuildServerCore.CustomClasses.BuildWorld;
import com.buildtools.BuildServerCore.CustomClasses.Component;
import com.buildtools.BuildServerCore.CustomClasses.ComponentTeleport;
import com.buildtools.BuildServerCore.CustomClasses.ComponentWorld;
import com.buildtools.BuildServerCore.Events.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class Main extends JavaPlugin{

    public static Main plugin;

    public static ComponentWorld worldComponent;
    public static ComponentTeleport teleportComponent;

    @Override
    public void onEnable() {
        plugin=this;
        worldComponent = new ComponentWorld();
        teleportComponent = new ComponentTeleport();

        plugin.getCommand("createmap").setExecutor(new CreateWorldCommand());
        plugin.getCommand("enablemap").setExecutor(new MapEnableCommand());
        plugin.getCommand("disablemap").setExecutor(new MapDisableCommand());
        plugin.getCommand("tptoworld").setExecutor(new WorldTPCommand());

        getServer().getPluginManager().registerEvents(new EventHandler(), plugin);
        getServer().getLogger().log(Level.INFO, "Launched plugin");
    }

    @Override
    public void onDisable() {
        for (BuildWorld world: worldComponent.worldList.values()) {
            if(world.isLoadedOnServer()){
                world.unloadWorld();
            }
        }
    }
}
