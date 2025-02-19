package io.anthills.classes;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Player;

public class Colony {
    private final UUID colonyID;
    private String name;
    private UUID queen;
    private final Set<UUID> memberUUIDs;
    private final Set<PheroCell> pheroCells = new HashSet<>();

    public Colony(UUID colonyID, String name, UUID queen) {
        this.colonyID = colonyID;
        this.name = name;
        this.queen = queen;
        this.memberUUIDs = new HashSet<>();
        this.memberUUIDs.add(queen);
    }

    public UUID getUniqueId() {
        return colonyID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getQueen() {
        return queen;
    }

    public void setQueen(UUID queen) {
        this.queen = queen;
    }

    public boolean addMember(UUID member) {
        return memberUUIDs.add(member);
    }

    public boolean removeMember(Player player) {
        if (player.getUniqueId().equals(queen)) {
            return false;
        }
        return memberUUIDs.remove(player.getUniqueId());
    }

    public boolean isMember(Player player) {
        return memberUUIDs.contains(player.getUniqueId());
    }

    public Set<UUID> getMembers() {
        return new HashSet<>(memberUUIDs);
    }

    public Set<PheroCell> getPheroCells() {
        return new HashSet<>(pheroCells);
    }

    public boolean addPheroCell(PheroCell pheroCell) {
        return pheroCells.add(pheroCell);
    }

    public boolean removePheroCell(PheroCell pheroCell) {
        return pheroCells.remove(pheroCell);
    }
}
