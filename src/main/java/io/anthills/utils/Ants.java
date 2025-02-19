package io.anthills.utils;

import java.util.UUID;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import io.anthills.Plugin;

public class Ants {
    private static final UUIDDataType UUID_DATA_TYPE = new UUIDDataType();
    private static NamespacedKey colonyIDKey = new NamespacedKey(Plugin.getInstance(), "colonyID");

    public static void setColonyID(Player player, UUID colonyID) {
        player.getPersistentDataContainer().set(colonyIDKey, UUID_DATA_TYPE, colonyID);
    }

    public static UUID getColonyID(Player player) {
        UUID colonyID = player.getPersistentDataContainer().get(colonyIDKey, UUID_DATA_TYPE);
        return colonyID;
    }
}
