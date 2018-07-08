package com.buildtools.BuildServerCore.Commands;

import com.buildtools.BuildServerCore.CustomClasses.Generators.FlatWorld;
import com.buildtools.BuildServerCore.CustomClasses.Generators.VoidWorld;
import com.buildtools.BuildServerCore.Main;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;

import java.util.HashMap;
import java.util.Map;

public class CreateWorldCommand implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){


        if(commandSender instanceof Player){

            Main.worldComponent.createMap(strings[0], ((Player) commandSender).getDisplayName(), strings[1], strings[2]);
        }

        return true;
    }
}
