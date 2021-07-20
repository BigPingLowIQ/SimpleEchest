package com.simplechest.ui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class InventoryHandler {
    private static final List<Inventory> inventories = new ArrayList<>();
    private static List<Player> playerList;
    private static ScheduledFuture<?> refreshScheduler = null;

    public static void setup(){

        for(int i=0;i<20;i++){
            inventories.add(createInventory(ChatColor.RED+"Page "+(i+1)));
        }
        setupScheduler();

    }

    public static void refreshPlayers(){


        int invNum = 10;
        playerList = new ArrayList<>(Bukkit.getOnlinePlayers());

        for(int i = 0;i<invNum;i++){
            fillGreen(inventories.get(i));
            loadSkulls(inventories.get(i));

            if(i==0){ //Add next button
                addNextButton(inventories.get(i));
            }else if(i==(invNum-1)){ //Add back button
                addBackButton(inventories.get(i));
            }else{//Add both buttons
                addButtons(inventories.get(i));
            }

        }

    }

    private static void addNextButton(Inventory inventory){
        ItemStack itemStack = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA+""+ChatColor.BOLD+"Next");
        itemStack.setItemMeta(meta);

        inventory.setItem(53,itemStack);
    }

    private static void addBackButton(Inventory inventory){
        ItemStack itemStack = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA+""+ChatColor.BOLD+"Back");
        itemStack.setItemMeta(meta);

        inventory.setItem(45,itemStack);
    }

    private static void addButtons(Inventory inventory){
        addNextButton(inventory);
        addBackButton(inventory);
    }

    private static void loadSkulls(Inventory inventory){
        ConcurrentHashMap<String,ItemStack> skulls = HeadHolder.getSkulls();

        List<Player> players;

        if(playerList.size()>28){
            players = playerList.subList(0,28);
            playerList = playerList.subList(28,playerList.size());
        }else{
            players = new ArrayList<>(playerList);
            playerList.clear();
        }

        int i = 10;
        for(Player player : players) {

            if(player!=null){


                ItemStack skull = skulls.get(player.getUniqueId().toString());
                if (i <= 16) {
                    inventory.setItem(i, skull);
                } else if (i >= 19 && i <= 25) {
                    inventory.setItem(i, skull);
                } else if (i >= 28 && i <= 34) {
                    inventory.setItem(i, skull);
                } else if (i >= 37) {
                    inventory.setItem(i, skull);
                }
                if (i == 16 || i == 25 || i == 34) {
                    i += 2;
                }

                i++;
            }
        }
    }


    private static Inventory createInventory(String title) {
        Inventory inv = Bukkit.createInventory(null,54, title);
        ItemStack itemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(" ");
        itemStack.setItemMeta(meta);



        ItemStack[] array = new ItemStack[inv.getSize()];
        Arrays.fill(array, itemStack);
        inv.setContents(array);

        itemStack = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        itemStack.setItemMeta(meta);

        fillGreen(inv);

        return inv;
    }

    private static void fillGreen(Inventory inv){
        ItemStack itemStack = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(" ");
        itemStack.setItemMeta(meta);
        for(int i = 10 ; i <= 43 ; i++){
            if(i<=16){
                inv.setItem(i,itemStack);
            }else if(i >= 19 && i <= 25){
                inv.setItem(i,itemStack);
            }else if(i >= 28 && i <= 34){
                inv.setItem(i,itemStack);
            }else if(i >= 37){
                inv.setItem(i,itemStack);
            }
        }
    }

    public static Inventory getInventory() {
        return inventories.get(0);
    }

    public static Inventory nextPage(InventoryView currentInventory) {
        String title = currentInventory.getTitle();
        int currentPage = Integer.parseInt(title.split(" ")[1]);

        return inventories.get(currentPage);
    }

    public static Inventory previousPage(InventoryView currentInventory) {
        String title = currentInventory.getTitle();
        int currentPage = Integer.parseInt(title.split(" ")[1]);

        return inventories.get(currentPage-2);
    }

    private static void setupScheduler(){
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable saveData = InventoryHandler::refreshPlayers;
        refreshScheduler = scheduler.scheduleAtFixedRate(saveData, 1,15, TimeUnit.SECONDS);
    }


    public static void cancelScheduler(){
        if(refreshScheduler!=null) {
            refreshScheduler.cancel(true);
        }
    }
}
