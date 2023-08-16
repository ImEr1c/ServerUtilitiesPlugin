package com.batmanatorul.serverutilities.suggestion.admin.inspect;

import com.batmanatorul.serverutilities.PlayerMessages;
import com.batmanatorul.serverutilities.ServerUtilities;
import com.batmanatorul.serverutilities.suggestion.admin.SuggestionsInventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class SuggestionInspectEvents implements Listener {

    private static final Map<UUID, Inventory> players = new HashMap<>();

    @EventHandler
    public void click(InventoryClickEvent event) {

        Inventory inventory = event.getClickedInventory();

        if (!(inventory.getHolder() instanceof SuggestionInspectInventoryHolder inspectInventoryHolder))
            return;

        event.setCancelled(true);

        ItemStack clicked = event.getCurrentItem();

        if (clicked.getType() == Material.ITEM_FRAME) {
            HumanEntity humanEntity = event.getWhoClicked();

            if (clicked.getItemMeta().getLore().get(0).equals("None")) {
                humanEntity.sendMessage("This suggestion doesn't have any image");
            } else {
                players.put(humanEntity.getUniqueId(), inventory);

                humanEntity.sendMessage(inspectInventoryHolder.getImageLink());
                humanEntity.sendMessage("Send any message in chat to reopen the suggestion");
                humanEntity.closeInventory();
            }
        }

        if (clicked.getType() == Material.NAME_TAG) {
            HumanEntity humanEntity = event.getWhoClicked();
            players.put(humanEntity.getUniqueId(), inventory);

            humanEntity.sendMessage(inspectInventoryHolder.getDescription());
            humanEntity.sendMessage("Send any message in chat to reopen the suggestion");
            humanEntity.closeInventory();
        }

        if (clicked.getType() == Material.GREEN_CONCRETE) {
            HumanEntity humanEntity = event.getWhoClicked();

            SuggestionsInventoryHolder.suggestions.remove(inspectInventoryHolder.getSuggestion());
            PlayerMessages.addNew(inspectInventoryHolder.getPlayer(), "Your suggestion has been approved: " + inspectInventoryHolder.getName());
            humanEntity.closeInventory();
        }

    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();

        if (!players.containsKey(player.getUniqueId()))
            return;

        event.setCancelled(true);

        Inventory inventory = players.get(player.getUniqueId());

        Bukkit.getScheduler().runTask(ServerUtilities.getInstance(), bukkitTask -> {
            player.openInventory(inventory);
        });

        players.remove(player.getUniqueId());
    }
}
