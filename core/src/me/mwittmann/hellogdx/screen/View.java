package me.mwittmann.hellogdx.screen;

class View {
    private final float gameX;
    private final float gameY;
    private final float gameWidth;
    private final float gameHeight;

    private final float screenX;
    private final float screenY;
    private final float screenWidth;
    private final float screenHeight;

    private final float xFactor;
    private final float yFactor;

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
