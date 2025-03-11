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
    private Set<Ant> members = new HashSet<>();
    private List<Cell> cells = new ArrayList<>();

    public Colony(UUID colonyID, String name, Ant queen) {
        this.colonyID = colonyID;
        this.name = name;
        this.queen = queen;
        addMember(queen);
    }

    public UUID getColonyId() {
        return this.colonyID;
    }

    public String getName() {
        return this.name;
    }

    public Ant getQueen() {
        return this.queen;
    }

    public Set<Ant> getMembers() {
        return this.members;
    }

    public List<Cell> getCells() {
        return this.cells;
    }

    public void addMember(Ant ant) {
        this.members.add(ant);
    }

    public void removeMember(Ant ant) {
        this.members.remove(ant);
    }

    public void addCell(Cell cell) {
        this.cells.add(cell);
    }

    public void removeCell(Cell cell) {
        this.cells.remove(cell);
    }
}
