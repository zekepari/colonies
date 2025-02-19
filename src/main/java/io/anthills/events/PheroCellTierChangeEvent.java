package io.anthills.events;

import io.anthills.classes.PheroCell;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PheroCellTierChangeEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final PheroCell pheroCell;
    private final int oldTier;
    private final int newTier;

    public PheroCellTierChangeEvent(PheroCell pheroCell, int oldTier, int newTier) {
        this.pheroCell = pheroCell;
        this.oldTier = oldTier;
        this.newTier = newTier;
    }

    public PheroCell getPheroCell() {
        return pheroCell;
    }

    public int getOldTier() {
        return oldTier;
    }

    public int getNewTier() {
        return newTier;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
