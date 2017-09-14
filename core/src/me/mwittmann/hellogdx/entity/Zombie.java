package me.mwittmann.hellogdx.entity;

import me.mwittmann.hellogdx.unit.Position;
import me.mwittmann.hellogdx.unit.Vector2df;
import me.mwittmann.hellogdx.util.GlobalRandom;

// Todo: Inc state time
public class Zombie {
    private Position position;

    // Todo: Should store in renderer probably
    public float stateTime = 0;

    public final int type;

    public Zombie(Position position) {
        this.position = position;

        stateTime += GlobalRandom.random.nextFloat();

        type = GlobalRandom.random.nextInt(2);
    }

    public void movePosition(Vector2df vector) {
        position = position.incX(vector.x).incY(vector.y);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
