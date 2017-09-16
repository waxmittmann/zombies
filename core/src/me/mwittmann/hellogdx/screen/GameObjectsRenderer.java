package me.mwittmann.hellogdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import me.mwittmann.hellogdx.asset.Animation;
import me.mwittmann.hellogdx.asset.Assets;
import me.mwittmann.hellogdx.entity.Player;
import me.mwittmann.hellogdx.entity.Zombie;
import me.mwittmann.hellogdx.util.DebugDraw;

class GameObjectsRenderer {
    SpriteBatch batch = new SpriteBatch();

    public void render(GameObjects gameObjects, View view) {
        DebugDraw.point(view.screenWidth / 2, view.screenHeight / 2, 10);

        batch.begin();
        batch.setProjectionMatrix(batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

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

        TextureRegion frame = animation.getKeyFrame(zombie.stateTime, Animation.ANIMATION_LOOPING);
        batch.draw(frame, x, y, width, height);
    }

    public void renderPlayer(Player player, View view) {
        TextureRegion playerTexture = Assets.player;

        int x = view.translateX(player.getPosition().x);
        int y = view.translateY(player.getPosition().y);

        int width   = view.translateWidth(player.getDimensions().x);
        int height  = view.translateHeight(player.getDimensions().y);

        batch.draw(playerTexture, x, y, width, height);
    }

    public void dispose() {
        batch.dispose();
    }
}
