package io.anthills.utils;

import org.bukkit.Location;
import org.bukkit.util.BlockVector;

public class LocationUtils {
    public static BlockVector toBlockVector(Location location) {
        return new BlockVector(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }
}
