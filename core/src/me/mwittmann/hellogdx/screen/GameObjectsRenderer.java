package me.mwittmann.hellogdx.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.mwittmann.hellogdx.asset.Animation;
import me.mwittmann.hellogdx.asset.Assets;
import me.mwittmann.hellogdx.entity.Player;
import me.mwittmann.hellogdx.entity.Zombie;

class GameObjectsRenderer {
    SpriteBatch batch = new SpriteBatch();

    float zombieScale = 0.5f;

    public void render(GameObjects gameObjects, View view) {
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

        int width   = view.translateWidth(zombie.getDimensions().x);
        int height  = view.translateHeight(zombie.getDimensions().y);

        batch.draw(
                animation.getKeyFrame(zombie.stateTime, Animation.ANIMATION_LOOPING),
                x, y,
                0, 0,
                width, height,
                //zombieScale, zombieScale,
                1.0f, 1.0f,
                0
        );
    }

    public void renderPlayer(Player player, View view) {
        TextureRegion playerTexture = Assets.player;

        int x = view.translateX(player.getPosition().x);
        int y = view.translateY(player.getPosition().y);

        int width   = view.translateWidth(player.getDimensions().x);
        int height  = view.translateHeight(player.getDimensions().y);

        batch.draw(
                playerTexture,
                x, y,
                0, 0,
                width, height,
                1.0f, 1.0f,
                //zombieScale, zombieScale,
                0
        );
    }

    public void dispose() {
        batch.dispose();
    }
}
