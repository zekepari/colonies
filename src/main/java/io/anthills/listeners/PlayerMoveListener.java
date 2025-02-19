package io.anthills.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import io.anthills.classes.Cell;
import io.anthills.events.CellChangeEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getFrom() == null || event.getTo() == null)
            return;

        Player player = event.getPlayer();
        Cell fromCell = new Cell(event.getFrom());
        Cell toCell = new Cell(event.getTo());

        if (!fromCell.equals(toCell)) {
            Bukkit.getPluginManager().callEvent(new CellChangeEvent(player, fromCell, toCell));
        }
    }
}
