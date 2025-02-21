package io.anthills.classes;

import org.bukkit.Location;

public class Cell extends CellPosition {
    private Colony colony;
    private int tier = 0;
    private int scent = 0;

    public Cell(CellPosition cellPosition) {
        super(cellPosition);
    }

    public Cell(Location location) {
        super(location);
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
        if (tier >= 0 && tier <= 3) {
            this.tier = tier;
        } else {
            throw new IllegalArgumentException("Invalid tier");
        }
    }

    public int getScent() {
        return scent;
    }

    public void updateScent(int amount) {
        int total = tier * 100 + scent + amount;

        total = Math.max(0, Math.min(total, 400));

        if (total == 400) {
            tier = 3;
            scent = 100;
        } else {
            tier = total / 100;
            scent = total % 100;
        }
    }

    public boolean isMaxed() {
        return tier == 3 && scent == 100;
    }
}
