package me.mwittmann.hellogdx.unit;

public class Position {
    public final int x;
    public final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position withX(int _x) {
        return new Position(_x, y);
    }

    public Position withY(int _y) {
        return new Position(x, _y);
    }
}
