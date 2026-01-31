package net.ggj2026.twofourone.ecs.systems;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import net.ggj2026.twofourone.Util;
import net.ggj2026.twofourone.ecs.components.*;
import net.ggj2026.twofourone.ecs.entities.Entity;
import net.ggj2026.twofourone.ecs.entities.MaskPickup;
import net.ggj2026.twofourone.ecs.entities.Particle;
import net.ggj2026.twofourone.level.PathfindingMap;

import java.util.Collections;

public class EnemySystem extends AbstractSystem {

    protected EnemySystem() {
        super(Collections.singletonList(EnemyComponent.class));
    }

    @Override
    protected void processUpdate(Entity entity, float delta) {
        EnemyComponent enemy = entity.getComponent(EnemyComponent.class);
        PositionComponent position = entity.getComponent(PositionComponent.class);
        PathfindingMap pathfindingMap = entity.level.pathfindingMap;

        // Health
        long playerCount = entity.level.entities.stream()
            .filter(e -> e.hasComponent(PlayerComponent.class))
            .count();
        if (enemy.health == 0 || playerCount == 0) {
            this.die(entity, playerCount > 0);
        }

        // Attack
        enemy.attackTimer = Util.stepTo(enemy.attackTimer, 0, delta);

        // Pathfinding
        Vector2 target = pathfindingMap.getNextPositionFrom(position.px, position.py);
        if (target != null) {
            float distToTarget = Util.pointDistance(position.x, position.y, target.x, target.y);
            if (distToTarget < 2) {
                enemy.targetPosition = target;
            }
            if (distToTarget > 0) {
                position.x += (target.x - position.x) / distToTarget * 2.5f * delta;
                position.y += (target.y - position.y) / distToTarget * 2.5f * delta;
            }
        }
    }

    private void die(Entity entity, boolean dropMask) {
        entity.remove = true;
        PositionComponent enemyPosition = entity.getComponent(PositionComponent.class);
        EnemyComponent enemy = entity.getComponent(EnemyComponent.class);

        // Drop mask
        if (dropMask && enemy.maskType != null) {
            Entity maskPickup = MaskPickup.instance(entity.level, enemy.maskType);
            entity.level.addEntity(maskPickup);

            maskPickup.getComponent(MaskPickupComponent.class).type = enemy.maskType;

            PositionComponent maskPickupPosition = maskPickup.getComponent(PositionComponent.class);
            maskPickupPosition.x = enemyPosition.x;
            maskPickupPosition.y = enemyPosition.y;
        }

        // Death particles
        Particle.smokeExplosion(entity.level, enemyPosition, 1, true);

    }

    @Override
    protected void processShapeRenderer(Entity entity, ShapeRenderer shapeRenderer) {
        /*EnemyComponent enemy = entity.getComponent(EnemyComponent.class);
        PositionComponent position = entity.getComponent(PositionComponent.class);

        if (enemy.targetPosition != null) {
            shapeRenderer.line(position.x, position.y, enemy.targetPosition.x, enemy.targetPosition.y);
        }*/
    }
}
