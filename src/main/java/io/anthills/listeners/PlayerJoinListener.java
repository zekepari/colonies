package io.anthills.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import io.anthills.classes.CellPosition;
import io.anthills.events.CellMoveEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        CellPosition cellPosition = new CellPosition(player.getLocation());

        Bukkit.getPluginManager().callEvent(new CellMoveEvent(player, null, cellPosition));
    }
}
