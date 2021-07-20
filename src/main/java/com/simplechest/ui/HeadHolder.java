package com.simplechest.ui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class HeadHolder implements Listener {
    private static final ConcurrentHashMap<String,ItemStack> skulls = new ConcurrentHashMap<>();

    @EventHandler
    public static void PlayerJoinEvent(PlayerJoinEvent e){
        Player player = e.getPlayer();
        new Thread(() -> {
            skulls.put(player.getUniqueId().toString(),getPlayerHead(player.getDisplayName(),player.getUniqueId()));
        }).start();
    }

    @EventHandler
    public static void PlayerQuitEvent(PlayerQuitEvent e){
        skulls.remove(e.getPlayer().getUniqueId().toString());
    }


    private static ItemStack getPlayerHead(String player,UUID uuid){

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD); // Create a new ItemStack of the Player Head type.
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta(); // Get the created item's ItemMeta and cast it to SkullMeta so we can access the skull properties
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid)); // Set the skull's owner so it will adapt the skin of the provided username (case sensitive).
        skullMeta.setDisplayName(player);
        skull.setItemMeta(skullMeta); // Apply the modified meta to the initial created item


        return skull;
    }

    public static ConcurrentHashMap<String,ItemStack> getSkulls(){
        return skulls;
    }

    public static void refreshAllSkulls(){
        skulls.clear();
        for(Player player : Bukkit.getOnlinePlayers()){
            new Thread(() -> {
                skulls.put(player.getUniqueId().toString(),getPlayerHead(player.getDisplayName(),player.getUniqueId()));
            }).start();
        }
    }

}
