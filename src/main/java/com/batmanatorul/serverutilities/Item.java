package com.batmanatorul.serverutilities;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Item {

    private final Material material;
    private String name;
    private List<String> lore;

    public Item(Material material) {
        this.material = material;
    }

    public Item name(String name) {
        this.name = name;
        return this;
    }

    public Item lore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemStack build() {
        ItemStack itemStack = new ItemStack(material);

        ItemMeta meta = itemStack.getItemMeta();

        if (name != null)
            meta.setDisplayName(name);

        if (lore != null)
            meta.setLore(lore);

        itemStack.setItemMeta(meta);

        return itemStack;
    }

}
