package com.batmanatorul.serverutilities.suggestion;

import com.batmanatorul.serverutilities.Item;
import com.batmanatorul.serverutilities.Utils;
import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SuggestionInventoryHolder implements InventoryHolder {

    private final Inventory inventory;
    private final String name;
    private final UUID player;
    private final List<String> text = new ArrayList<>(3);
    private String image;

    public SuggestionInventoryHolder(String suggestionName, UUID player) {
        this.inventory = Bukkit.createInventory(this, 36, "Report");
        this.player = player;
        this.name = "Suggestion: " + suggestionName;
        init();
    }

    private void init() {
        ItemStack name = new Item(Material.NAME_TAG).name(this.name).build();

        inventory.setItem(4, name);

        ItemStack reporter = new Item(Material.NAME_TAG).name("Author: " + Bukkit.getServer().getPlayer(player).getName()).build();

        inventory.setItem(27, reporter);

        for (int i = 0; i < 3; i++) {
            Item textSlot = new Item(Material.BOOK).name("Text Slot #" + (i + 1));

            if (text.size() > i && text.get(i) != null)
                textSlot.lore(Utils.formatLore(text.get(i)));

            inventory.setItem(12 + i, textSlot.build());
        }

        Item image = new Item(Material.ITEM_FRAME).name("Image Slot");

        if (this.image != null)
            image.lore(ImmutableList.of(this.image));

        inventory.setItem(15, image.build());

        ItemStack confirm = new Item(Material.GREEN_STAINED_GLASS_PANE).name("Send Suggestion").build();

        inventory.setItem(35, confirm);

        Utils.fillEmptySlots(inventory, Material.GRAY_STAINED_GLASS_PANE);
    }

    public void set(int i, String text) {

        int index = i - 12;

        if (index < 3) {
            if (this.text.size() > index)
                this.text.set(index, text);
            else
                this.text.add(index, text);
        } else
            image = text;

        inventory.clear();
        init();
    }

    public Suggestion finish() {
        return new Suggestion(name, player, text, image);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
