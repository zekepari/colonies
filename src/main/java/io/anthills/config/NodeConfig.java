package io.anthills.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;

import io.anthills.classes.NodeBlockOption;
import io.anthills.classes.NodeData;
import io.anthills.classes.NodeType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class NodeConfig {
    private static final Map<NodeType, NodeData> nodeConfigs = new HashMap<>();

    static {
        nodeConfigs.put(NodeType.TIMBER, new NodeData(
                NodeType.TIMBER,
                10,
                List.of(
                        new NodeBlockOption(Material.OAK_LOG, 1.0),
                        new NodeBlockOption(Material.SPRUCE_LOG, 0.8),
                        new NodeBlockOption(Material.BIRCH_LOG, 0.8),
                        new NodeBlockOption(Material.JUNGLE_LOG, 0.6),
                        new NodeBlockOption(Material.ACACIA_LOG, 0.6),
                        new NodeBlockOption(Material.DARK_OAK_LOG, 0.5),
                        new NodeBlockOption(Material.MANGROVE_LOG, 0.5),
                        new NodeBlockOption(Material.CRIMSON_STEM, 0.2),
                        new NodeBlockOption(Material.WARPED_STEM, 0.2),
                        new NodeBlockOption(Material.CHERRY_LOG, 0.1)),
                Component.text("[Timber Node]", NamedTextColor.GOLD)));

        nodeConfigs.put(NodeType.ORE, new NodeData(
                NodeType.ORE,
                15,
                List.of(
                        new NodeBlockOption(Material.IRON_ORE, 1.0),
                        new NodeBlockOption(Material.COAL_ORE, 0.8),
                        new NodeBlockOption(Material.GOLD_ORE, 0.3),
                        new NodeBlockOption(Material.LAPIS_ORE, 0.2),
                        new NodeBlockOption(Material.REDSTONE_ORE, 0.2),
                        new NodeBlockOption(Material.DIAMOND_ORE, 0.05),
                        new NodeBlockOption(Material.EMERALD_ORE, 0.05)),
                Component.text("[Ore Node]", NamedTextColor.GRAY)));

        nodeConfigs.put(NodeType.FLOWER, new NodeData(
                NodeType.FLOWER,
                12,
                List.of(
                        new NodeBlockOption(Material.DANDELION, 1.0),
                        new NodeBlockOption(Material.POPPY, 0.9),
                        new NodeBlockOption(Material.BLUE_ORCHID, 0.8),
                        new NodeBlockOption(Material.ALLIUM, 0.7),
                        new NodeBlockOption(Material.AZURE_BLUET, 0.6),
                        new NodeBlockOption(Material.RED_TULIP, 0.5),
                        new NodeBlockOption(Material.ORANGE_TULIP, 0.5),
                        new NodeBlockOption(Material.WHITE_TULIP, 0.5),
                        new NodeBlockOption(Material.PINK_TULIP, 0.4),
                        new NodeBlockOption(Material.OXEYE_DAISY, 0.3)),
                Component.text("[Flower Node]", NamedTextColor.LIGHT_PURPLE)));

        nodeConfigs.put(NodeType.SAND, new NodeData(
                NodeType.SAND,
                8,
                List.of(
                        new NodeBlockOption(Material.SAND, 1.0),
                        new NodeBlockOption(Material.RED_SAND, 0.8),
                        new NodeBlockOption(Material.CLAY, 0.5),
                        new NodeBlockOption(Material.SANDSTONE, 0.4),
                        new NodeBlockOption(Material.CUT_SANDSTONE, 0.3)),
                Component.text("[Sand Node]", NamedTextColor.YELLOW)));
    }

    public static NodeData getNodeData(NodeType type) {
        return nodeConfigs.get(type);
    }
}
