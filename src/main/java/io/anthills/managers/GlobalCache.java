package io.anthills.managers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.anthills.classes.Ant;
import io.anthills.classes.Cell;
import io.anthills.classes.Colony;
import io.anthills.classes.PheroCell;

public class GlobalCache {
    private static final Map<UUID, Ant> ants = new HashMap<>();
    private static final Map<UUID, Colony> colonies = new HashMap<>();
    private static final Map<Integer, PheroCell> pheroCells = new HashMap<>();

    /* ========= ANT METHODS ========= */

    public static void registerAnt(Ant ant) {
        ants.put(ant.getPlayerId(), ant);
    }

    public static Ant getAnt(UUID playerId) {
        return ants.computeIfAbsent(playerId, id -> new Ant(id));
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

    /* ========= PHEROCELL METHODS ========= */

    public static void registerPheroCell(PheroCell pheroCell) {
        pheroCells.put(pheroCell.hashCode(), pheroCell);
    }

    public static PheroCell getPheroCell(Cell cell) {
        return pheroCells.get(cell.hashCode());
    }

    public static Map<Integer, PheroCell> getPheroCells() {
        return Collections.unmodifiableMap(pheroCells);
    }

    public static void unregisterPheroCell(Cell cell) {
        pheroCells.remove(cell.hashCode());
    }
}
