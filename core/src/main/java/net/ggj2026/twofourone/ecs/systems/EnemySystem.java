package net.ggj2026.twofourone.ecs.systems;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import net.ggj2026.twofourone.Util;
import net.ggj2026.twofourone.ecs.components.BulletComponent;
import net.ggj2026.twofourone.ecs.components.EnemyComponent;
import net.ggj2026.twofourone.ecs.components.PositionComponent;
import net.ggj2026.twofourone.ecs.entities.Entity;
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

        Vector2 target = pathfindingMap.getNextPositionFrom(position.px, position.py);
        enemy.targetPosition = target;
        float distToTarget = Util.pointDistance(position.x, position.y, target.x, target.y);
        if (distToTarget > 0) {
            position.x += (target.x - position.x) / distToTarget * 2.5f * delta;
            position.y += (target.y - position.y) / distToTarget * 2.5f * delta;
        }
    }

    @Override
    protected void processShapeRenderer(Entity entity, ShapeRenderer shapeRenderer) {
        EnemyComponent enemy = entity.getComponent(EnemyComponent.class);
        PositionComponent position = entity.getComponent(PositionComponent.class);

        if (enemy.targetPosition != null) {
            shapeRenderer.line(position.x, position.y, enemy.targetPosition.x, enemy.targetPosition.y);
        }
    }
}
