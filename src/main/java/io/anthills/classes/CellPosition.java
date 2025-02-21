package io.anthills.classes;

import java.util.Objects;
import org.bukkit.Location;

public class CellPosition {
    private final int cellX;
    private final int cellY;
    private final int cellZ;

    public CellPosition(CellPosition cellPosition) {
        this.cellX = cellPosition.cellX;
        this.cellY = cellPosition.cellY;
        this.cellZ = cellPosition.cellZ;
    }

    public CellPosition(Location location) {
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
        if (!(o instanceof CellPosition))
            return false;
        CellPosition that = (CellPosition) o;
        return cellX == that.cellX && cellY == that.cellY && cellZ == that.cellZ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cellX, cellY, cellZ);
    }
}
