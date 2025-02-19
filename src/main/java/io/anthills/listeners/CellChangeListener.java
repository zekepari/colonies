package io.anthills.listeners;

import io.anthills.classes.Cell;
import io.anthills.events.CellChangeEvent;
import io.anthills.managers.PheromoneManager;
import io.anthills.managers.SidebarManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CellChangeListener implements Listener {
    @EventHandler
    public void onCellChange(CellChangeEvent event) {
        Player player = event.getPlayer();
        Cell cell = event.getToCell();

        SidebarManager.getSidebarManager(player).updateAndDisplay(player, cell);
        PheromoneManager.processCell(player, cell);
    }
}