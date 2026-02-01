package net.ggj2026.twofourone.ecs.systems;

import com.badlogic.gdx.math.Vector2;
import net.ggj2026.twofourone.Assets;
import net.ggj2026.twofourone.GameScreen;
import net.ggj2026.twofourone.Util;
import net.ggj2026.twofourone.ecs.components.*;
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
        VelocityComponent velocity = entity.getComponent(VelocityComponent.class);

        if (enemyComponent.attackTimer == 0) {
            Assets.damselFire.play();
            enemyComponent.attackTimer = enemyComponent.attackDelay;
            float dir = Util.randomRange(0, (float) (2 * Math.PI));
            Entity bullet = Bullet.spawnBullet(entity.level, new Vector2(positionComponent.x, positionComponent.y), dir, BulletType.ENEMY);
            bullet.getComponent(LevelCollisionComponent.class).collidesWithLevel = false;
        }

        if (positionComponent.x > entity.level.width){
            positionComponent.x = entity.level.width;
            velocity.ex = 0;
            velocity.ey = 0;
            velocity.vx = -velocity.vx;
        }
        if (positionComponent.y > entity.level.height){
            positionComponent.y = entity.level.height;
            velocity.ex = 0;
            velocity.ey = 0;
            velocity.vy = -velocity.vy;
        }
        if (positionComponent.x < 0){
            positionComponent.x = 0;
            velocity.ex = 0;
            velocity.ey = 0;
            velocity.vx = -velocity.vx;
        }
        if (positionComponent.y < 0){
            positionComponent.y = 0;
            velocity.ex = 0;
            velocity.ey = 0;
            velocity.vy = -velocity.vy;
        }
    }
}
