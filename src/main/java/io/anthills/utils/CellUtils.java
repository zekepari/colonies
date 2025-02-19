package io.anthills.utils;

import io.anthills.classes.Cell;
import io.anthills.classes.Colony;
import io.anthills.managers.GlobalCache;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class CellUtils {

    /**
     * Returns a list of all online players whose current location is within the
     * specified cell.
     *
     * @param cell the cell to check
     * @return a list of players in the cell
     */
    public static List<Player> getPlayersInCell(Cell cell) {
        return Bukkit.getOnlinePlayers().stream()
                .filter(player -> new Cell(player.getLocation()).equals(cell))
                .collect(Collectors.toList());
    }

    /**
     * Checks if there is any outsider in the given cell.
     * A player is considered an outsider if the player's ant (fetched from
     * GlobalCache)
     * has no colony or belongs to a different colony than the specified owner.
     *
     * @param cell  the cell to check
     * @param owner the colony that "owns" the cell
     * @return true if at least one player in the cell is not a member of the owner
     *         colony, false otherwise
     */
    public static boolean hasOutsiderInCell(Cell cell, Colony owner) {
        return getPlayersInCell(cell).stream()
                .map(player -> GlobalCache.getAnt(player.getUniqueId()))
                .anyMatch(ant -> ant.getColony() == null || !ant.getColony().equals(owner));
    }
}
