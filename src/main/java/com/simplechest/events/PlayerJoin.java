package com.simplechest.events;

import com.simplechest.SimplEchest;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

import javax.lang.model.type.PrimitiveType;
import javax.naming.Name;

public class PlayerJoin implements Listener {

    @EventHandler
    public static void PlayerJoinEvent(PlayerJoinEvent e){
        Player player = e.getPlayer();
        PersistentDataContainer data = player.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(SimplEchest.getInstance(),"canOpenECHEST");
        if(!(data.has(key, PersistentDataType.BYTE))){
            data.set(key,PersistentDataType.BYTE,(byte) 1);
        }
    }
}
