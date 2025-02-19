package io.anthills.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import io.anthills.Plugin;
import io.anthills.classes.Cell;
import io.anthills.classes.PheroCell;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import org.bukkit.entity.Player;

public class CellListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getFrom() == null || event.getTo() == null)
            return;

        Cell fromCell = new Cell(event.getFrom());
        Cell toCell = new Cell(event.getTo());

        if (!fromCell.equals(toCell)) {
            Player player = event.getPlayer();

            PheroCell pheroCell = Plugin.pheroCells.stream()
                    .filter(cell -> cell.equals(toCell))
                    .findFirst()
                    .orElse(null);

            Component message;
            if (pheroCell != null && pheroCell.getColony() != null) {
                message = Component.text("You've entered a new cell: ")
                        .color(NamedTextColor.GOLD)
                        .append(Component.text(
                                "[" + toCell.getCellX() + ", " + toCell.getCellY() + ", " + toCell.getCellZ() + "] "))
                        .append(Component.text("owned by " + pheroCell.getColony().getName())
                                .color(NamedTextColor.AQUA));
            } else {
                message = Component.text("You've entered a new cell: ")
                        .color(NamedTextColor.GOLD)
                        .append(Component.text(
                                "[" + toCell.getCellX() + ", " + toCell.getCellY() + ", " + toCell.getCellZ() + "]"));
            }

            player.sendMessage(message);
        }
    }
}
