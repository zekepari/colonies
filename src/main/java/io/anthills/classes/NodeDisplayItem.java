package io.anthills.classes;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.joml.Matrix4f;

import io.anthills.Plugin;

import org.bukkit.entity.ItemDisplay;
import org.bukkit.World;

public class NodeDisplayItem {
    private final World world;
    private ItemDisplay itemDisplay;

    private final int interpolationDuration = 20;
    private final int interpolationDelay = 0;

    private final float baseScale = 0.4f;
    private final float amplitude = 0.1f;
    private final float rotation = (float) Math.toRadians(90);
    private boolean reverse = false;

    public NodeDisplayItem(Location location, Material material) {
        this.world = location.getWorld();
        this.itemDisplay = world.spawn(location, ItemDisplay.class, entity -> {
            entity.setItemStack(new ItemStack(material));
            entity.setPersistent(false);
        });

        Bukkit.getScheduler().runTaskTimerAsynchronously(Plugin.getInstance(), task -> {
            if (!itemDisplay.isValid()) {
                task.cancel();
                return;
            }

            reverse = !reverse;
            float scaleFactor = reverse ? baseScale : (baseScale + amplitude);
            Matrix4f matrix = new Matrix4f().scale(scaleFactor).rotateY(rotation + 0.1F);

            itemDisplay.setTransformationMatrix(matrix);
            itemDisplay.setInterpolationDelay(interpolationDelay);
            itemDisplay.setInterpolationDuration(interpolationDuration);

        }, 1, interpolationDuration);
    }

    public void update(Material material) {
        if (itemDisplay.isValid()) {
            itemDisplay.setItemStack(new ItemStack(material));
        }
    }

    public void delete() {
        if (itemDisplay.isValid()) {
            itemDisplay.remove();
        }
    }
}
