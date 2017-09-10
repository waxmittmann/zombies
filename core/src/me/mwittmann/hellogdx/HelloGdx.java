package me.mwittmann.hellogdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import javax.xml.soap.Text;

class Assets {
    public static Texture sheet;
    public static Animation zombieA;
    public static Animation zombieB;

    public static void load() {
        sheet = loadTexture("ZombiesSpritesheet2.png");

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
    }

    public static Texture loadTexture (String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public static void dispose() {
        sheet.dispose();
        // Need to dispose animation keyframes?
    }
}

class HelloGdxScreen extends ScreenAdapter {
    SpriteBatch batch;
    //Texture img;
    float stateTime = 0;

    public HelloGdxScreen() {
        batch = new SpriteBatch();
        Assets.load();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        ShapeRenderer shapeRenderer = new ShapeRenderer();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 0, 0);
        shapeRenderer.rect(0, 0, 200, 200);
        shapeRenderer.rect(200, 0, 200, 200);
        shapeRenderer.end();

        batch.begin();

        TextureRegion keyFrame = Assets.zombieA.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
        batch.draw(keyFrame, 0, 0);

        TextureRegion keyFrame2 = Assets.zombieB.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
        batch.draw(keyFrame2, 200, 0);

        batch.end();

        stateTime += delta;
    }

    @Override
    public void dispose () {
        batch.dispose();
        Assets.dispose();
    }
}

public class HelloGdx extends Game {

    @Override
    public void create () {
        this.setScreen(new HelloGdxScreen());
    }

    @Override
    public void render () {
        super.render();
    }
}
