package net.ggj2026.twofourone.ecs.systems;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import net.ggj2026.twofourone.Assets;
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
        if (enemy.health == 0 || entity.level.playerCount == 0) {
            this.die(entity);
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
                position.x += (target.x - position.x) / distToTarget * enemy.speed * delta;
                position.y += (target.y - position.y) / distToTarget * enemy.speed * delta;
            }
        }
    }

    private void die(Entity entity) {
        entity.remove = true;
        entity.getComponent(EnemyComponent.class).onDeath.accept(entity);
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
