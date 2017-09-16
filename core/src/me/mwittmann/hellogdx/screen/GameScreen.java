package me.mwittmann.hellogdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import me.mwittmann.hellogdx.asset.Assets;
import me.mwittmann.hellogdx.entity.Zombie;
import me.mwittmann.hellogdx.unit.Dimensions2d;
import me.mwittmann.hellogdx.unit.Vector2df;
import me.mwittmann.hellogdx.unit.Position;
import me.mwittmann.hellogdx.util.DebugDraw;
import me.mwittmann.hellogdx.util.GlobalRandom;

public class GameScreen extends ScreenAdapter {

    GameObjects gameObjects;
    GameObjectsRenderer gameObjectsRenderer = new GameObjectsRenderer();

    DebugDraw debug = new DebugDraw();

    public final float factor = 5.0f;

    int playerSpeed = 10;

    float sinceLast = 0;
    float waitFor = 0.025f;

    float gameFactor = 0.1f;

    Dimensions2d gameDimensions = new Dimensions2d(800, 800);

    public GameScreen() {
        Assets.load();

        gameObjects = new GameObjects(gameDimensions);

        for (int i = 0; i < 200; i++) {
            int x = GlobalRandom.random.nextInt((int)gameDimensions.x);
            int y = GlobalRandom.random.nextInt((int)gameDimensions.y);
            gameObjects.addZombie(new Zombie(new Position(x, y)));
        }
    }

    @Override
    public void render(float deltaSeconds) {
        sinceLast += deltaSeconds;
        if (sinceLast < waitFor) {
            return;
        } else {
            deltaSeconds = sinceLast;
            sinceLast = 0;
        }

        movePlayer(deltaSeconds);
        gameObjects.moveZombies(deltaSeconds);

        super.render(deltaSeconds);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        float gameWidth = screenWidth * gameFactor;
        float gameHeight = screenHeight * gameFactor;

        View view = new View(
            gameObjects.player.getPosition().x + gameObjects.player.getDimensions().x / 2,
            gameObjects.player.getPosition().y + gameObjects.player.getDimensions().y / 2,
            gameWidth, gameHeight,
            screenWidth / 2, screenHeight / 2,
            screenWidth, screenHeight);

        gameObjectsRenderer.render(gameObjects, view);

        debug.tick(deltaSeconds, gameObjects);
    }

    private void movePlayer(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            gameObjects.movePlayer(new Vector2df(0f, playerSpeed * delta * factor));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            gameObjects.movePlayer(new Vector2df(-playerSpeed * delta * factor, 0f));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            gameObjects.movePlayer(new Vector2df(playerSpeed * delta * factor, 0f));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            gameObjects.movePlayer(new Vector2df(0f, -playerSpeed * delta * factor));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
            this.gameFactor *= (1 + 0.5 * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.EQUALS)) {
            this.gameFactor /= (1 + 0.5 * delta);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            for (int i = 0; i < 200; i++) {
                int x = GlobalRandom.random.nextInt((int) gameDimensions.x);
                int y = GlobalRandom.random.nextInt((int) gameDimensions.y);
                gameObjects.addZombie(new Zombie(new Position(x, y)));
            }
        }

        if (this.gameFactor < 0.01)
            this.gameFactor = 0.01f;
        if (this.gameFactor > 10.0f)
            this.gameFactor = 10.0f;

    }

    @Override
    public void dispose () {
        gameObjectsRenderer.dispose();
        Assets.dispose();
    }
}
