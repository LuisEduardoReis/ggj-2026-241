package net.ggj2026.twofourone.ecs.entities;

import com.badlogic.gdx.math.Vector2;
import net.ggj2026.twofourone.Util;
import net.ggj2026.twofourone.ecs.components.*;
import net.ggj2026.twofourone.gamelogic.BulletType;
import net.ggj2026.twofourone.level.Level;
import net.ggj2026.twofourone.sprites.EntityZ;
import net.ggj2026.twofourone.sprites.SpriteAssets;

public class Bullet {
    public static Entity instance(Level level) {
        Entity bullet = new Entity(level)
                .addComponent(new PositionComponent())
                .addComponent(new VelocityComponent())
                .addComponent(new SpriteComponent())
                .addComponent(new BulletComponent())
                .addComponent(new EntityCollisionsComponent())
                .addComponent(new LevelCollisionComponent());

        bullet.z = EntityZ.FRIENDLY_BULLETS;
        SpriteComponent spriteComponent = bullet.getComponent(SpriteComponent.class);
        spriteComponent.addSprite(SpriteAssets.blueFireSprite);
        spriteComponent.states.get(0).scale = 0.75f;
        spriteComponent.states.get(0).animated = true;
        spriteComponent.states.get(0).rotationDelta = (float) (2 * 2*Math.PI);

        LevelCollisionComponent levelCollisionComponent = bullet.getComponent(LevelCollisionComponent.class);
        levelCollisionComponent.radius = 0.2f;
        levelCollisionComponent.handleLevelCollision = (x,y) -> {
            bullet.remove = !levelCollisionComponent.bounce;
        };

        EntityCollisionsComponent entityCollisionsComponent = bullet.getComponent(EntityCollisionsComponent.class);
        entityCollisionsComponent.radius = 0.2f;
        entityCollisionsComponent.pushesOthers = false;
        entityCollisionsComponent.handleEntityCollision = (me, other) -> {
            BulletComponent bulletComponent = me.getComponent(BulletComponent.class);

            if (bulletComponent.friendly && other.hasComponent(EnemyComponent.class)) {
                me.remove = true;

                // Bump the enemy
                PositionComponent pos = me.getComponent(PositionComponent.class);
                PositionComponent otherPos = other.getComponent(PositionComponent.class);
                VelocityComponent otherVelocity = other.getComponent(VelocityComponent.class);
                EntityCollisionsComponent otherEntityCollisionsComponent = other.getComponent(EntityCollisionsComponent.class);
                float dist = Util.pointDistance(pos.x, pos.y, otherPos.x, otherPos.y);
                otherVelocity.ex = (otherPos.x - pos.x) / dist * 10 / otherEntityCollisionsComponent.mass;
                otherVelocity.ey = (otherPos.y - pos.y) / dist * 10 / otherEntityCollisionsComponent.mass;

                // Deal damage
                other.getComponent(EnemyComponent.class).damage(bulletComponent.damage);
            }

            if (!bulletComponent.friendly && other.hasComponent(PlayerComponent.class)) {
                me.remove = true;

                // Bump the player
                PositionComponent pos = me.getComponent(PositionComponent.class);
                PositionComponent otherPos = other.getComponent(PositionComponent.class);
                VelocityComponent otherVelocity = other.getComponent(VelocityComponent.class);
                EntityCollisionsComponent otherEntityCollisionsComponent = other.getComponent(EntityCollisionsComponent.class);
                float dist = Util.pointDistance(pos.x, pos.y, otherPos.x, otherPos.y);
                otherVelocity.ex = (otherPos.x - pos.x) / dist * 10 / otherEntityCollisionsComponent.mass;
                otherVelocity.ey = (otherPos.y - pos.y) / dist * 10 / otherEntityCollisionsComponent.mass;

                // Deal damage
                PlayerComponent playerComponent = other.getComponent(PlayerComponent.class);
                playerComponent.damage(bulletComponent.damage, other);
            }
        };

        return bullet;
    }

    public static Entity spawnBullet(Level level, Vector2 position, float dir, BulletType type) {
        Entity bullet = Bullet.instance(level);
        BulletComponent bulletComponent = bullet.getComponent(BulletComponent.class);
        SpriteComponent sprite = bullet.getComponent(SpriteComponent.class);
        LevelCollisionComponent levelCollisionComponent = bullet.getComponent(LevelCollisionComponent.class);

        sprite.sprites.set(0, BulletType.bulletSprites.get(type));
        switch (type) {
            case NORMAL:
                bulletComponent.damage = 50;
                break;
            case TRIPLE:
                bulletComponent.damage = 50;
                levelCollisionComponent.bounce = true;
                break;
            case HIGH_DAMAGE:
                bulletComponent.damage = 100;
                break;
            case ENEMY:
                bulletComponent.damage = 25;
                bulletComponent.speed = 10;
                bulletComponent.friendly = false;
                bullet.z = EntityZ.ENEMY_BULLETS;
                break;
        }

        float bulletSpeed = bulletComponent.speed;
        bullet.getComponent(PositionComponent.class).set(position.x, position.y);
        bullet.getComponent(VelocityComponent.class).set(
            (float) (bulletSpeed * Math.cos(dir)),
            (float) (bulletSpeed * Math.sin(dir))
        );
        level.addEntity(bullet);
        return bullet;
    }
}
