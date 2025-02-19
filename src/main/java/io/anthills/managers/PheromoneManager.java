package io.anthills.managers;

import io.anthills.Plugin;
import io.anthills.classes.Ant;
import io.anthills.classes.Cell;
import io.anthills.classes.PheroCell;
import io.anthills.events.ColonyClaimEvent;
import io.anthills.events.PheroCellTierChangeEvent;
import io.anthills.utils.CellUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class PheromoneManager {
    private static final Map<Player, BukkitTask> tasks = new HashMap<>();

    public static void processCell(Player player, Cell cell) {
        BukkitTask existingTask = tasks.remove(player);
        if (existingTask != null) {
            existingTask.cancel();
        }

        BukkitTask task = Bukkit.getScheduler().runTaskLater(Plugin.getInstance(), () -> {
            if (!player.isOnline())
                return;
            Cell currentCell = new Cell(player.getLocation());
            if (currentCell.equals(cell)) {
                PheroCell pheroCell = GlobalCache.getPheroCell(cell);
                Ant ant = GlobalCache.getAnt(player.getUniqueId());

                if (pheroCell == null) {
                    pheroCell = new PheroCell(player.getLocation(), ant.getColony());
                    GlobalCache.registerPheroCell(pheroCell);
                    Bukkit.getPluginManager().callEvent(new ColonyClaimEvent(pheroCell.getColony(), pheroCell));
                } else {
                    if (CellUtils.hasOutsiderInCell(cell, ant.getColony())) {
                        return;
                    }
                    int oldTier = pheroCell.getTier();
                    if (pheroCell.promoteTier()) {
                        Bukkit.getPluginManager()
                                .callEvent(new PheroCellTierChangeEvent(pheroCell, oldTier, pheroCell.getTier()));
                    }
                }
                if (GlobalCache.getPheroCell(cell).getTier() < 3) {
                    processCell(player, cell);
                }
            }
        }, 20 * 20);
        tasks.put(player, task);
    }
}
