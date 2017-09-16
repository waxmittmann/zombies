package me.mwittmann.hellogdx.util;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.mwittmann.hellogdx.screen.GameObjects;
import me.mwittmann.hellogdx.screen.View;

public class DebugDraw {
    static ShapeRenderer sr = new ShapeRenderer();
    float stateTime = 0;
    int counter = 0;

    public static void point(float x, float y, int radius) {
        sr.setColor(1f, 0f, 0f, 1);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.circle(x, y, radius);
        sr.end();
    }

    public void tick(float delta, GameObjects gameObjects) {
        if (counter >= 100) {
            counter = 0;
            System.out.println("At delta, " + delta + ", " + stateTime);
            System.out.println("Player at: " + gameObjects.getPlayerPosition());
        } else {
            counter++;
        }
        stateTime += delta;
    }
}
