package io.anthills.persistence;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import io.anthills.classes.Ant;
import io.anthills.classes.Colony;

import java.util.UUID;

@DatabaseTable(tableName = "ants")
public class AntRecord {
    @DatabaseField(id = true)
    private String id;

    @DatabaseField(canBeNull = true)
    private String colonyId;

    public AntRecord() {
    }

    public AntRecord(Ant ant) {
        this.id = ant.getPlayerId().toString();
        Colony colony = ant.getColony();
        if (colony != null) {
            this.colonyId = colony.getColonyId().toString();
        }
    }

    public Ant toAnt() {
        Ant ant = new Ant(UUID.fromString(id));
        if (colonyId != null) {
        }
        return ant;
    }

    public String getColonyId() {
        return colonyId;
    }

    public String getId() {
        return id;
    }
}
