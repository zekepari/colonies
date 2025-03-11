package io.anthills.managers;

import io.anthills.Plugin;
import io.anthills.classes.Ant;
import io.anthills.classes.Cell;
import io.anthills.classes.Colony;
import io.anthills.events.CellUpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PheromoneManager {
    private static final Map<Integer, BukkitTask> cellTasks = new HashMap<>();

    public static void processCell(Cell cell) {
        if (cellTasks.containsKey(cell.hashCode()))
            return;

        BukkitTask task = Bukkit.getScheduler().runTaskTimer(Plugin.getInstance(), () -> {
            List<Player> cellPlayers = CellTracker.getPlayersInCell(cell);

            if (cellPlayers.isEmpty()) {
                BukkitTask t = cellTasks.remove(cell.hashCode());
                if (t != null)
                    t.cancel();
                return;
            }

            Set<Colony> colonies = new HashSet<>();
            Colony capturingColony = null;
            for (Player player : cellPlayers) {
                Ant ant = GlobalCache.getAnt(player.getUniqueId());
                Colony antColony = ant.getColony();
                if (antColony == null)
                    continue;

                colonies.add(antColony);
                capturingColony = antColony;
                if (colonies.size() > 1)
                    break;
            }

            if (colonies.size() > 1)
                return;

            boolean friendlyCell = capturingColony != null && capturingColony.equals(cell.getColony());

            if (friendlyCell) {
                cell.updateScent(cellPlayers.size());
            } else {
                cell.updateScent(-cellPlayers.size());
                if (cell.getTier() == 0 && cell.getScent() == 0)
                    cell.setColony(capturingColony);
            }
            Bukkit.getPluginManager().callEvent(new CellUpdateEvent(cell));
        }, 20, 20);

        cellTasks.put(cell.hashCode(), task);
    }
}
