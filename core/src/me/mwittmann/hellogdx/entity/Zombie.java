package me.mwittmann.hellogdx.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import me.mwittmann.hellogdx.asset.Animation;
import me.mwittmann.hellogdx.asset.Assets;
import me.mwittmann.hellogdx.unit.Position;
import me.mwittmann.hellogdx.util.GlobalRandom;

public class Zombie {
    private final Position position;
    private final Animation animation;

    float stateTime = 0;
    float scale = 0.5f;

    public Zombie(Position position) {
        this.position = position;

        stateTime += GlobalRandom.random.nextFloat();

        int type = GlobalRandom.random.nextInt(2);
        if (type == 0)
            animation = Assets.zombieA;
        else
            animation = Assets.zombieB;
    }

    public void render(SpriteBatch batch, float delta) {
        TextureRegion keyFrame = animation.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);

        batch.draw(keyFrame, position.x, position.y, 0, 0, 200, 200, scale, scale, 0);

        stateTime += delta;
    }
}
