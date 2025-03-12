package io.anthills.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import io.anthills.classes.Ant;
import io.anthills.classes.CellPosition;
import io.anthills.classes.Cell;
import io.anthills.events.ColonyCreateEvent;
import io.anthills.managers.PheromoneManager;
import io.anthills.managers.data.CellTracker;
import io.anthills.managers.data.GlobalCache;

public class ColonyCreateListener implements Listener {

    @EventHandler
    public void onCellChange(ColonyCreateEvent event) {
        Ant queen = event.getColony().getQueen();
        Player player = queen.getPlayer();

        CellPosition cellPosition = CellTracker.getCellOfPlayer(player);
        Cell cell = GlobalCache.getCell(cellPosition);

        PheromoneManager.processCell(cell);
    }
}
