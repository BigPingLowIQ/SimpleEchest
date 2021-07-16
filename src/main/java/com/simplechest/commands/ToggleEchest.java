package com.simplechest.commands;

import com.simplechest.SimplEchest;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ToggleEchest implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){return true;}

        Player player = (Player) sender;
        PersistentDataContainer data = player.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(SimplEchest.getInstance(),"canOpenECHEST");

        if(data.has(key,PersistentDataType.BYTE)) {
            if (data.get(key, PersistentDataType.BYTE) == (byte) 1) {
                data.set(key, PersistentDataType.BYTE, (byte) 0);
                sender.sendMessage(ChatColor.RED+"Inventory open echest has been disabled.");
            } else {
                data.set(key, PersistentDataType.BYTE, (byte) 1);
                sender.sendMessage(ChatColor.GREEN+"Inventory open echest has been enabled.");
            }
        }
        return true;
    }
}
