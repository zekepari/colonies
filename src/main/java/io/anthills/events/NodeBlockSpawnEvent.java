package io.anthills.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import io.anthills.classes.Node;

public class NodeBlockSpawnEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final Node node;

    public NodeBlockSpawnEvent(Node node) {
        this.node = node;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Node getNode() {
        return node;
    }
}