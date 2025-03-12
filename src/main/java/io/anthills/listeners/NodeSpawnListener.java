package io.anthills.listeners;

import io.anthills.classes.NodeOption;
import io.anthills.events.NodeBlockSpawnEvent;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.FireworkMeta;

public class NodeSpawnListener implements Listener {

    @EventHandler
    public void onNodeSpawn(NodeBlockSpawnEvent event) {
        NodeOption nodeOption = event.getNode().getNodeOption();
        Location location = event.getNode().getLocation();

        location.getWorld().playSound(location, Sound.BLOCK_ANVIL_USE, 1.0f, 1.0f);

        if (nodeOption.getWeight() < 0.1) {
            location.getWorld().playSound(location, Sound.ENTITY_WITHER_AMBIENT, 1.0f, 1.0f);
        }

        if (nodeOption.getWeight() < 0.2) {
            World world = location.getWorld();
            if (world == null)
                return;
            Firework firework = world.spawn(location.add(0, 1, 0), Firework.class);
            FireworkMeta meta = firework.getFireworkMeta();

            FireworkEffect effect = FireworkEffect.builder()
                    .with(Type.BALL)
                    .withColor(Color.RED)
                    .withFade(Color.BLUE)
                    .trail(true)
                    .flicker(true)
                    .build();
            meta.addEffect(effect);
            meta.setPower(1);
            firework.setFireworkMeta(meta);
        }
    }
}
