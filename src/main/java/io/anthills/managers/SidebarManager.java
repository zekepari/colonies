package io.anthills.managers;

import io.anthills.classes.Cell;
import io.anthills.classes.PheroCell;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.Map;

public class SidebarManager {
    private static final Map<Player, SidebarManager> MANAGERS = new HashMap<>();

    public static SidebarManager getSidebarManager(Player player) {
        return MANAGERS.computeIfAbsent(player, p -> new SidebarManager());
    }

    private final Scoreboard scoreboard;
    private final Objective objective;
    private final Map<String, Integer> fieldLines;
    private final Map<Integer, String> displayedLines;

    public SidebarManager() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        scoreboard = manager.getNewScoreboard();
        objective = scoreboard.registerNewObjective("sidebar", Criteria.DUMMY,
                Component.text("[AntHills.io]", NamedTextColor.GREEN));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        fieldLines = new HashMap<>();
        displayedLines = new HashMap<>();
    }

    public void updateField(String field, Component value, int line) {
        if (fieldLines.containsKey(field)) {
            int oldLine = fieldLines.get(field);
            String oldDisplay = displayedLines.get(oldLine);
            scoreboard.resetScores(oldDisplay);
        }
        String plainTextValue = LegacyComponentSerializer.legacySection().serialize(value);
        Score score = objective.getScore(plainTextValue);
        score.setScore(line);
        fieldLines.put(field, line);
        displayedLines.put(line, plainTextValue);
    }

    public void clearFields() {
        for (String displayedLine : displayedLines.values()) {
            scoreboard.resetScores(displayedLine);
        }
        fieldLines.clear();
        displayedLines.clear();
    }

    /**
     * A convenience method that both updates the sidebar with the given cell data
     * and displays it to the player.
     *
     * @param player the player whose sidebar should be updated
     * @param cell   the cell to display information for
     */
    public void updateAndDisplay(Player player, Cell cell) {
        clearFields();
        int line = 15;
        Component cellCoordinates = Component.text("Cell: ["
                + cell.getCellX() + ", "
                + cell.getCellY() + ", "
                + cell.getCellZ() + "]", NamedTextColor.GOLD);
        updateField("cell", cellCoordinates, line--);

        PheroCell pheroCell = cell instanceof PheroCell
                ? (PheroCell) cell
                : GlobalCache.getPheroCell(cell);

        if (pheroCell != null && pheroCell.getColony() != null) {
            Component colonyInfo = Component.text("Colony: " + pheroCell.getColony().getName(), NamedTextColor.AQUA);
            updateField("colony", colonyInfo, line--);
            Component tierInfo = Component.text("Tier: " + pheroCell.getTier(), NamedTextColor.YELLOW);
            updateField("tier", tierInfo, line--);
        } else {
            Component unclaimed = Component.text("Unclaimed", NamedTextColor.RED);
            updateField("colony", unclaimed, line--);
            Component tierInfo = Component.text("Tier: 0", NamedTextColor.GRAY);
            updateField("tier", tierInfo, line--);
        }
        player.setScoreboard(scoreboard);
    }
}
