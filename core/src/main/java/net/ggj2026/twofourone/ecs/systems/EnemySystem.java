package net.ggj2026.twofourone.ecs.systems;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import net.ggj2026.twofourone.Util;
import net.ggj2026.twofourone.ecs.components.*;
import net.ggj2026.twofourone.ecs.entities.Entity;
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
        if (enemy.health == 0) {
            this.die(entity);
        }

        // Pathfinding
        Vector2 target = pathfindingMap.getNextPositionFrom(position.px, position.py);
        float distToTarget = Util.pointDistance(position.x, position.y, target.x, target.y);
        if (distToTarget < 2) {
            enemy.targetPosition = target;
        }
        if (distToTarget > 0) {
            position.x += (target.x - position.x) / distToTarget * 2.5f * delta;
            position.y += (target.y - position.y) / distToTarget * 2.5f * delta;
        }
    }

    private void die(Entity entity) {
        entity.remove = true;
        PositionComponent enemyPosition = entity.getComponent(PositionComponent.class);

        // Death particles
        for (int i = 0; i < 10; i++) {
            Entity particle = Particle.instance(entity.level);
            entity.level.addEntity(particle);

            PositionComponent particlePosition = particle.getComponent(PositionComponent.class);
            VelocityComponent particleVelocity = particle.getComponent(VelocityComponent.class);
            SpriteComponent particleSprite = particle.getComponent(SpriteComponent.class);
            float dir = Util.randomRange(0, (float) (2*Math.PI));
            float force = Util.randomRange(0.5f, 3);
            particlePosition.x = enemyPosition.x;
            particlePosition.y = enemyPosition.y;

            particleVelocity.vx = (float) (force * Math.cos(dir));
            particleVelocity.vy = (float) (force * Math.sin(dir));
            particleSprite.states.get(0).animated = true;
            particleSprite.states.get(0).rotationDelta = (float) (4 * 2*Math.PI);
            particleSprite.states.get(0).scaleV = -1f;

        }
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
