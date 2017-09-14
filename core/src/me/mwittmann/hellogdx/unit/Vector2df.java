package me.mwittmann.hellogdx.unit;

abstract class Vector2d<S> {
    public final S x, y;

    public Vector2d(S x, S y) {
        this.x = x;
        this.y = y;
    }
}


public class Vector2df extends Vector2d<Float> {
    public Vector2df(Float x, Float y) {
        super(x, y);
    }
}

