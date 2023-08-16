package com.batmanatorul.serverutilities.suggestion.admin;

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

import java.util.ArrayList;
import java.util.List;

public class SuggestionsInventoryHolder implements InventoryHolder {
    public static List<Suggestion> suggestions = new ArrayList<>();

    private final Inventory inventory;

    public SuggestionsInventoryHolder() {
        this.inventory = Bukkit.createInventory(this, 54, "Suggestions");
        init();
    }

    private void init() {

        int index = 0;

        for (int i = 0; i < 4; i++) {
            for (int i1 = 1; i1 < 8; i1++) {

                boolean doesHaveMore = index < suggestions.size();

                ItemStack stack;

                if (doesHaveMore) {
                    Suggestion suggestion = suggestions.get(index);

                    List<String> lore = ImmutableList.of(ChatColor.BLUE + "Player: " + Bukkit.getServer().getPlayer(suggestion.getPlayer()).getName(),
                            ChatColor.GREEN + "Left click to inspect");

                    stack = new Item(Material.BLUE_CONCRETE).name(suggestion.getName()).lore(lore).build();
                } else {
                    stack = new Item(Material.WHITE_CONCRETE).name("").build();
                }

                inventory.setItem(9 * i + i1, stack);

                index++;
            }
        }

        Utils.fillEmptySlots(inventory, Material.GRAY_STAINED_GLASS_PANE);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
