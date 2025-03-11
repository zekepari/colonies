package io.anthills.managers;

import io.anthills.classes.Cell;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;

import fr.mrmicky.fastboard.FastBoard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SidebarManager {
    private static final Map<UUID, FastBoard> playerBoards = new HashMap<>();

    public static void updatePlayerBoard(Player player, Cell cell) {
        FastBoard board = playerBoards.computeIfAbsent(player.getUniqueId(), uuid -> {
            FastBoard newBoard = new FastBoard(player);
            newBoard.updateTitle(LegacyComponentSerializer.legacySection().serialize(
                    Component.text("[AntHills.io]", NamedTextColor.GREEN)));
            return newBoard;
        });

        ArrayList<String> lines = new ArrayList<>();

        String cellCoordinates = LegacyComponentSerializer.legacySection().serialize(
                Component.text("Cell: [" + cell.getCellX() + ", " + cell.getCellY() + ", " + cell.getCellZ() + "]",
                        NamedTextColor.GOLD));
        lines.add(cellCoordinates);

        if (cell.getCellY() > 18) {
            String surface = LegacyComponentSerializer.legacySection().serialize(
                    Component.text("Surface", NamedTextColor.DARK_GREEN));
            lines.add(surface);
        } else {
            if (cell.getColony() != null) {
                String colonyInfo = LegacyComponentSerializer.legacySection().serialize(
                        Component.text("Colony: " + cell.getColony().getName(), NamedTextColor.AQUA));
                lines.add(colonyInfo);
                String tierInfo = LegacyComponentSerializer.legacySection().serialize(
                        Component.text("Tier: " + cell.getTier(), NamedTextColor.YELLOW));
                lines.add(tierInfo);
                String progressInfo = LegacyComponentSerializer.legacySection().serialize(
                        Component.text("Scent: " + cell.getScent() + "%", NamedTextColor.LIGHT_PURPLE));
                lines.add(progressInfo);
            } else {
                String unclaimed = LegacyComponentSerializer.legacySection().serialize(
                        Component.text("Unclaimed", NamedTextColor.RED));
                lines.add(unclaimed);
                String tierInfo = LegacyComponentSerializer.legacySection().serialize(
                        Component.text("Tier: 0", NamedTextColor.GRAY));
                lines.add(tierInfo);
                String progressInfo = LegacyComponentSerializer.legacySection().serialize(
                        Component.text("Progress: 0%", NamedTextColor.GRAY));
                lines.add(progressInfo);
            }
        }

        board.updateLines(lines);
    }

    public static void remove(Player player) {
        FastBoard board = playerBoards.remove(player.getUniqueId());
        if (board != null)
            board.delete();
    }
}
