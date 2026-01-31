package net.ggj2026.twofourone.ecs.entities.enemies;

import net.ggj2026.twofourone.Util;
import net.ggj2026.twofourone.ecs.components.*;
import net.ggj2026.twofourone.ecs.entities.Entity;
import net.ggj2026.twofourone.ecs.entities.MaskPickup;
import net.ggj2026.twofourone.ecs.entities.Particle;
import net.ggj2026.twofourone.gamelogic.MaskType;
import net.ggj2026.twofourone.level.Level;
import net.ggj2026.twofourone.sprites.EntityZ;
import net.ggj2026.twofourone.sprites.SpriteAssets;

public class KasperEnemy {
    public static Entity instance(Level level) {
        Entity enemy = new Entity(level)
            .addComponent(new EnemyComponent())
            .addComponent(new KasperEnemyComponent())
            .addComponent(new PositionComponent())
            .addComponent(new VelocityComponent())
            .addComponent(new SpriteComponent())
            .addComponent(new LevelCollisionComponent())
            .addComponent(new EntityCollisionsComponent());
        enemy.z = EntityZ.ENEMIES;

        PositionComponent position = enemy.getComponent(PositionComponent.class);
        EnemyComponent enemyComponent = enemy.getComponent(EnemyComponent.class);
        KasperEnemyComponent kasperEnemyComponent = enemy.getComponent(KasperEnemyComponent.class);
        SpriteComponent spriteComponent = enemy.getComponent(SpriteComponent.class);

        if (Math.random() < 0.25) {
            kasperEnemyComponent.maskType = MaskType.values()[Util.randomRangeInt(0, MaskType.values().length)];
        }

        spriteComponent.addSprite(SpriteAssets.kasperSprite);
        spriteComponent.states.get(0).scale = 1;
        spriteComponent.states.get(0).animated = true;
        spriteComponent.states.get(0).animationDelay = 1 / 3f / 2f;

        if (kasperEnemyComponent.maskType != null) {
            spriteComponent.addSprite(MaskType.maskSprites.get(kasperEnemyComponent.maskType));
            spriteComponent.states.get(1).x = -0.5f/16f;
            spriteComponent.states.get(1).y = 0.2f;
            spriteComponent.states.get(1).scale = 1f;
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

        enemyComponent.onDeath = (entity) -> {
            // Drop mask
            if (kasperEnemyComponent.maskType != null && entity.level.playerCount > 0) {
                Entity maskPickup = MaskPickup.instance(entity.level, kasperEnemyComponent.maskType);
                entity.level.addEntity(maskPickup);

                maskPickup.getComponent(MaskPickupComponent.class).type = kasperEnemyComponent.maskType;

                PositionComponent maskPickupPosition = maskPickup.getComponent(PositionComponent.class);
                maskPickupPosition.x = position.x;
                maskPickupPosition.y = position.y;
            }

            // Death particles
            Particle.smokeExplosion(entity.level, position, 1, false);
        };

        return enemy;
    }
}
