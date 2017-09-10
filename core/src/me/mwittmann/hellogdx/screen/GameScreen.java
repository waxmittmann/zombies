package me.mwittmann.hellogdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.mwittmann.hellogdx.asset.Animation;
import me.mwittmann.hellogdx.asset.Assets;
import me.mwittmann.hellogdx.entity.Zombie;
import me.mwittmann.hellogdx.unit.Position;

public class GameScreen extends ScreenAdapter {
    SpriteBatch batch;
    float stateTime = 0;

    Zombie zombieA = new Zombie(new Position(100, 200));

    Zombie zombieB = new Zombie(new Position(25, 100));

    public GameScreen() {
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
