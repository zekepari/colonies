package io.anthills.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;

import io.anthills.classes.NodeOption;
import io.anthills.enums.NodeType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class NodeConfig {
        private static final Map<NodeType, NodeData> nodeConfigs = new HashMap<>();

        static {
                nodeConfigs.put(NodeType.TIMBER, new NodeData(
                                10,
                                List.of(
                                                new NodeOption(Material.OAK_LOG, 1.0),
                                                new NodeOption(Material.SPRUCE_LOG, 0.8),
                                                new NodeOption(Material.BIRCH_LOG, 0.8),
                                                new NodeOption(Material.JUNGLE_LOG, 0.6),
                                                new NodeOption(Material.ACACIA_LOG, 0.6),
                                                new NodeOption(Material.DARK_OAK_LOG, 0.5),
                                                new NodeOption(Material.MANGROVE_LOG, 0.5),
                                                new NodeOption(Material.CRIMSON_STEM, 0.2),
                                                new NodeOption(Material.WARPED_STEM, 0.2),
                                                new NodeOption(Material.CHERRY_LOG, 0.1)),
                                Component.text("[Timber Node]", NamedTextColor.GOLD)));

                nodeConfigs.put(NodeType.ORE, new NodeData(
                                15,
                                List.of(
                                                new NodeOption(Material.IRON_ORE, 1.0),
                                                new NodeOption(Material.COAL_ORE, 0.8),
                                                new NodeOption(Material.GOLD_ORE, 0.3),
                                                new NodeOption(Material.LAPIS_ORE, 0.2),
                                                new NodeOption(Material.REDSTONE_ORE, 0.2),
                                                new NodeOption(Material.DIAMOND_ORE, 0.05),
                                                new NodeOption(Material.EMERALD_ORE, 0.05)),
                                Component.text("[Ore Node]", NamedTextColor.GRAY)));

                nodeConfigs.put(NodeType.FLOWER, new NodeData(
                                12,
                                List.of(
                                                new NodeOption(Material.DANDELION, 1.0),
                                                new NodeOption(Material.POPPY, 0.9),
                                                new NodeOption(Material.BLUE_ORCHID, 0.8),
                                                new NodeOption(Material.ALLIUM, 0.7),
                                                new NodeOption(Material.AZURE_BLUET, 0.6),
                                                new NodeOption(Material.RED_TULIP, 0.5),
                                                new NodeOption(Material.ORANGE_TULIP, 0.5),
                                                new NodeOption(Material.WHITE_TULIP, 0.5),
                                                new NodeOption(Material.PINK_TULIP, 0.4),
                                                new NodeOption(Material.OXEYE_DAISY, 0.3)),
                                Component.text("[Flower Node]", NamedTextColor.LIGHT_PURPLE)));

                nodeConfigs.put(NodeType.SAND, new NodeData(
                                8,
                                List.of(
                                                new NodeOption(Material.SAND, 1.0),
                                                new NodeOption(Material.RED_SAND, 0.8),
                                                new NodeOption(Material.CLAY, 0.5),
                                                new NodeOption(Material.SANDSTONE, 0.4),
                                                new NodeOption(Material.CUT_SANDSTONE, 0.3)),
                                Component.text("[Sand Node]", NamedTextColor.YELLOW)));
        }

        public static NodeData getNodeData(NodeType type) {
                return nodeConfigs.get(type);
        }

        public static class NodeData {
                private final int regenTimeSeconds;
                private final List<NodeOption> blockOptions;
                private final Component nodeName;

                public NodeData(int regenTimeSeconds, List<NodeOption> blockOptions,
                                Component nodeName) {
                        this.regenTimeSeconds = regenTimeSeconds;
                        this.blockOptions = blockOptions;
                        this.nodeName = nodeName;
                }

                public int getRegenTimeSeconds() {
                        return regenTimeSeconds;
                }

                public List<NodeOption> getBlockOptions() {
                        return blockOptions;
                }

                public Component getNodeName() {
                        return nodeName;
                }
        }
}
