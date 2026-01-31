package net.ggj2026.twofourone.ecs.entities;

import net.ggj2026.twofourone.Util;
import net.ggj2026.twofourone.ecs.components.*;
import net.ggj2026.twofourone.gamelogic.MaskType;
import net.ggj2026.twofourone.level.Level;
import net.ggj2026.twofourone.sprites.EntityZ;
import net.ggj2026.twofourone.sprites.SpriteAssets;

public class Enemy {
    public static Entity instance(Level level) {
        Entity enemy = new Entity(level)
            .addComponent(new EnemyComponent())
            .addComponent(new PositionComponent())
            .addComponent(new VelocityComponent())
            .addComponent(new SpriteComponent())
            .addComponent(new LevelCollisionComponent())
            .addComponent(new EntityCollisionsComponent());
        enemy.z = EntityZ.ENEMIES;

        PositionComponent position = enemy.getComponent(PositionComponent.class);
        EnemyComponent enemyComponent = enemy.getComponent(EnemyComponent.class);
        if (Math.random() < 0.25) {
            enemyComponent.maskType = MaskType.values()[Util.randomRangeInt(0, MaskType.values().length)];
        }

        SpriteComponent spriteComponent = enemy.getComponent(SpriteComponent.class);
        spriteComponent.addSprite(SpriteAssets.kasperSprite);
        spriteComponent.states.get(0).scale = 1;
        spriteComponent.states.get(0).animated = true;
        spriteComponent.states.get(0).animationDelay = 1 / 3f / 2f;

        if (enemyComponent.maskType != null) {
            spriteComponent.addSprite(MaskType.maskSprites.get(enemyComponent.maskType));
            spriteComponent.states.get(1).x = -0.5f/16f;
            spriteComponent.states.get(1).y = 0.2f;
            spriteComponent.states.get(1).scale = 0.75f;
        }

        EntityCollisionsComponent entityCollisionsComponent = enemy.getComponent(EntityCollisionsComponent.class);
        entityCollisionsComponent.handleEntityCollision = (me, other) -> {
            if (other.hasComponent(PlayerComponent.class)) {
                if (enemyComponent.attackTimer == 0) {
                    enemyComponent.attackTimer = enemyComponent.attackDelay;
                    PlayerComponent player = other.getComponent(PlayerComponent.class);
                    PositionComponent playerPosition = other.getComponent(PositionComponent.class);
                    VelocityComponent playerVelocity = other.getComponent(VelocityComponent.class);
                    float dist = Util.pointDistance(position.x, position.y, playerPosition.x, playerPosition.y);
                    playerVelocity.ex = (playerPosition.x - position.x) / dist * 7;
                    playerVelocity.ey = (playerPosition.y - position.y) / dist * 7;

                    player.damage(enemyComponent.attackDamage, other);
                }
            }
        };

        return enemy;
    }
}
