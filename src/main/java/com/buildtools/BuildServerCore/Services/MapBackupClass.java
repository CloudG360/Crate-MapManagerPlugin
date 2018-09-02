package com.buildtools.BuildServerCore.Services;

import com.buildtools.BuildServerCore.Main;
import org.bukkit.World;

import java.util.concurrent.TimeUnit;

public class MapBackupClass extends Thread {

    @Override
    public void run(){
        while(true) {
            try {
                TimeUnit.MINUTES.sleep(20);
                for (World world : Main.plugin.getServer().getWorlds()) {
                    if (Main.worldComponent.getWorldType(world.getName()).toString().contains("ACTIVE")) {
                        Main.worldComponent.backupMap(world.getName());
                    }
                }
            } catch (InterruptedException err){
                return;
            }
        }
    }

}
