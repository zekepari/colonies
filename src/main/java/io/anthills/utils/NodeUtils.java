package io.anthills.utils;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;

import io.anthills.classes.NodeOption;
import io.anthills.config.NodeConfig;
import io.anthills.enums.NodeType;

public class NodeUtils {
    public static NodeOption getWeightedOption(NodeType nodeType) {
        NodeConfig.NodeData data = NodeConfig.getNodeData(nodeType);
        List<NodeOption> options = data.getBlockOptions();

        int onlinePlayers = Bukkit.getOnlinePlayers().size();
        int maxPlayers = 8;
        // double adjustmentFactor = 1.0;

        double rawExponent = 1 + ((maxPlayers - onlinePlayers) / maxPlayers); // * adjustmentFactor;
        double exponent = Math.max(rawExponent, 1.0);
        double totalWeight = 0;
        double[] adjustedWeights = new double[options.size()];

        for (int i = 0; i < options.size(); i++) {
            double baseWeight = options.get(i).getWeight();
            double effectiveWeight = Math.pow(baseWeight, exponent);
            adjustedWeights[i] = effectiveWeight;
            totalWeight += effectiveWeight;
        }

        double randomValue = new Random().nextDouble() * totalWeight;
        double cumulative = 0;
        for (int i = 0; i < options.size(); i++) {
            cumulative += adjustedWeights[i];
            if (randomValue <= cumulative) {
                return options.get(i);
            }
        }
        return options.get(0);
    }

    public static int getStartIndex(NodeOption nodeOption, NodeType nodeType) {
        List<NodeOption> nodeOptions = NodeConfig.getNodeData(nodeType).getBlockOptions();
        for (int i = 0; i < nodeOptions.size(); i++) {
            if (nodeOptions.get(i).equals(nodeOption)) {
                return i;
            }
        }
        return 0;
    }
}
