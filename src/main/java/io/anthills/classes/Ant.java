package io.anthills.classes;

import java.util.UUID;

public class Ant {
    private final UUID playerId;
    private Colony colony;

    public Ant(UUID playerId) {
        this.playerId = playerId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public Colony getColony() {
        return colony;
    }

    public void setColony(Colony colony) {
        this.colony = colony;
    }
}
