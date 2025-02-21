package io.anthills.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import io.anthills.classes.CellPosition;
import io.anthills.events.CellMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getFrom() == null || event.getTo() == null)
            return;

        Player player = event.getPlayer();
        CellPosition fromCellPosition = new CellPosition(event.getFrom());
        CellPosition toCellPosition = new CellPosition(event.getTo());

        if (!fromCellPosition.equals(toCellPosition)) {
            Bukkit.getPluginManager().callEvent(new CellMoveEvent(player, fromCellPosition, toCellPosition));
        }
    }
}
