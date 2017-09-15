package me.mwittmann.hellogdx.screen;

import me.mwittmann.hellogdx.entity.Player;
import me.mwittmann.hellogdx.entity.Zombie;
import me.mwittmann.hellogdx.unit.Dimensions2d;
import me.mwittmann.hellogdx.unit.Position;
import me.mwittmann.hellogdx.unit.Vector2df;

import java.util.ArrayList;
import java.util.List;

class GameObjects {
    private final Dimensions2d dimensions;
    List<Zombie> zombies = new ArrayList<>();
    Player player;

    public GameObjects(Dimensions2d dimensions) {
        this.dimensions = dimensions;
        player = new Player(new Position(dimensions.x / 2, dimensions.y / 2));
    }

    public void addZombie(Zombie zombie) {
        zombies.add(zombie);
    }

    public void movePlayer(Vector2df vector) {
        player.movePosition(vector);
    }

    public void moveZombies(float deltaSeconds) {
        for (Zombie zombie : zombies) {

            Vector2df mv = zombie.getMove(deltaSeconds);
            zombie.movePosition(mv);

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
