package com.simplechest;

import com.simplechest.commands.EnderChest;
import com.simplechest.commands.EnderChestUI;
import com.simplechest.commands.ToggleEchest;
import com.simplechest.events.InventoryClick;
import com.simplechest.events.PlayerInteract;
import com.simplechest.events.PlayerJoin;
import com.simplechest.ui.EnderChestOpen;
import com.simplechest.ui.HeadHolder;
import com.simplechest.ui.InventoryHandler;
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

        new UpdateChecker(instance,94336);

        int pluginId = 12088;
        Metrics metrics = new Metrics(instance,pluginId);



        loadPlayers();

        getServer().getPluginManager().registerEvents(new InventoryClick(),instance);
        getServer().getPluginManager().registerEvents(new PlayerJoin(),instance);
        getServer().getPluginManager().registerEvents(new PlayerInteract(),instance);
        getServer().getPluginManager().registerEvents(new HeadHolder(),instance);
        getServer().getPluginManager().registerEvents(new EnderChestOpen(),instance);

        getCommand("togglesimpleechest").setExecutor(new ToggleEchest());
        getCommand("enderchest").setExecutor(new EnderChest());
        getCommand("enderchestlist").setExecutor(new EnderChestUI());

        HeadHolder.refreshAllSkulls();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        InventoryHandler.setup();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        InventoryHandler.cancelScheduler();
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
