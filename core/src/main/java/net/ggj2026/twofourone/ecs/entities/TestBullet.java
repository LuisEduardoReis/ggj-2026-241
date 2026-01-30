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

        SpriteComponent spriteComponent = bullet.getComponent(SpriteComponent.class);
        spriteComponent.addSprite(SpriteAssets.testSprite);
        spriteComponent.states.get(0).scaleX = 0.5f;
        spriteComponent.states.get(0).scaleY = 0.5f;

        return bullet;
    }

}
