package me.mwittmann.hellogdx.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import me.mwittmann.hellogdx.asset.Animation;
import me.mwittmann.hellogdx.asset.Assets;
import me.mwittmann.hellogdx.unit.Position;
import me.mwittmann.hellogdx.util.GlobalRandom;

public class Player {
    private Position position;

    float stateTime = 0;
    float scale = 0.5f;

    public Player(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position p) {
        this.position = p;
    }

    public void render(SpriteBatch batch, float delta) {
        TextureRegion keyFrame = Assets.player;

        batch.draw(keyFrame, position.x, position.y, 0, 0, 200, 200, scale, scale, 0);

        stateTime += delta;
    }
}
