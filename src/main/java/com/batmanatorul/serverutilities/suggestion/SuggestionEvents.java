package com.batmanatorul.serverutilities.suggestion;

import com.batmanatorul.serverutilities.ServerUtilities;
import com.batmanatorul.serverutilities.Tuple;
import com.batmanatorul.serverutilities.suggestion.admin.SuggestionsInventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SuggestionEvents implements Listener {

    private final Map<UUID, Tuple<Inventory, Integer>> inventoryMap = new HashMap<>();

    @EventHandler
    public void click(InventoryClickEvent event) {

        Inventory inventory = event.getClickedInventory();

        if (inventory.getHolder() == null || !(inventory.getHolder() instanceof SuggestionInventoryHolder holder))
            return;

        ItemStack clicked = event.getCurrentItem();

        event.setCancelled(true);

        if (clicked.getType() == Material.BOOK || clicked.getType() == Material.ITEM_FRAME) {
            HumanEntity humanEntity = event.getWhoClicked();

            inventoryMap.put(humanEntity.getUniqueId(), new Tuple<>(inventory, event.getSlot()));

            humanEntity.closeInventory();
            humanEntity.sendMessage(clicked.getType() == Material.BOOK ? "Type the text that you want to be in that text slot or type cancel to cancel"
                    : "Type the link for the image that you want in that image slot or type cancel to cancel");
        }

        if (clicked.getType() == Material.GREEN_STAINED_GLASS_PANE) {
            SuggestionsInventoryHolder.suggestions.add(holder.finish());

            HumanEntity humanEntity = event.getWhoClicked();

            humanEntity.closeInventory();
            humanEntity.sendMessage("Suggestions has been sent successfully!");
        }

    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();

        if (!inventoryMap.containsKey(player.getUniqueId()))
            return;

        event.setCancelled(true);

        String message = event.getMessage();

        Tuple<Inventory, Integer> tuple = inventoryMap.get(player.getUniqueId());

        Inventory inventory = tuple.getA();

        int i = tuple.getB();

        if (message.equalsIgnoreCase("cancel")) {
            Bukkit.getScheduler().runTask(ServerUtilities.getInstance(), bukkitTask -> {
                player.openInventory(inventory);
            });
        } else {

            if (inventory.getHolder() instanceof SuggestionInventoryHolder holder) {

                holder.set(i, event.getMessage());

                Bukkit.getScheduler().runTask(ServerUtilities.getInstance(), bukkitTask -> {
                    player.openInventory(holder.getInventory());
                });

            }

        }

        inventoryMap.remove(player.getUniqueId());
    }
}
