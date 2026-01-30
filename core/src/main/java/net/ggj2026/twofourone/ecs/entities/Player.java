package net.ggj2026.twofourone.ecs.entities;

import net.ggj2026.twofourone.ecs.components.*;
import net.ggj2026.twofourone.level.Level;
import net.ggj2026.twofourone.sprites.SpriteAssets;

public class Player {
    public static Entity instance(Level level) {
        Entity player = new Entity(level)
                .addComponent(new PositionComponent())
                .addComponent(new PlayerComponent())
                .addComponent(new SpriteComponent())
                .addComponent(new VelocityComponent())
                .addComponent(new LevelCollisionComponent());

        player.getComponent(SpriteComponent.class).sprite = SpriteAssets.testAnimatedSprite;

        return player;
    }
}
