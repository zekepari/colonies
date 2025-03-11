package io.anthills.classes;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Display.Billboard;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.joml.Vector3f;

import de.oliver.fancyholograms.api.FancyHologramsPlugin;
import de.oliver.fancyholograms.api.HologramManager;
import de.oliver.fancyholograms.api.data.ItemHologramData;
import de.oliver.fancyholograms.api.data.TextHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import io.anthills.Plugin;
import io.anthills.config.NodeConfig;
import io.anthills.events.NodeRegenerateEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class Node {
    private final Location location;
    private final NodeType type;
    private Block nodeBlock;
    private Material currentMaterial;

    public Node(Location location, NodeType type) {
        this.location = location.getBlock().getLocation();
        this.type = type;
    }

    public NodeType getType() {
        return type;
    }

    public Location getLocation() {
        return location;
    }

    public NodeData getNodeData() {
        return NodeConfig.getNodeData(type);
    }

    public void spawnBlock() {
        currentMaterial = getWeightedMaterial();

        updateItemHologram(currentMaterial);
        updateTextHologram(getNodeData().getNodeName());

        nodeBlock = location.getBlock();
        nodeBlock.setType(currentMaterial);
    }

    private Material getWeightedMaterial() {
        double totalWeight = getNodeData().getBlockOptions().stream().mapToDouble(NodeBlockOption::getWeight).sum();
        double randomValue = new Random().nextDouble() * totalWeight;
        double cumulative = 0;
        for (NodeBlockOption option : getNodeData().getBlockOptions()) {
            cumulative += option.getWeight();
            if (randomValue <= cumulative)
                return option.getMaterial();
        }
        return Material.STONE;
    }

    public void remove() {
        if (nodeBlock != null)
            nodeBlock.setType(Material.AIR);
    }

    public void startCooldown() {
        int totalCooldown = getNodeData().getRegenTimeSeconds();
        Component nodeName = getNodeData().getNodeName();

        new BukkitRunnable() {
            int remaining = totalCooldown;

            @Override
            public void run() {
                if (remaining < 1) {
                    spawnBlock();
                    Bukkit.getPluginManager().callEvent(new NodeRegenerateEvent(Node.this, nodeBlock));
                    cancel();
                    return;
                }

                double ratio = (double) remaining / totalCooldown;
                int gb = (int) (255 * ratio);
                TextColor dynamicColor = TextColor.fromHexString(String.format("#%02X%02X%02X", 255, gb, gb));

                Component text = nodeName
                        .appendNewline()
                        .append(Component.text(remaining + "s Remaining", dynamicColor));

                updateTextHologram(text);
                remaining--;
            }
        }.runTaskTimer(Plugin.getInstance(), 0L, 20L);
    }

    private void updateItemHologram(Material material) {
        HologramManager manager = FancyHologramsPlugin.get().getHologramManager();
        String name = getItemHoloName();
        Hologram hologram = manager.getHologram(name).orElse(null);

        if (hologram == null) {
            ItemHologramData itemData = new ItemHologramData(name, location.clone().add(0.5, 1.4, 0.5));
            itemData.setItemStack(new ItemStack(material));
            itemData.setScale(new Vector3f(0.4f, 0.4f, 0.4f));
            itemData.setBillboard(Billboard.VERTICAL);
            itemData.setPersistent(false);
            hologram = manager.create(itemData);
            manager.addHologram(hologram);
        } else {
            ItemHologramData itemData = (ItemHologramData) hologram.getData();
            itemData.setItemStack(new ItemStack(material));
        }
    }

    private void updateTextHologram(Component text) {
        String serialized = MiniMessage.miniMessage().serialize(text);
        List<String> lines = List.of(serialized.split("\n"));

        HologramManager manager = FancyHologramsPlugin.get().getHologramManager();
        String name = getTextHoloName();
        Hologram hologram = manager.getHologram(name).orElse(null);

        if (hologram == null) {
            TextHologramData textData = new TextHologramData(name, location.clone().add(0.5, 1.8, 0.5));
            textData.setBillboard(Billboard.CENTER);
            textData.setText(lines);
            textData.setPersistent(false);
            hologram = manager.create(textData);
            manager.addHologram(hologram);
        } else {
            TextHologramData textData = (TextHologramData) hologram.getData();
            textData.setText(lines);
        }
    }

    private String getItemHoloName() {
        return "node_item_" + location.hashCode();
    }

    private String getTextHoloName() {
        return "node_text_" + location.hashCode();
    }
}
