package io.anthills.listeners;

import io.anthills.classes.Cell;
import io.anthills.events.CellUpdateEvent;
import io.anthills.managers.EffectManager;
import io.anthills.managers.SidebarManager;
import io.anthills.managers.data.CellTracker;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CellUpdateListener implements Listener {

    @EventHandler
    public void onCellChange(CellUpdateEvent event) {
        Cell cell = event.getCell();

        CellTracker.getPlayersInCell(cell).forEach(player -> {
            SidebarManager.updatePlayerBoard(player, cell);
            EffectManager.processPlayer(player, cell);
        });
    }
}
