package io.anthills.events;

import io.anthills.classes.CellPosition;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CellMoveEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final CellPosition fromCell;
    private final CellPosition toCell;

    public CellMoveEvent(Player player, CellPosition fromCell, CellPosition toCell) {
        this.player = player;
        this.fromCell = fromCell;
        this.toCell = toCell;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }

    public CellPosition getFromCellPosition() {
        return fromCell;
    }

    public CellPosition getToCellPosition() {
        return toCell;
    }
}
