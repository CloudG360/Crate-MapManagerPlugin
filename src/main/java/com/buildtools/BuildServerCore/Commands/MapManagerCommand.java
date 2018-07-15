package com.buildtools.BuildServerCore.Commands;

import com.buildtools.BuildServerCore.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.DoubleChest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MapManagerCommand implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){


        if(commandSender instanceof Player){

            Player player = (Player) commandSender;

            Main.uiComponent.openUI(player, "test");
        }

        return true;
    }
}
