package io.anthills.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import io.anthills.classes.Cell;
import io.anthills.events.CellChangeEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Cell currentCell = new Cell(player.getLocation());

        Bukkit.getPluginManager().callEvent(new CellChangeEvent(player, null, currentCell));
    }
}
