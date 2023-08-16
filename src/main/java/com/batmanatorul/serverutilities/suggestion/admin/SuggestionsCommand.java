package com.batmanatorul.serverutilities.suggestion.admin;

import com.google.common.collect.ImmutableList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

public class SuggestionsCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player))
            return true;

        player.openInventory(new SuggestionsInventoryHolder().getInventory());

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return ImmutableList.of();
    }
}
