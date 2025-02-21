package io.anthills.listeners;

import io.anthills.classes.Cell;
import io.anthills.classes.CellPosition;
import io.anthills.events.CellMoveEvent;
import io.anthills.managers.CellTracker;
import io.anthills.managers.EffectManager;
import io.anthills.managers.GlobalCache;
import io.anthills.managers.PheromoneManager;
import io.anthills.managers.SidebarManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CellMoveListener implements Listener {

    @EventHandler
    public void onCellChange(CellMoveEvent event) {
        Player player = event.getPlayer();
        CellPosition cellPosition = event.getToCellPosition();

        CellTracker.updatePlayerCell(player, cellPosition);

        Cell cell = GlobalCache.getCell(cellPosition);

        SidebarManager.updateAndDisplay(player, cell);
        EffectManager.processPlayer(player, cell);
        PheromoneManager.processCell(cell);
    }
}