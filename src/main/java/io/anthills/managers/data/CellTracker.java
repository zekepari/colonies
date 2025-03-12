package io.anthills.managers.data;

import io.anthills.classes.CellPosition;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CellTracker {
    private static final Map<CellPosition, List<Player>> cellMap = new HashMap<>();
    private static final Map<Player, CellPosition> playerMap = new HashMap<>();

    public static synchronized void updatePlayerCell(Player player, CellPosition cellPosition) {
        CellPosition oldCell = playerMap.get(player);
        if (oldCell != null) {
            if (oldCell.equals(cellPosition))
                return;
            List<Player> oldPlayers = cellMap.get(oldCell);
            if (oldPlayers != null) {
                oldPlayers.remove(player);
                if (oldPlayers.isEmpty())
                    cellMap.remove(oldCell);
            }
        }

        playerMap.put(player, cellPosition);
        cellMap.computeIfAbsent(cellPosition, k -> new ArrayList<>()).add(player);
    }

    public static synchronized CellPosition getCellOfPlayer(Player player) {
        return playerMap.get(player);
    }

    public static synchronized List<Player> getPlayersInCell(CellPosition cellPosition) {
        List<Player> players = cellMap.get(cellPosition);
        return players == null ? Collections.emptyList() : new ArrayList<>(players);
    }
}
