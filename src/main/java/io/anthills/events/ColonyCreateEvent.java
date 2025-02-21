package io.anthills.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import io.anthills.classes.Colony;

public class ColonyCreateEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final Colony colony;

    public ColonyCreateEvent(Colony colony) {
        this.colony = colony;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Colony getColony() {
        return colony;
    }
}
