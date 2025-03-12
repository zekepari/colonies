package io.anthills.persistence;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import io.anthills.classes.Cell;
import io.anthills.classes.Colony;

import org.bukkit.Bukkit;
import org.bukkit.World;

@DatabaseTable(tableName = "cells")
public class CellRecord {
    @DatabaseField(id = true)
    private String id;

    @DatabaseField
    private int cellX;

    @DatabaseField
    private int cellY;

    @DatabaseField
    private int cellZ;

    @DatabaseField
    private String worldName;

    @DatabaseField
    private int scent;

    @DatabaseField(canBeNull = true)
    private String colonyId;

    public CellRecord() {
    }

    public CellRecord(Cell cell) {
        this.cellX = cell.getCellX();
        this.cellY = cell.getCellY();
        this.cellZ = cell.getCellZ();
        this.worldName = cell.getWorld().getName();
        this.scent = cell.getScent();
        this.id = cell.toString();

        Colony colony = cell.getColony();
        if (colony != null) {
            this.colonyId = colony.getColonyId().toString();
        }
    }

    public Cell toCell() {
        World world = Bukkit.getWorld(worldName);
        Cell cell = new Cell(cellX, cellY, cellZ, world);
        cell.updateScent(scent);
        return cell;
    }

    public String getId() {
        return id;
    }

    public String getColonyId() {
        return colonyId;
    }
}
