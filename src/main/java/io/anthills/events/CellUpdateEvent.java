package io.anthills.events;

import io.anthills.classes.Cell;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CellUpdateEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final Cell cell;

    public CellUpdateEvent(Cell cell) {
        this.cell = cell;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Cell getCell() {
        return cell;
    }
}
