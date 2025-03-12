package io.anthills.persistence;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import io.anthills.classes.Node;
import io.anthills.enums.NodeType;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

@DatabaseTable(tableName = "nodes")
public class NodeRecord {
    @DatabaseField(id = true)
    private String id;

    @DatabaseField
    private int blockX;

    @DatabaseField
    private int blockY;

    @DatabaseField
    private int blockZ;

    @DatabaseField
    private String nodeType;

    @DatabaseField
    private String worldName;

    public NodeRecord() {
    }

    public NodeRecord(Node node) {
        this.id = node.getBlockVector().toString();
        this.blockX = node.getBlockVector().getBlockX();
        this.blockY = node.getBlockVector().getBlockY();
        this.blockZ = node.getBlockVector().getBlockZ();
        this.nodeType = node.getNodeType().name();
        this.worldName = node.getLocation().getWorld().getName();
    }

    public Node toNode() {
        World world = Bukkit.getWorld(worldName);
        NodeType nodeType = NodeType.valueOf(this.nodeType);
        Node node = new Node(new Location(world, blockX, blockY, blockZ), nodeType);
        return node;
    }

    public String getId() {
        return id;
    }
}
