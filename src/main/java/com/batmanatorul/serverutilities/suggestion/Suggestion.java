package com.batmanatorul.serverutilities.suggestion;

import com.batmanatorul.serverutilities.Utils;
import com.google.common.collect.ImmutableMap;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.*;

public class Suggestion implements ConfigurationSerializable {
    private final List<String> description;
    private final String name;
    private final UUID player;
    private final String image;

    public Suggestion(String name, UUID player, List<String> description, String image) {
        this.name = name;
        this.player = player;
        this.description = Utils.formatLore(description.toArray(String[]::new));
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public UUID getPlayer() {
        return player;
    }

    public List<String> getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    @Override
    public Map<String, Object> serialize() {
        return ImmutableMap.of("player", player.toString(), "name", name, "description", description, "image", image == null ? "" : image);
    }

    public static Suggestion deserialize(Map<String, Object> map) {
        return new Suggestion((String) map.get("name"), UUID.fromString((String) map.get("player")), (List<String>) map.get("description"), (String) map.get("image"));
    }
}
