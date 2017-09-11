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
import me.mwittmann.hellogdx.util.GlobalRandom;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends ScreenAdapter {
    SpriteBatch batch;
    float stateTime = 0;

    List<Zombie> zombies = new ArrayList<>();

    private int width = 500;
    private int height = 500;

    public GameScreen() {
        batch = new SpriteBatch();
        Assets.load();

        for (int i = 0; i < 20; i++) {
            int x = GlobalRandom.random.nextInt(width);
            int y = GlobalRandom.random.nextInt(height);
            zombies.add(new Zombie(new Position(x, y)));
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        for (Zombie zombie : zombies) {
            zombie.render(batch, delta);
        }
        batch.end();

        stateTime += delta;
    }

    @Override
    public void resize (int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void dispose () {
        batch.dispose();
        Assets.dispose();
    }
}
