package com.batmanatorul.serverutilities;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static void fillEmptySlots(Inventory inventory, Material material) {

        ItemStack fillWith = new ItemStack(material);
        ItemMeta meta = fillWith.getItemMeta();
        meta.setDisplayName("");
        fillWith.setItemMeta(meta);

        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null)
                inventory.setItem(i, fillWith.clone());
        }
    }

    public static String combine(int start, String... strings) {
        StringBuilder builder = new StringBuilder();

        for (int i = start; i < strings.length; i++) {
            builder.append(strings[i]).append(" ");
        }

        return builder.toString();
    }

    public static String color(String s) {
        for (ChatColor value : ChatColor.values()) {
            s = s.replace("&" + value.getChar(), value.toString());
        }

        return s;
    }

    public static List<String> formatLore(String... strings) {

        List<String> list = new ArrayList<>();

        for (String string : strings) {

            if (string.length() > 40) {

                int i = 0;

                while (i < string.length()) {
                    String s = string.substring(i, Math.min(string.length(), i + 40));

                    list.add(ChatColor.RESET.toString() + ChatColor.WHITE + s);

                    i += 40;
                }

            } else {
                list.add(ChatColor.RESET.toString() + ChatColor.WHITE + string);
            }

        }

        return list;
    }
}
