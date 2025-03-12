package io.anthills.classes;

import java.util.Objects;
import org.bukkit.Location;
import org.bukkit.World;

public class CellPosition {
    private final int cellX;
    private final int cellY;
    private final int cellZ;
    private final World world;
    private final boolean isSurface;

    public CellPosition(CellPosition cellPosition) {
        cellX = cellPosition.cellX;
        cellY = cellPosition.cellY;
        cellZ = cellPosition.cellZ;
        world = cellPosition.world;
        isSurface = cellPosition.isSurface;
    }

    public CellPosition(Location location) {
        cellX = location.getChunk().getX();
        cellY = Math.floorDiv(location.getBlockY() - location.getWorld().getMinHeight(), 16) + 1;
        cellZ = location.getChunk().getZ();
        world = location.getWorld();
        isSurface = cellY > 18;
    }

    public CellPosition(int cellX, int cellY, int cellZ, World world) {
        this.cellX = cellX;
        this.cellY = cellY;
        this.cellZ = cellZ;
        this.world = world;
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

    public World getWorld() {
        return world;
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

    @Override
    public String toString() {
        return world.getName() + ":" + cellX + "_" + cellY + "_" + cellZ;
    }
}
