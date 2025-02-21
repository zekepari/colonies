package io.anthills.managers;

import io.anthills.classes.Cell;
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

    private final Scoreboard scoreboard;
    private final Objective objective;
    private final Map<String, Integer> fieldLines;
    private final Map<Integer, String> displayedLines;

    private SidebarManager(Player player) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        scoreboard = manager.getNewScoreboard();
        objective = scoreboard.registerNewObjective("sidebar", Criteria.DUMMY,
                Component.text("[AntHills.io]", NamedTextColor.GREEN));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        fieldLines = new HashMap<>();
        displayedLines = new HashMap<>();
        player.setScoreboard(scoreboard);
    }

    public static void updateAndDisplay(Player player, Cell cell) {
        SidebarManager sidebar = MANAGERS.computeIfAbsent(player, p -> new SidebarManager(player));
        sidebar.updateDisplay(cell);
    }

    private void updateDisplay(Cell cell) {
        clearFields();
        int line = 15;
        Component cellCoordinates = Component.text("Cell: ["
                + cell.getCellX() + ", "
                + cell.getCellY() + ", "
                + cell.getCellZ() + "]", NamedTextColor.GOLD);
        updateField("cell", cellCoordinates, line--);

        if (cell.getColony() != null) {
            Component colonyInfo = Component.text("Colony: " + cell.getColony().getName(), NamedTextColor.AQUA);
            updateField("colony", colonyInfo, line--);
            Component tierInfo = Component.text("Tier: " + cell.getTier(), NamedTextColor.YELLOW);
            updateField("tier", tierInfo, line--);
            Component progressInfo = Component.text("Scent: " + cell.getScent() + "%", NamedTextColor.LIGHT_PURPLE);
            updateField("progress", progressInfo, line--);
        } else {
            Component unclaimed = Component.text("Unclaimed", NamedTextColor.RED);
            updateField("colony", unclaimed, line--);
            Component tierInfo = Component.text("Tier: 0", NamedTextColor.GRAY);
            updateField("tier", tierInfo, line--);
            Component progressInfo = Component.text("Progress: 0%", NamedTextColor.GRAY);
            updateField("progress", progressInfo, line--);
        }
    }

    private void updateField(String field, Component value, int line) {
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

    private void clearFields() {
        for (String displayedLine : displayedLines.values()) {
            scoreboard.resetScores(displayedLine);
        }
        fieldLines.clear();
        displayedLines.clear();
    }
}
