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
}

class GameObjects {
    private final Dimensions2d dimensions;
    List<Zombie> zombies = new ArrayList<>();
    Player player = new Player(new Position(50, 100));

    public GameObjects(Dimensions2d dimensions) {
        this.dimensions = dimensions;
    }

    public void addZombie(Zombie zombie) {
        zombies.add(zombie);
    }

    public void movePlayer(Vector2df vector) {
        player.movePosition(vector);
    }

    public void moveZombies() {
        for (Zombie zombie : zombies) {
            Vector2df v = new Vector2df(GlobalRandom.random.nextInt(100) / 10.0f - 5.0f, GlobalRandom.random.nextInt(100) / 10.0f - 5.0f);
            zombie.movePosition(v);
            if (zombie.getPosition().x < 0) {
                zombie.setPosition(zombie.getPosition().withX(0));
            } else if (zombie.getPosition().x > dimensions.x) {
                zombie.setPosition(zombie.getPosition().withX(dimensions.x));
            }

            if (zombie.getPosition().y < 0) {
                zombie.setPosition(zombie.getPosition().withY(0));
            } else if (zombie.getPosition().y > dimensions.y) {
                zombie.setPosition(zombie.getPosition().withY(dimensions.y));
            }
        }
    }
}


class GameObjectsRenderer {
    SpriteBatch batch = new SpriteBatch();

    float zombieScale = 0.5f;

    public void render(float delta, GameObjects gameObjects, View view) {
        ShapeRenderer sr = new ShapeRenderer();
        sr.setColor(1f, 0f, 0f, 1);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.circle(3, 3, 3);
        sr.circle(3, 20, 3);
        sr.end();

        batch.begin();

        for (Zombie zombie : gameObjects.zombies) {
            renderZombie(zombie, view);
        }

        renderPlayer(gameObjects.player, view);

        batch.end();
    }

    public void renderZombie(Zombie zombie, View view) {
        Animation animation;
        if (zombie.type == 0)
            animation = Assets.zombieA;
        else
            animation = Assets.zombieB;

        int x = view.translateX(zombie.getPosition().x);
        int y = view.translateY(zombie.getPosition().y);

        batch.draw(
                animation.getKeyFrame(zombie.stateTime, Animation.ANIMATION_LOOPING),
                x, y,
                0, 0,
                200, 200,
                zombieScale, zombieScale,
                0
        );
    }

    public void renderPlayer(Player player, View view) {
        TextureRegion playerTexture = Assets.player;

        int x = view.translateX(player.getPosition().x);
        int y = view.translateY(player.getPosition().y);

        batch.draw(
                playerTexture,
                x, y,
                0, 0,
                200, 200,
                zombieScale, zombieScale,
                0
        );
    }

    public void dispose() {
        batch.dispose();
    }
}



public class GameScreen extends ScreenAdapter {

    GameObjects gameObjects;
    GameObjectsRenderer gameObjectsRenderer = new GameObjectsRenderer();

    float stateTime = 0;

    private int width;
    private int height;

    public final float factor = 20.0f;

    int playerSpeed = 10;

    public GameScreen() {
        Assets.load();

        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        gameObjects = new GameObjects(new Dimensions2d(width, height));

        for (int i = 0; i < 20; i++) {
            int x = GlobalRandom.random.nextInt(width);
            int y = GlobalRandom.random.nextInt(height);
            gameObjects.addZombie(new Zombie(new Position(x, y)));
        }
    }

    int counter = 0;
    float sinceLast = 0;
    float waitFor = 0.025f;

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
        gameObjects.moveZombies();

        super.render(deltaSeconds);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        float gameWidth = 640;
        float gameHeight = 480;

        View view = new View(
            gameObjects.player.getPosition().x + 50, // The 50 is the player's game width/height atm
            gameObjects.player.getPosition().y + 50,
            gameWidth, gameHeight,
            screenWidth / 2, screenHeight / 2,
            screenWidth, screenHeight);
        gameObjectsRenderer.render(deltaSeconds, gameObjects, view);

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
    }

    @Override
    public void resize (int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void dispose () {
        gameObjectsRenderer.dispose();
        Assets.dispose();
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
