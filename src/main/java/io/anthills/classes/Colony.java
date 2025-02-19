package io.anthills.classes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Colony {
    private final UUID colonyID;
    private String name;
    private Ant queen;
    private final Set<Ant> members = new HashSet<>();
    private static List<PheroCell> pheroCells = new ArrayList<>();

    public Colony(UUID colonyID, String name, Ant queen) {
        this.colonyID = colonyID;
        this.name = name;
        this.queen = queen;
        addMember(queen);
    }

    public UUID getColonyId() {
        return colonyID;
    }

    public String getName() {
        return name;
    }

    public Ant getQueen() {
        return queen;
    }

    public Set<Ant> getMembers() {
        return members;
    }

    public List<PheroCell> getPheroCells() {
        return pheroCells;
    }

    public void addMember(Ant ant) {
        members.add(ant);
        ant.setColony(this);
    }

    public void addPheroCell(PheroCell cell) {
        pheroCells.add(cell);
    }
}
