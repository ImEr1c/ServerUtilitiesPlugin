package com.batmanatorul.serverutilities.commands;

import com.batmanatorul.serverutilities.ServerUtilities;
import com.batmanatorul.serverutilities.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DiscordCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        sender.sendMessage(Utils.color(ServerUtilities.getInstance().getConfig().getString("discord-msg")));
        return true;
    }
}
