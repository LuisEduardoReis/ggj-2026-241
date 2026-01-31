package net.ggj2026.twofourone.ecs.systems;

import com.badlogic.gdx.math.Vector2;
import net.ggj2026.twofourone.Util;
import net.ggj2026.twofourone.ecs.components.DamselEnemyComponent;
import net.ggj2026.twofourone.ecs.components.EnemyComponent;
import net.ggj2026.twofourone.ecs.components.KasperEnemyComponent;
import net.ggj2026.twofourone.ecs.components.PositionComponent;
import net.ggj2026.twofourone.ecs.entities.Bullet;
import net.ggj2026.twofourone.ecs.entities.Entity;
import net.ggj2026.twofourone.gamelogic.BulletType;

import java.util.Collections;

public class DamselEnemySystem extends AbstractSystem {
    protected DamselEnemySystem() {
        super(Collections.singletonList(DamselEnemyComponent.class));
    }

    @Override
    protected void processUpdate(Entity entity, float delta) {
        EnemyComponent enemyComponent = entity.getComponent(EnemyComponent.class);
        PositionComponent positionComponent = entity.getComponent(PositionComponent.class);

        if (enemyComponent.attackTimer == 0) {
            enemyComponent.attackTimer = enemyComponent.attackDelay;
            float dir = Util.randomRange(0, (float) (2 * Math.PI));
            Bullet.spawnBullet(entity.level, new Vector2(positionComponent.x, positionComponent.y), dir, BulletType.ENEMY);
        }
    }
}
