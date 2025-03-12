package io.anthills.managers.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.util.BlockVector;

import io.anthills.Plugin;
import io.anthills.classes.Ant;
import io.anthills.classes.CellPosition;
import io.anthills.classes.Colony;
import io.anthills.classes.Node;
import io.anthills.classes.Cell;

public class GlobalCache {
    private static final Map<UUID, Ant> ants = new HashMap<>();
    private static final Map<UUID, Colony> colonies = new HashMap<>();
    private static final Map<CellPosition, Cell> cells = new HashMap<>();
    private static final Map<BlockVector, Node> nodes = new HashMap<>();

    /* ========= ANT METHODS ========= */

    public static void registerAnt(Ant ant) {
        ants.put(ant.getPlayerId(), ant);
    }

    public static Ant getAnt(UUID playerId) {
        return ants.computeIfAbsent(playerId, key -> new Ant(playerId));
    }

    public static Map<UUID, Ant> getAnts() {
        return Collections.unmodifiableMap(ants);
    }

    public static void unregisterAnt(UUID playerId) {
        Ant ant = ants.get(playerId);
        if (ant != null) {
            try {
                Plugin.getPersistenceManager().deleteAnt(ant);
            } catch (Exception e) {
                Plugin.getInstance().getLogger().severe("Error deleting ant: " + e.getMessage());
            }
            ants.remove(playerId);
        }
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
        Colony colony = colonies.get(colonyId);
        if (colony != null) {
            try {
                Plugin.getPersistenceManager().deleteColony(colony);
            } catch (Exception e) {
                Plugin.getInstance().getLogger().severe("Error deleting colony: " + e.getMessage());
            }
            colonies.remove(colonyId);
        }
    }

    /* ========= CELL METHODS ========= */

    public static void registerCell(Cell cell) {
        cells.put(cell, cell);
    }

    public static Cell getCell(CellPosition cellPosition) {
        return cells.computeIfAbsent(cellPosition, key -> new Cell(cellPosition));
    }

    public static Map<CellPosition, Cell> getCells() {
        return Collections.unmodifiableMap(cells);
    }

    public static void unregisterCell(CellPosition cellPosition) {
        Cell cell = cells.get(cellPosition);
        if (cell != null) {
            try {
                Plugin.getPersistenceManager().deleteCell(cell);
            } catch (Exception e) {
                Plugin.getInstance().getLogger().severe("Error deleting cell: " + e.getMessage());
            }
            cells.remove(cellPosition);
        }
    }

    /* ========= NODE METHODS ========= */

    public static void registerNode(Node node) {
        nodes.put(node.getBlockVector(), node);
    }

    public static Node getNode(BlockVector blockVector) {
        return nodes.get(blockVector);
    }

    public static Node getNode(Location location) {
        BlockVector blockVector = new BlockVector(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        return nodes.get(blockVector);
    }

    public static Map<BlockVector, Node> getNodes() {
        return Collections.unmodifiableMap(nodes);
    }

    public static void unregisterNode(BlockVector blockVector) {
        Node node = nodes.get(blockVector);
        if (node != null) {
            try {
                Plugin.getPersistenceManager().deleteNode(node);
            } catch (Exception e) {
                Plugin.getInstance().getLogger().severe("Error deleting node: " + e.getMessage());
            }
            nodes.remove(blockVector);
        }
    }

    public static void unregisterNode(Location location) {
        BlockVector blockVector = new BlockVector(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        unregisterNode(blockVector);
    }
}
