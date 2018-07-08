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
            Map<String, ChunkGenerator> translationTable = new HashMap<String, ChunkGenerator>();
            translationTable.put("void", new VoidWorld());
            translationTable.put("flat", new FlatWorld());

            Main.worldComponent.createWorld(strings[0], translationTable.get(strings[1]), ((Player) commandSender).getDisplayName());
        }

        return true;
    }
}
