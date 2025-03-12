package io.anthills.classes;

import org.bukkit.Location;
import org.bukkit.World;

public class Cell extends CellPosition {
    private Colony colony;
    private int scent = 0;

    public Cell(CellPosition cellPosition) {
        super(cellPosition);
    }

    public Cell(Location location) {
        super(location);
    }

    public Cell(int cellX, int cellY, int cellZ, World world) {
        super(cellX, cellY, cellZ, world);
    }

    public Colony getColony() {
        return colony;
    }

    public void setColony(Colony colony) {
        this.colony = colony;
    }

    public int getTier() {
        return scent / 100;
    }

    public int getScent() {
        return scent % 100;
    }

    public void updateScent(int amount) {
        scent = Math.max(0, Math.min(scent + amount, 400));
    }
}