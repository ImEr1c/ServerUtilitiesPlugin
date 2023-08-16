package com.batmanatorul.serverutilities;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.*;

public class PlayerMessages implements Listener {
    private static final Map<UUID, List<String>> map = new HashMap<>();

    @EventHandler
    public void join(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        if (!map.containsKey(player.getUniqueId()))
            return;

        map.get(player.getUniqueId()).forEach(player::sendMessage);
        map.get(player.getUniqueId()).clear();
    }

    public static void addNew(UUID uuid, String message) {

        if (!map.containsKey(uuid))
            map.put(uuid, new ArrayList<>());

        map.get(uuid).add(message);
    }

    public static void addAll(List<Tuple<UUID, List<String>>> list) {
        list.forEach(uuidListTuple -> map.put(uuidListTuple.getA(), uuidListTuple.getB()));
    }

    public static List<Tuple<UUID, List<String>>> toList() {
        List<Tuple<UUID, List<String>>> list = new ArrayList<>();

        map.forEach((uuid, strings) -> list.add(new Tuple<>(uuid, strings)));

        return list;
    }
}
