package com.simplechest.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class EnderChest implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){return true;}

        Player player = (Player) sender;

        if(args.length==0 && player.hasPermission("simplechest.enderchest")){
            player.openInventory(player.getEnderChest());
        }else if(args.length==1 && player.hasPermission("simplechest.enderchest.invsee")){
            Player target = Bukkit.getServer().getPlayer(args[0]);
            if(target!=null){
                player.openInventory(target.getEnderChest());
            }
        }

        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> players = new ArrayList<>();
        if(sender.hasPermission("simplechest.enderchest.invsee")){
            for(Player player : Bukkit.getServer().getOnlinePlayers()){
                players.add(player.getDisplayName());
            }
        }
        return players;
    }
}
