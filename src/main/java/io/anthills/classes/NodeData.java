package io.anthills.classes;

import java.util.List;

import net.kyori.adventure.text.Component;

public class NodeData {
    private final NodeType type;
    private final int regenTimeSeconds;
    private final List<NodeBlockOption> blockOptions;
    private final Component nodeName;

    public NodeData(NodeType type, int regenTimeSeconds, List<NodeBlockOption> blockOptions, Component nodeName) {
        this.type = type;
        this.regenTimeSeconds = regenTimeSeconds;
        this.blockOptions = blockOptions;
        this.nodeName = nodeName;
    }

    public NodeType getType() {
        return type;
    }

    public int getRegenTimeSeconds() {
        return regenTimeSeconds;
    }

    public List<NodeBlockOption> getBlockOptions() {
        return blockOptions;
    }

    public Component getNodeName() {
        return nodeName;
    }
}
