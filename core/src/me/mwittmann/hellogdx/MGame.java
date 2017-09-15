package me.mwittmann.hellogdx;

import com.badlogic.gdx.Gdx;
import me.mwittmann.hellogdx.screen.GameScreen;

public class MGame extends com.badlogic.gdx.Game {

    @Override
    public void create () {
        Gdx.graphics.setTitle("Zombies !!!");
        this.setScreen(new GameScreen());
    }

    @Override
    public void render () {
        super.render();
    }
}
