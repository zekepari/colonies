package io.anthills.events;

import io.anthills.classes.Cell;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CellChangeEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final Cell fromCell;
    private final Cell toCell;

    public CellChangeEvent(Player player, Cell fromCell, Cell toCell) {
        this.player = player;
        this.fromCell = fromCell;
        this.toCell = toCell;
    }

    public Player getPlayer() {
        return player;
    }

    public Cell getFromCell() {
        return fromCell;
    }

    public Cell getToCell() {
        return toCell;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
