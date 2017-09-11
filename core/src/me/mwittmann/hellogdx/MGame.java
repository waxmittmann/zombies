package me.mwittmann.hellogdx;

import me.mwittmann.hellogdx.screen.GameScreen;

public class MGame extends com.badlogic.gdx.Game {

    @Override
    public void create () {
        this.setScreen(new GameScreen());
    }

    @Override
    public void render () {
        super.render();
    }
}
