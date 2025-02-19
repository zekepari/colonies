package io.anthills.events;

import io.anthills.classes.Colony;
import io.anthills.classes.PheroCell;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ColonyClaimEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final Colony colony;
    private final PheroCell pheroCell;

    public ColonyClaimEvent(Colony colony, PheroCell pheroCell) {
        this.colony = colony;
        this.pheroCell = pheroCell;
    }

    public Colony getColony() {
        return colony;
    }

    public PheroCell getPheroCell() {
        return pheroCell;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
