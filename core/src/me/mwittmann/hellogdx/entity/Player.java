package me.mwittmann.hellogdx.entity;

import me.mwittmann.hellogdx.unit.Vector2df;
import me.mwittmann.hellogdx.unit.Position;

public class Player {
    private Position position;

    // Todo: Inc state time
    float stateTime = 0;

    public Player(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position p) {
        this.position = p;
    }

    public void movePosition(Vector2df vector) {
        position = position.incX(vector.x).incY(vector.y);
    }
}
