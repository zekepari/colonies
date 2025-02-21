package io.anthills.listeners;

import io.anthills.classes.Cell;
import io.anthills.events.CellUpdateEvent;
import io.anthills.managers.CellTracker;
import io.anthills.managers.EffectManager;
import io.anthills.managers.SidebarManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CellUpdateListener implements Listener {

    @EventHandler
    public void onCellChange(CellUpdateEvent event) {
        Cell cell = event.getCell();

        CellTracker.getPlayersInCell(cell).forEach(player -> {
            SidebarManager.updateAndDisplay(player, cell);
            EffectManager.processPlayer(player, cell);
        });
    }
}
