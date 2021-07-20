package com.simplechest.ui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;


public class EnderChestOpen implements Listener {

    @EventHandler
    public static void InventoryClickEvent(InventoryClickEvent e){
        String title = e.getView().getTitle().split(" ")[0];
        if(title.equalsIgnoreCase(ChatColor.RED+"Page")){
            e.setCancelled(true);
            ItemStack clickedItem = e.getCurrentItem();
            if(clickedItem!=null){
                if(clickedItem.getType().equals(Material.PLAYER_HEAD)){
                    HumanEntity player = e.getWhoClicked();
                    for(Player target : Bukkit.getOnlinePlayers()){
                        if(target.getDisplayName().equalsIgnoreCase(clickedItem.getItemMeta().getDisplayName())){
                            player.openInventory(target.getEnderChest());
                        }
                    }
                }else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA+""+ChatColor.BOLD+"Next")){
                    HumanEntity player = e.getWhoClicked();
                    player.openInventory(InventoryHandler.nextPage(e.getView()));
                }else if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA+""+ChatColor.BOLD+"Back")){
                    HumanEntity player = e.getWhoClicked();
                    player.openInventory(InventoryHandler.previousPage(e.getView()));
                }
            }
        }
    }

}
