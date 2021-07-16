package com.simplechest.events;

import com.simplechest.SimplEchest;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerInteract implements Listener {

    @EventHandler
    public static void PlayerInteractEvent(PlayerInteractEvent e){
        if(e.getAction().equals(Action.RIGHT_CLICK_AIR)){

            Player human = e.getPlayer();
            ItemStack item = human.getInventory().getItemInMainHand();

            if(item.getType().equals(Material.ENDER_CHEST)){

                if(human.hasPermission("simplechest.inventoryenderchest")) {
                    PersistentDataContainer data = human.getPersistentDataContainer();
                    byte Byte = data.getOrDefault(new NamespacedKey(SimplEchest.getInstance(),"canOpenECHEST"), PersistentDataType.BYTE,(byte) 0);
                    if(Byte == (byte) 1) {
                        human.openInventory(human.getEnderChest());
                    }
                }
            }

        }
    }

}
