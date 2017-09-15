package me.mwittmann.hellogdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.mwittmann.hellogdx.asset.Animation;
import me.mwittmann.hellogdx.asset.Assets;
import me.mwittmann.hellogdx.entity.Player;
import me.mwittmann.hellogdx.entity.Zombie;
import me.mwittmann.hellogdx.unit.Dimensions2d;
import me.mwittmann.hellogdx.unit.Vector2df;
import me.mwittmann.hellogdx.unit.Position;
import me.mwittmann.hellogdx.unit.Vector2di;
import me.mwittmann.hellogdx.util.GlobalRandom;

import java.util.ArrayList;
import java.util.List;


public class GameScreen extends ScreenAdapter {

    GameObjects gameObjects;
    GameObjectsRenderer gameObjectsRenderer = new GameObjectsRenderer();

    float stateTime = 0;

    public final float factor = 5.0f;

    int playerSpeed = 10;

    int counter = 0;
    float sinceLast = 0;
    float waitFor = 0.025f;

    float gameFactor = 0.1f;

    public GameScreen() {
        Assets.load();

        Dimensions2d gameDimensions = new Dimensions2d(800, 800);

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

        // Not working
//        float gameWidth =  screenWidth / 10;
//        float gameHeight = screenHeight / 10;

        float gameWidth = screenWidth * gameFactor;
        float gameHeight = screenHeight * gameFactor;

        View view = new View(
            gameObjects.player.getPosition().x + gameObjects.player.getDimensions().x / 2, // The 50 is the player's game width/height atm
            gameObjects.player.getPosition().y + gameObjects.player.getDimensions().y / 2,
            gameWidth, gameHeight,
            screenWidth / 2, screenHeight / 2,
            screenWidth, screenHeight);
        gameObjectsRenderer.render(gameObjects, view);

        stateTime += deltaSeconds;
        printTicksCounter(deltaSeconds, gameObjects);
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


    @Override
    public void resize (int width, int height) {
    }

    private void printTicksCounter(float delta, GameObjects gameObjects) {
        if (counter >= 100) {
            counter = 0;
            System.out.println("At delta, " + delta + ", " + stateTime);
            System.out.println("Player at: " + gameObjects.player.getPosition());
        } else {
            counter++;
        }
    }
}
