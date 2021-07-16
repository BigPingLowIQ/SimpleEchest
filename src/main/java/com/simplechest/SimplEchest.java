package com.simplechest;

import com.simplechest.commands.EnderChest;
import com.simplechest.commands.ToggleEchest;
import com.simplechest.events.InventoryClick;
import com.simplechest.events.PlayerInteract;
import com.simplechest.events.PlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimplEchest extends JavaPlugin {
    private static SimplEchest instance;


    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        loadPlayers();

        getServer().getPluginManager().registerEvents(new InventoryClick(),this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(),this);
        getServer().getPluginManager().registerEvents(new PlayerInteract(),this);
        getCommand("togglesimpleechest").setExecutor(new ToggleEchest());
        getCommand("enderchest").setExecutor(new EnderChest());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static SimplEchest getInstance(){
        return instance;
    }

    private static void loadPlayers(){
        for(Player player : Bukkit.getOnlinePlayers()){
            PersistentDataContainer data = player.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(SimplEchest.getInstance(),"canOpenECHEST");
            if(!(data.has(key, PersistentDataType.BYTE))){
                data.set(key,PersistentDataType.BYTE,(byte) 1);
            }
        }
    }

}
