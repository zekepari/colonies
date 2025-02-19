package io.anthills.classes;

import org.bukkit.Location;

public class PheroCell extends Cell {
    private Colony colony;
    private int tier = 1;

    public PheroCell(Location location, Colony colony) {
        super(location);
        this.colony = colony;
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

    public boolean promoteTier() {
        if (tier < 3) {
            tier++;
            return true;
        }
        return false;
    }

    public boolean demoteTier() {
        if (tier > 1) {
            tier--;
            return true;
        }
        return false;
    }
}
