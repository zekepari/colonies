package io.anthills.classes;

import java.util.Objects;
import org.bukkit.Location;

public class CellPosition {
    private final int cellX;
    private final int cellY;
    private final int cellZ;
    private final boolean isSurface;

    public CellPosition(CellPosition cellPosition) {
        cellX = cellPosition.cellX;
        cellY = cellPosition.cellY;
        cellZ = cellPosition.cellZ;
        isSurface = cellPosition.isSurface;
    }

    public CellPosition(Location location) {
        cellX = location.getChunk().getX();
        cellY = Math.floorDiv(location.getBlockY() - location.getWorld().getMinHeight(), 16) + 1;
        cellZ = location.getChunk().getZ();
        isSurface = cellY > 18;
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

    public boolean isSurface() {
        return isSurface;
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
