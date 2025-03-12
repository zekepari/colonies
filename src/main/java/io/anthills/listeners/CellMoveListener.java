package io.anthills.listeners;

import io.anthills.classes.Cell;
import io.anthills.classes.CellPosition;
import io.anthills.events.CellMoveEvent;
import io.anthills.managers.EffectManager;
import io.anthills.managers.PheromoneManager;
import io.anthills.managers.SidebarManager;
import io.anthills.managers.data.CellTracker;
import io.anthills.managers.data.GlobalCache;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CellMoveListener implements Listener {

    @EventHandler
    public void onCellChange(CellMoveEvent event) {
        Player player = event.getPlayer();
        CellPosition cellPosition = event.getToCellPosition();
        Cell cell = GlobalCache.getCell(cellPosition);

        CellTracker.updatePlayerCell(player, cellPosition);
        SidebarManager.updatePlayerBoard(player, cell);
        EffectManager.processPlayer(player, cell);

        if (cell.getCellY() <= 18)
            PheromoneManager.processCell(cell);
    }
}