package com.batmanatorul.serverutilities.suggestion.admin.inspect;

import com.batmanatorul.serverutilities.Item;
import com.batmanatorul.serverutilities.Utils;
import com.batmanatorul.serverutilities.suggestion.Suggestion;
import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class SuggestionInspectInventoryHolder implements InventoryHolder {

    private final Inventory inventory;
    private final Suggestion suggestion;

    public SuggestionInspectInventoryHolder(Suggestion suggestion) {
        this.inventory = Bukkit.createInventory(this, 54, "Suggestion inspecter");
        this.suggestion = suggestion;
        init();
    }

    private void init() {

        ItemStack suggestionItem = new Item(Material.NAME_TAG).name(suggestion.getName()).lore(suggestion.getDescription()).build();

        inventory.setItem(4, suggestionItem);

        ItemStack image = new Item(Material.ITEM_FRAME).name("Screenshot").lore(ImmutableList.of(suggestion.getImage() == null || suggestion.getImage().equals("") ? "None" : suggestion.getImage())).build();

        inventory.setItem(13, image);

        ItemStack accept = new Item(Material.GREEN_CONCRETE).name("Approve").lore(ImmutableList.of(ChatColor.GREEN + "Left click to approve this suggestion")).build();

        inventory.setItem(46, accept);

        Utils.fillEmptySlots(inventory, Material.GRAY_STAINED_GLASS_PANE);
    }

    public Suggestion getSuggestion() {
        return suggestion;
    }

    public String getName() {
        return suggestion.getName();
    }

    public String getImageLink() {
        return suggestion.getImage();
    }

    public String getDescription() {
        return Utils.combine(0, suggestion.getDescription().toArray(String[]::new));
    }

    public UUID getPlayer() {
        return suggestion.getPlayer();
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
