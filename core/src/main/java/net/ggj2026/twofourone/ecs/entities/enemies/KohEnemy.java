package net.ggj2026.twofourone.ecs.entities.enemies;

import net.ggj2026.twofourone.ecs.components.*;
import net.ggj2026.twofourone.ecs.entities.Entity;
import net.ggj2026.twofourone.ecs.entities.Particle;
import net.ggj2026.twofourone.level.Level;
import net.ggj2026.twofourone.sprites.EntityZ;
import net.ggj2026.twofourone.sprites.SpriteAssets;

public class KohEnemy {
    public static Entity instance(Level level) {
        Entity enemy = new Entity(level)
            .addComponent(new EnemyComponent())
            .addComponent(new KohEnemyComponent())
            .addComponent(new PositionComponent())
            .addComponent(new VelocityComponent())
            .addComponent(new SpriteComponent())
            .addComponent(new LevelCollisionComponent())
            .addComponent(new EntityCollisionsComponent());
        enemy.z = EntityZ.ENEMIES;

        PositionComponent positionComponent = enemy.getComponent(PositionComponent.class);
        EnemyComponent enemyComponent = enemy.getComponent(EnemyComponent.class);
        SpriteComponent spriteComponent = enemy.getComponent(SpriteComponent.class);
        EntityCollisionsComponent entityCollisionsComponent = enemy.getComponent(EntityCollisionsComponent.class);

        spriteComponent.addSprite(SpriteAssets.kohSprite);
        spriteComponent.states.get(0).scale = 1.5f;
        spriteComponent.states.get(0).height = 4;
        spriteComponent.states.get(0).y = 2;
        spriteComponent.states.get(0).animated = true;
        spriteComponent.states.get(0).animationDelay = 1 / 4f / 2f;

        enemyComponent.health = 2000;
        enemyComponent.speed = 2.5f;
        enemyComponent.onDeath = (entity) -> {
            // Death particles
            Particle.smokeExplosion(entity.level, positionComponent, 2, false);
        };

        entityCollisionsComponent.mass = 20;

        return enemy;
    }
}
