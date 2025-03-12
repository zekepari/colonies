package io.anthills.classes;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Colony {
    private final UUID colonyID;
    private String name;
    private Ant queen;
    private Set<Ant> members = new HashSet<>();
    private Set<Cell> cells = new HashSet<>();

    public Colony(UUID colonyID, String name) {
        this.colonyID = colonyID;
        this.name = name;
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

    public Set<Cell> getCells() {
        return this.cells;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQueen(Ant queen) {
        this.members.add(queen);
        this.queen = queen;
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
