package com.simplechest.events;

import com.simplechest.SimplEchest;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class InventoryClick implements Listener {

    @EventHandler
    public static void InventoryClickEvent(InventoryClickEvent e){
        ItemStack item = e.getCurrentItem();

        if(item!=null && e.isRightClick() && item.getType().equals(Material.ENDER_CHEST)){
            HumanEntity human = e.getWhoClicked();
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
