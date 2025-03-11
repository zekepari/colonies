package io.anthills.events;

import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import io.anthills.classes.Node;

public class NodeRegenerateEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final Node node;
    private final Block block;

    public NodeRegenerateEvent(Node node, Block block) {
        this.node = node;
        this.block = block;
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

    public Block getBlock() {
        return block;
    }
}