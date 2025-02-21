package io.anthills.managers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.anthills.classes.Ant;
import io.anthills.classes.CellPosition;
import io.anthills.classes.Colony;
import io.anthills.classes.Cell;

public class GlobalCache {
    private static final Map<UUID, Ant> ants = new HashMap<>();
    private static final Map<UUID, Colony> colonies = new HashMap<>();
    private static final Map<Integer, Cell> cells = new HashMap<>();

    /* ========= ANT METHODS ========= */

    public static Ant getAnt(UUID playerId) {
        return ants.computeIfAbsent(playerId, key -> new Ant(playerId));
    }

    public static Map<UUID, Ant> getAnts() {
        return Collections.unmodifiableMap(ants);
    }

    public static void unregisterAnt(UUID playerId) {
        ants.remove(playerId);
    }

    /* ========= COLONY METHODS ========= */

    public static void registerColony(Colony colony) {
        colonies.put(colony.getColonyId(), colony);
    }

    public static Colony getColony(UUID colonyId) {
        return colonies.get(colonyId);
    }

    public static Map<UUID, Colony> getColonies() {
        return Collections.unmodifiableMap(colonies);
    }

    public static void unregisterColony(UUID colonyId) {
        colonies.remove(colonyId);
    }

    /* ========= CELL METHODS ========= */

    public static Cell getCell(CellPosition cellPosition) {
        return cells.computeIfAbsent(cellPosition.hashCode(), key -> new Cell(cellPosition));
    }

    public static Map<Integer, Cell> getCells() {
        return Collections.unmodifiableMap(cells);
    }

    public static void unregisterCell(CellPosition cell) {
        cells.remove(cell.hashCode());
    }
}
