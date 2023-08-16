package com.batmanatorul.serverutilities.report;

import com.batmanatorul.serverutilities.ServerUtilities;
import com.batmanatorul.serverutilities.Utils;
import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class ReportCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length < 2) {
            sender.sendMessage("Usage: /report player <player name> or /report bug <bug name>");
            return true;
        }

        if (!(sender instanceof Player player))
            return true;

        UUID reporterUUID = player.getUniqueId();
        UUID reportedPlayer = null;
        String bugName = null;

        boolean playerReport = args[0].equals("player");

        if (playerReport)
            reportedPlayer = Bukkit.getServer().getPlayer(args[1]).getUniqueId();
        else
            bugName = Utils.combine(1, args);

        Report report = playerReport ? new Report(reporterUUID, reportedPlayer) : new Report(reporterUUID, bugName);

        player.openInventory(playerReport ? new ReportInventoryHolder(reportedPlayer, report).getInventory() : new ReportInventoryHolder(bugName, report)
                .getInventory());

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return args.length == 1 ? ImmutableList.of("player", "bug") : args.length == 2 && args[0].equals("player") ? null : ImmutableList.of();
    }
}
