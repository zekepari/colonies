package io.anthills.classes;

import java.util.Objects;
import org.bukkit.Location;

public class Cell {
    private final int cellX;
    private final int cellY;
    private final int cellZ;

    public Cell(Location location) {
        this.cellX = location.getChunk().getX();
        this.cellY = Math.floorDiv(location.getBlockY() - location.getWorld().getMinHeight(), 16) + 1;
        this.cellZ = location.getChunk().getZ();
    }

    public int getCellX() {
        return cellX;
    }

    public int getCellY() {
        return cellY;
    }

    public int getCellZ() {
        return cellZ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Cell))
            return false;
        Cell that = (Cell) o;
        return cellX == that.cellX && cellY == that.cellY && cellZ == that.cellZ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cellX, cellY, cellZ);
    }
}
