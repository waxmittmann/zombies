package me.mwittmann.hellogdx.unit;

public class Position {
    public final float x;
    public final float y;

    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Position withX(float _x) {
        return new Position(_x, y);
    }

    public Position withY(float _y) {
        return new Position(x, _y);
    }

    public Position incY(float yDelta) {
        return new Position(x, y + yDelta);
    }

    public Position incX(float xDelta) {
        return new Position(x + xDelta, y);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
