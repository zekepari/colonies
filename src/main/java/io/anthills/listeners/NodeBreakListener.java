package io.anthills.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import io.anthills.classes.Node;
import io.anthills.events.NodeBlockBreakEvent;

public class NodeBreakListener implements Listener {

    @EventHandler
    public void onNodeBreak(NodeBlockBreakEvent event) {
        Node node = event.getNode();
        node.startCooldown();
    }
}
