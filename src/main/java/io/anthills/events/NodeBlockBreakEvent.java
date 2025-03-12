package io.anthills.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import io.anthills.classes.Node;

public class NodeBlockBreakEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final Node node;
    private final Player breaker;
    private final Block block;

    public NodeBlockBreakEvent(Node node, Player breaker, Block block) {
        this.node = node;
        this.breaker = breaker;
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

    public Player getBreaker() {
        return breaker;
    }

    public Block getBlock() {
        return block;
    }
}
