package me.mwittmann.hellogdx.entity;

import me.mwittmann.hellogdx.unit.Dimensions2d;
import me.mwittmann.hellogdx.unit.Position;
import me.mwittmann.hellogdx.unit.Vector2df;
import me.mwittmann.hellogdx.util.GlobalRandom;

// Todo: Inc state time
public class Zombie {
    private Position position;

    // Todo: Should store in renderer probably
    public float stateTime = 0;

    public final int type;
    private Dimensions2d dimensions = new Dimensions2d(10.0f, 10.0f);

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

    public Dimensions2d getDimensions() {
        return dimensions;
    }


    private Vector2df currentDirection = new Vector2df(
        GlobalRandom.random.nextInt(10) - 5f,
        GlobalRandom.random.nextInt(10) - 5f
    );
    float movedCurrentDirection = 0.0f;

    public Vector2df getMove(float deltaSeconds) {
        stateTime += deltaSeconds;
        
        // If we've moved in the same direction for > 1 sec, consider changing direction
        if (movedCurrentDirection >= 1.0) {
            // 30% chance of direction change
            if (GlobalRandom.random.nextInt(10) > 7) {
                currentDirection = new Vector2df(
                        GlobalRandom.random.nextInt(10) - 5f,
                        GlobalRandom.random.nextInt(10) - 5f
                );
            }
            // Else keep going, redecide in half a second
            movedCurrentDirection = 0.5f;
        }
        movedCurrentDirection += deltaSeconds;

        return new Vector2df(currentDirection.x * deltaSeconds, currentDirection.y * deltaSeconds);
    }
}
