package com.batmanatorul.serverutilities.suggestion.admin;

import com.batmanatorul.serverutilities.suggestion.admin.inspect.SuggestionInspectInventoryHolder;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SuggestionsEvents implements Listener {

    @EventHandler
    public void click(InventoryClickEvent event) {

        Inventory inventory = event.getClickedInventory();

        if (inventory.getHolder() == null || !(inventory.getHolder() instanceof SuggestionsInventoryHolder))
            return;

        event.setCancelled(true);

        ItemStack clicked = event.getCurrentItem();

        if (clicked.getType() == Material.BLUE_CONCRETE) {
            int line = event.getSlot() / 9;

            int i = event.getSlot() - (line == 0 ? 0 : line * 2) - 1;

            event.getWhoClicked().openInventory(new SuggestionInspectInventoryHolder(SuggestionsInventoryHolder.suggestions.get(i)).getInventory());

        }

    }
}
