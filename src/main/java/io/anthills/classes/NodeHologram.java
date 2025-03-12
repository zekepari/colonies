package io.anthills.classes;

import org.bukkit.Location;
import org.bukkit.Material;

import io.anthills.config.NodeConfig;
import net.kyori.adventure.text.Component;

public class NodeHologram {
    private final Node node;
    private NodeDisplayItem displayItem;
    private NodeDisplayText displayText;

    public NodeHologram(Node node) {
        this.node = node;

        initializeDisplays();
    }

    private void initializeDisplays() {
        NodeConfig.NodeData data = NodeConfig.getNodeData(node.getNodeType());
        Component nodeName = data.getNodeName();
        Location textLocation = node.getLocation().clone().add(0.5, 1.8, 0.5);
        displayText = new NodeDisplayText(textLocation, nodeName);

        Material material = (node.getNodeOption() != null) ? node.getNodeOption().getMaterial() : Material.AIR;
        Location itemLocation = node.getLocation().clone().add(0.5, 1.4, 0.5);
        displayItem = new NodeDisplayItem(itemLocation, material);
    }

    public void updateTextHologram(int cooldown, int totalCooldown) {
        displayText.update(cooldown, totalCooldown);
    }

    public void updateItemDisplay(NodeOption nodeOption) {
        if (node.getNodeOption() != null) {
            displayItem.update(nodeOption.getMaterial());
        }
    }

    public void delete() {
        if (displayText != null) {
            displayText.delete();
        }
        if (displayItem != null) {
            displayItem.delete();
        }
    }
}
