package io.anthills.classes;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.BlockVector;

import io.anthills.Plugin;
import io.anthills.config.NodeConfig;
import io.anthills.config.NodeConfig.NodeData;
import io.anthills.enums.NodeType;
import io.anthills.events.NodeBlockSpawnEvent;
import io.anthills.utils.LocationUtils;
import io.anthills.utils.NodeUtils;

public class Node {
    private final World world;
    private final BlockVector blockVector;
    private final NodeType nodeType;
    private NodeOption nodeOption;
    private NodeDisplayItem displayItem;
    private NodeDisplayText displayText;
    private int remainingCooldown;

    private BukkitTask cooldownTask;

    public Node(Location location, NodeType nodeType) {
        this.blockVector = LocationUtils.toBlockVector(location);
        this.world = location.getWorld();
        this.nodeType = nodeType;
        spawn();
    }

    public BlockVector getBlockVector() {
        return blockVector;
    }

    public Location getLocation() {
        return new Location(world, blockVector.getBlockX(), blockVector.getBlockY(),
                blockVector.getBlockZ());
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public NodeOption getNodeOption() {
        return nodeOption;
    }

    public int getRemainingCooldown() {
        return remainingCooldown;
    }

    public void setNodeOption(NodeOption nodeOption) {
        this.nodeOption = nodeOption;
    }

    public void spawn() {
        this.nodeOption = NodeUtils.getWeightedOption(nodeType);
        getLocation().getBlock().setType(nodeOption.getMaterial());

        if (displayItem == null) {
            Location itemLocation = getLocation().clone().add(0.5, 1.4, 0.5);
            displayItem = new NodeDisplayItem(itemLocation, nodeOption.getMaterial());
        }
        if (displayText == null) {
            Location textLocation = getLocation().clone().add(0.5, 1.8, 0.5);
            displayText = new NodeDisplayText(textLocation, NodeConfig.getNodeData(nodeType));
        }

        displayItem.update(nodeOption.getMaterial());
        displayText.update(0);

        Bukkit.getPluginManager().callEvent(new NodeBlockSpawnEvent(this));
    }

    public void delete() {
        if (cooldownTask != null) {
            cooldownTask.cancel();
            cooldownTask = null;
        }
        this.nodeOption = null;
        getLocation().getBlock().setType(Material.AIR);
        
        displayItem.delete();
        displayText.delete();
    }

    public void startCooldown() {
        NodeData nodeData = NodeConfig.getNodeData(nodeType);
        List<NodeOption> nodeOptions = nodeData.getBlockOptions();
        remainingCooldown = nodeData.getRegenTimeSeconds();

        cooldownTask = new BukkitRunnable() {
            int currentIndex = NodeUtils.getStartIndex(nodeOption, nodeType);

            @Override
            public void run() {
                if (remainingCooldown < 1) {
                    spawn();
                    cancel();
                    return;
                } else if (remainingCooldown <= 3) {
                    float basePitch = 1.0f;
                    float maxPitch = 2f;
                    float pitch = basePitch + ((5 - remainingCooldown) / 4.0f) * (maxPitch - basePitch);
                    getLocation().getWorld().playSound(getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, pitch);
                }

                currentIndex = (currentIndex + 1) % nodeOptions.size();
                NodeOption nextNodeOption = nodeOptions.get(currentIndex);
                displayItem.update(nextNodeOption.getMaterial());
                displayText.update(remainingCooldown);

                remainingCooldown--;
            }
        }.runTaskTimer(Plugin.getInstance(), 0L, 20L);
    }
}
