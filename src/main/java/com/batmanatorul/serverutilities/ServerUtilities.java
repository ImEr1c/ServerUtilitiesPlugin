package com.batmanatorul.serverutilities;

import com.batmanatorul.serverutilities.commands.DiscordCommand;
import com.batmanatorul.serverutilities.report.ReportCommand;
import com.batmanatorul.serverutilities.report.ReportEvents;
import com.batmanatorul.serverutilities.suggestion.SuggestCommand;
import com.batmanatorul.serverutilities.suggestion.Suggestion;
import com.batmanatorul.serverutilities.suggestion.SuggestionEvents;
import com.batmanatorul.serverutilities.suggestion.admin.SuggestionsCommand;
import com.batmanatorul.serverutilities.suggestion.admin.SuggestionsEvents;
import com.batmanatorul.serverutilities.suggestion.admin.SuggestionsInventoryHolder;
import com.batmanatorul.serverutilities.suggestion.admin.inspect.SuggestionInspectEvents;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class ServerUtilities extends JavaPlugin {

    private static ServerUtilities instance;

    public static ServerUtilities getInstance() {
        return instance;
    }

    private FileConfiguration suggestions;

    @Override
    public void onEnable() {
        ConfigurationSerialization.registerClass(Suggestion.class);

        this.suggestions = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "data.yml"));

        instance = this;

        getConfig().options().copyDefaults(true);

        getCommand("discord").setExecutor(new DiscordCommand());
        getCommand("report").setExecutor(new ReportCommand());
        getCommand("suggest").setExecutor(new SuggestCommand());
        getCommand("suggestions").setExecutor(new SuggestionsCommand());

        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new ReportEvents(), this);
        pluginManager.registerEvents(new SuggestionEvents(), this);
        pluginManager.registerEvents(new SuggestionsEvents(), this);
        pluginManager.registerEvents(new SuggestionInspectEvents(), this);
        pluginManager.registerEvents(new PlayerMessages(), this);

        SuggestionsInventoryHolder.suggestions = (List<Suggestion>) suggestions.getList("suggestions", new ArrayList<>());
        PlayerMessages.addAll((List<Tuple<UUID, List<String>>>) suggestions.getList("playermsgs", new ArrayList<>()));
    }

    @Override
    public void onDisable() {
        suggestions.set("suggestions", SuggestionsInventoryHolder.suggestions);
        suggestions.set("playermsgs", PlayerMessages.toList());

        try {
            suggestions.save(new File(getDataFolder(), "data.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
