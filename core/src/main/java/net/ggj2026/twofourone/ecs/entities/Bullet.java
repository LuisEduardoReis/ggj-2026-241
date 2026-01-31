package net.ggj2026.twofourone.ecs.entities;

import net.ggj2026.twofourone.Util;
import net.ggj2026.twofourone.ecs.components.*;
import net.ggj2026.twofourone.level.Level;
import net.ggj2026.twofourone.sprites.SpriteAssets;

import java.util.Arrays;
import java.util.Collection;

public class Bullet {
    public static Entity instance(Level level) {
        Entity bullet = new Entity(level)
                .addComponent(new PositionComponent())
                .addComponent(new VelocityComponent())
                .addComponent(new SpriteComponent())
                .addComponent(new BulletComponent())
                .addComponent(new EntityCollisionsComponent())
                .addComponent(new LevelCollisionComponent());

        SpriteComponent spriteComponent = bullet.getComponent(SpriteComponent.class);
        spriteComponent.addSprite(SpriteAssets.testSprite);
        spriteComponent.states.get(0).scaleX = 0.5f;
        spriteComponent.states.get(0).scaleY = 0.5f;

        LevelCollisionComponent levelCollisionComponent = bullet.getComponent(LevelCollisionComponent.class);
        levelCollisionComponent.radius = 0.2f;
        levelCollisionComponent.handleLevelCollision = (x,y) -> bullet.remove = true;

        EntityCollisionsComponent entityCollisionsComponent = bullet.getComponent(EntityCollisionsComponent.class);
        entityCollisionsComponent.radius = 0.2f;
        entityCollisionsComponent.pushesOthers = false;
        entityCollisionsComponent.handleEntityCollision = (me, other) -> {
            if (other.hasComponent(EnemyComponent.class)) {
                me.remove = true;
                PositionComponent pos = me.getComponent(PositionComponent.class);
                PositionComponent otherPos = other.getComponent(PositionComponent.class);
                VelocityComponent otherVelocity = other.getComponent(VelocityComponent.class);
                float dist = Util.pointDistance(pos.x, pos.y, otherPos.x, otherPos.y);
                otherVelocity.ex = (otherPos.x - pos.x) / dist * 10;
                otherVelocity.ey = (otherPos.y - pos.y) / dist * 10;
            }
        };

        return bullet;
    }

}
