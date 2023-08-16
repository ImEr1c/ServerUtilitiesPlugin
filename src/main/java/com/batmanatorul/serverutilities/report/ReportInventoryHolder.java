package com.batmanatorul.serverutilities.report;

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

public class ReportInventoryHolder implements InventoryHolder {

    private final Inventory inventory;
    private final String name;
    private final Report report;
    private final List<String> text = new ArrayList<>(5);

    public ReportInventoryHolder(String bugName, Report report) {
        this.inventory = Bukkit.createInventory(this, 36, "Report");
        this.report = report;
        this.name = "Bug Report: " + bugName;
        init();
    }

    public ReportInventoryHolder(UUID reportedPlayer, Report report) {
        this.inventory = Bukkit.createInventory(this, 36, "Report");
        this.report = report;
        this.name = "Player Report: " + Bukkit.getServer().getPlayer(reportedPlayer).getName();
        init();
    }

    private void init() {
        ItemStack name = new Item(Material.NAME_TAG).name(this.name).build();

        inventory.setItem(4, name);

        ItemStack reporter = new Item(Material.NAME_TAG).name("Author: " + Bukkit.getServer().getPlayer(report.getReporter()).getName()).build();

        inventory.setItem(27, reporter);

        for (int i = 0; i < 5; i++) {
            Item textSlot = new Item(Material.BOOK).name("Text Slot #" + (i + 1));

            if (text.size() > i && text.get(i) != null)
                textSlot.lore(ImmutableList.of(text.get(i)));

            inventory.setItem(11 + i, textSlot.build());
        }

        ItemStack confirm = new Item(Material.GREEN_STAINED_GLASS_PANE).name("Send Report").build();

        inventory.setItem(35, confirm);

        Utils.fillEmptySlots(inventory, Material.GRAY_STAINED_GLASS_PANE);
    }

    public void set(int i, String text) {

        int index = i - 11;

        if (this.text.size() > index)
            this.text.set(index, text);
        else
            this.text.add(index, text);

        inventory.clear();
        init();
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
