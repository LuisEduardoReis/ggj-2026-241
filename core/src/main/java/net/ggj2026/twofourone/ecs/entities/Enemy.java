package net.ggj2026.twofourone.ecs.entities;

import net.ggj2026.twofourone.ecs.components.*;
import net.ggj2026.twofourone.level.Level;
import net.ggj2026.twofourone.sprites.SpriteAssets;

public class Enemy {
    public static Entity instance(Level level) {
        Entity player = new Entity(level)
            .addComponent(new EnemyComponent())
            .addComponent(new PositionComponent())
            .addComponent(new VelocityComponent())
            .addComponent(new SpriteComponent())
            .addComponent(new LevelCollisionComponent())
            .addComponent(new EntityCollisionsComponent());

        SpriteComponent spriteComponent = player.getComponent(SpriteComponent.class);
        spriteComponent.addSprite(SpriteAssets.kasperSprite);
        spriteComponent.addSprite(SpriteAssets.playerSprite);
        spriteComponent.states.get(0).scaleX = 1;
        spriteComponent.states.get(0).scaleY = 1;
        spriteComponent.states.get(1).x = -0.5f/16f;
        spriteComponent.states.get(1).y = 0.2f;
        spriteComponent.states.get(1).scaleX = 0.75f;
        spriteComponent.states.get(1).scaleY = 0.75f;


        return player;
    }
}
