package me.mwittmann.hellogdx;

import com.badlogic.gdx.Game;
import me.mwittmann.hellogdx.screen.GameScreen;

public class Game extends com.badlogic.gdx.Game {

    @Override
    public void create () {
        this.setScreen(new GameScreen());
    }

    @Override
    public void render () {
        super.render();
    }
}
