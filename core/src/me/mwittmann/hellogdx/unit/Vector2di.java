package me.mwittmann.hellogdx.unit;

public class Vector2di extends Vector2d<Integer> {
    public Vector2di(Integer x, Integer y) {
        super(x, y);
    }

    public Vector2di(Float x, Float y) {
        super(x.intValue(), y.intValue());
    }
}
