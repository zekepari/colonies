package io.anthills.classes;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.BlockVector;

import io.anthills.Plugin;
import io.anthills.config.NodeConfig;
import io.anthills.enums.NodeType;
import io.anthills.events.NodeBlockSpawnEvent;
import io.anthills.utils.LocationUtils;
import io.anthills.utils.NodeUtils;

public class Node {
    private final World world;
    private final BlockVector blockVector;
    private final NodeType nodeType;
    private NodeOption nodeOption;
    private NodeHologram nodeHologram;
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
        NodeOption nodeOption = NodeUtils.getWeightedOption(nodeType);
        this.nodeOption = nodeOption;
        getLocation().getBlock().setType(nodeOption.getMaterial());

        if (nodeHologram == null) {
            nodeHologram = new NodeHologram(this);
        }
        nodeHologram.update();

        Bukkit.getPluginManager().callEvent(new NodeBlockSpawnEvent(this));
    }

    public void delete() {
        if (cooldownTask != null) {
            cooldownTask.cancel();
            cooldownTask = null;
        }
        this.nodeOption = null;
        getLocation().getBlock().setType(org.bukkit.Material.AIR);
        nodeHologram.delete();
    }

    public void startCooldown() {
        NodeConfig.NodeData nodeData = NodeConfig.getNodeData(nodeType);
        int nodeOptionsSize = nodeData.getBlockOptions().size();
        remainingCooldown = nodeData.getRegenTimeSeconds();
        final int startIndex = NodeUtils.getStartIndex(nodeOption, nodeType);

        cooldownTask = new BukkitRunnable() {
            int currentIndex = startIndex;

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
                nodeHologram.update();
                currentIndex = (currentIndex + 1) % nodeOptionsSize;
                remainingCooldown--;
            }
        }.runTaskTimer(Plugin.getInstance(), 0L, 20L);
    }
}
