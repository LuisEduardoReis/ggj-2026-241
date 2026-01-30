package net.ggj2026.twofourone.ecs.entities;

import net.ggj2026.twofourone.ecs.components.*;
import net.ggj2026.twofourone.level.Level;
import net.ggj2026.twofourone.sprites.SpriteAssets;

public class TestBullet {

    public static Entity instance(Level level) {
        Entity bullet = new Entity(level)
                .addComponent(new PositionComponent())
                .addComponent(new VelocityComponent())
                .addComponent(new SpriteComponent())
                .addComponent(new BulletComponent());

        bullet.getComponent(SpriteComponent.class).sprite = SpriteAssets.testSprite;
        bullet.getComponent(SpriteComponent.class).state.scaleX = 0.5f;
        bullet.getComponent(SpriteComponent.class).state.scaleY = 0.5f;

        return bullet;
    }

}
