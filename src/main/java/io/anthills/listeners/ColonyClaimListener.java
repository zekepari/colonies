package io.anthills.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import io.anthills.classes.PheroCell;
import io.anthills.events.ColonyClaimEvent;
import io.anthills.managers.SidebarManager;
import io.anthills.utils.CellUtils;

public class ColonyClaimListener implements Listener {
    @EventHandler
    public void onCellChange(ColonyClaimEvent event) {
        PheroCell cell = event.getPheroCell();

        CellUtils.getPlayersInCell(cell).forEach(player -> {
            SidebarManager.getSidebarManager(player).updateAndDisplay(player, cell);
        });
    }
}
