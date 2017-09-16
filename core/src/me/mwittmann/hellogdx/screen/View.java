package me.mwittmann.hellogdx.screen;

public class View {
    public final float gameX;
    public final float gameY;
    public final float gameWidth;
    public final float gameHeight;

    public final float screenX;
    public final float screenY;
    public final float screenWidth;
    public final float screenHeight;

    public final float xFactor;
    public final float yFactor;

    public View(
        float gameX, float gameY, float gameWidth, float gameHeight,
        int screenX, int screenY, int screenWidth, int screenHeight
    ) {
        this.gameX = gameX;
        this.gameY = gameY;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;

        this.screenX = screenX;
        this.screenY = screenY;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.xFactor = screenWidth / gameWidth;
        this.yFactor = screenHeight / gameHeight;
    }

    public int translateX(float x) {
        return (int)((x - gameX) * xFactor + screenX);
    }

    public int translateY(float y) {
        return (int)((y - gameY) * yFactor + screenY);
    }

    public int translateWidth(float x) {
        return (int)(x * xFactor);
    }

    public int translateHeight(float y) {
        return (int)(y * yFactor);
    }
}
