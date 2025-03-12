package io.anthills.classes;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.joml.Vector3f;

import de.oliver.fancyholograms.api.hologram.Hologram;
import de.oliver.fancyholograms.api.data.ItemHologramData;
import io.anthills.Plugin;
import io.anthills.enums.HologramAnimationType;

public class HologramAnimation {
    private final Hologram hologram;
    private final HologramAnimationType animationType;
    private final long period;
    private final float baseScale;
    private final double amplitude;

    private BukkitTask task;
    private boolean paused;
    private long startTime;

    public HologramAnimation(Hologram hologram, HologramAnimationType animationType, long period, float baseScale,
            double amplitude) {
        this.hologram = hologram;
        this.animationType = animationType;
        this.period = period;
        this.baseScale = baseScale;
        this.amplitude = amplitude;
        this.paused = false;
    }

    public void start() {
        if (task != null)
            return;

        startTime = System.currentTimeMillis();
        final double twoPi = 2 * Math.PI;

        task = Bukkit.getScheduler().runTaskTimerAsynchronously(Plugin.getInstance(), () -> {
            if (paused)
                return;
            long now = System.currentTimeMillis();
            double t = ((now - startTime) % period) / (double) period;
            float newScale = baseScale;
            if (animationType == HologramAnimationType.PULSE)
                newScale = baseScale + (float) (amplitude * Math.sin(twoPi * t));
            if (hologram.getData() instanceof ItemHologramData) {
                ItemHologramData data = (ItemHologramData) hologram.getData();
                data.setScale(new Vector3f(newScale, newScale, newScale));
                hologram.forceUpdate();
                hologram.refreshForViewers();
            }
        }, 0L, 1L);
    }

    public void pause() {
        this.paused = true;
    }

    public void resume() {
        this.paused = false;
    }

    public void delete() {
        if (task != null) {
            task.cancel();
            task = null;
        }
    }
}
