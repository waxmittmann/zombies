package me.mwittmann.hellogdx.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    public static Texture sheet;
    private static Texture sheet2;

    public static TextureRegion player;
    public static Animation zombieA;
    public static Animation zombieB;

    public static void load() {
        sheet = loadTexture("ZombiesSpritesheet2.png");
        sheet2 = loadTexture("PlayerSpritesheet.png");

        //spring = new TextureRegion(sheet, 128, 0, 32, 32);
        zombieA = new Animation(
            0.2f,
            new TextureRegion(sheet, 0, 0, 200, 200),
            new TextureRegion(sheet, 0, 200, 200, 200),
            new TextureRegion(sheet, 0, 400, 200, 200),
            new TextureRegion(sheet, 0, 600, 200, 200)
        );

        zombieB = new Animation(
            0.2f,
            new TextureRegion(sheet, 200, 0, 200, 200),
            new TextureRegion(sheet, 200, 200, 200, 200),
            new TextureRegion(sheet, 200, 400, 200, 200),
            new TextureRegion(sheet, 200, 600, 200, 200)
        );

        player = new TextureRegion(sheet2, 0, 0, 200, 200);
    }

    public static Texture loadTexture (String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public static void dispose() {
        sheet.dispose();
        // Need to dispose animation keyframes?
    }
}
