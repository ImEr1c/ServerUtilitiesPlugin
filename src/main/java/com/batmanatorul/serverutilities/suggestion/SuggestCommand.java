package com.batmanatorul.serverutilities.suggestion;

import com.batmanatorul.serverutilities.Utils;
import com.google.common.collect.ImmutableList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class SuggestCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length < 1) {
            sender.sendMessage("Usage: /suggest <suggestion name>");
            return true;
        }

        if (!(sender instanceof Player player))
            return true;

        UUID reporterUUID = player.getUniqueId();
        String suggestionName = Utils.combine(0, args);

        player.openInventory(new SuggestionInventoryHolder(suggestionName, reporterUUID).getInventory());

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return ImmutableList.of();
    }
}
