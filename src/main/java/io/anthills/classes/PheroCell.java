package io.anthills.classes;

import org.bukkit.Location;

public class PheroCell extends Cell {
    private Colony colony;
    private int tier;

    public PheroCell(Location location, Colony colony, int tier) {
        super(location);
        this.colony = colony;
        setTier(tier);
    }

    public Colony getColony() {
        return colony;
    }

    public void setColony(Colony colony) {
        this.colony = colony;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        if (tier < 1 || tier > 3) {
            throw new IllegalArgumentException("Tier must be between 1 and 3 inclusive.");
        }
        this.tier = tier;
    }
}
