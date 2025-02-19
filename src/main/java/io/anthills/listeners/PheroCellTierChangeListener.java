package io.anthills.listeners;

import io.anthills.classes.PheroCell;
import io.anthills.events.PheroCellTierChangeEvent;
import io.anthills.managers.SidebarManager;
import io.anthills.utils.CellUtils;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PheroCellTierChangeListener implements Listener {
    @EventHandler
    public void onCellChange(PheroCellTierChangeEvent event) {
        PheroCell cell = event.getPheroCell();

        CellUtils.getPlayersInCell(cell).forEach(player -> {
            SidebarManager.getSidebarManager(player).updateAndDisplay(player, cell);
        });
    }
}
