package io.anthills.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import io.anthills.classes.Node;
import io.anthills.events.NodeBreakEvent;

public class NodeBreakListener implements Listener {

    @EventHandler
    public void onNodeBreak(NodeBreakEvent event) {
        Node node = event.getNode();

        node.startCooldown();
    }
}
