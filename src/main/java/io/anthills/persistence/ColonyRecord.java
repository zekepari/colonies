package io.anthills.persistence;

import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import io.anthills.classes.Ant;
import io.anthills.classes.Colony;

@DatabaseTable(tableName = "colonies")
public class ColonyRecord {
    @DatabaseField(id = true)
    private String id;

    @DatabaseField
    private String name;

    @DatabaseField(canBeNull = true)
    private String queenId;

    public ColonyRecord() {
    }

    public ColonyRecord(Colony colony) {
        this.id = colony.getColonyId().toString();
        this.name = colony.getName();
        Ant queen = colony.getQueen();
        if (queen != null) {
            this.queenId = queen.getPlayerId().toString();
        }
    }

    public Colony toColony() {
        Colony colony = new Colony(UUID.fromString(id), name);
        return colony;
    }

    public String getQueenId() {
        return queenId;
    }

    public String getId() {
        return id;
    }
}
