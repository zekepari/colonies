package io.anthills.classes;

import org.bukkit.Material;

public class NodeOption {
    private final Material material;
    private final double weight;

    public NodeOption(Material material, double weight) {
        this.material = material;
        this.weight = weight;
    }

    public Material getMaterial() {
        return material;
    }

    public double getWeight() {
        return weight;
    }
}
