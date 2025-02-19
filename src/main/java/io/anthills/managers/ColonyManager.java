package io.anthills.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.anthills.classes.Colony;
import org.bukkit.entity.Player;

public class ColonyManager {
    private static final Map<UUID, Colony> colonies = new HashMap<>();

    public static Colony registerColony(String name, UUID queen) {
        Colony colony = new Colony(UUID.randomUUID(), name, queen);
        colonies.put(colony.getUniqueId(), colony);
        return colony;
    }

    public static Colony getColony(UUID colonyId) {
        return colonies.get(colonyId);
    }

    public static Colony getColonyByPlayer(Player player) {
        for (Colony colony : colonies.values()) {
            if (colony.isMember(player)) {
                return colony;
            }
        }
        return null;
    }

    public static void unregisterColony(UUID colonyId) {
        colonies.remove(colonyId);
    }

    public static void saveColonies() {
        // TODO: Implement saving logic (e.g., to a file or database)
    }

    public static void loadColonies() {
        // TODO: Implement loading logic
    }
}