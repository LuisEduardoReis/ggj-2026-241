package net.ggj2026.twofourone.ecs.systems;

import com.badlogic.gdx.math.Vector2;
import net.ggj2026.twofourone.Assets;
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
        DamselEnemyComponent damsel = entity.getComponent(DamselEnemyComponent.class);

        damsel.stateTimer = Util.stepTo(damsel.stateTimer, 0, delta);
        switch (damsel.state) {
            case IDLE:
                if (damsel.stateTimer == 0) {
                    if (Math.random() < 0.5) {
                        damsel.state = DamselEnemyComponent.State.SPIRAL;
                        damsel.stateTimer = 5;
                    } else {
                        damsel.state = DamselEnemyComponent.State.BURST;
                    }
                }
                break;
            case SPIRAL:
                damsel.fireDirection += damsel.fireRotationSpeed * delta;
                if (enemyComponent.attackTimer == 0) {
                    Assets.damselFire.play();
                    enemyComponent.attackTimer = enemyComponent.attackDelay;
                    Entity bullet = Bullet.spawnBullet(entity.level, positionComponent.toVector2(), damsel.fireDirection, BulletType.ENEMY);
                    bullet.getComponent(LevelCollisionComponent.class).collidesWithLevel = false;
                }
                if (damsel.stateTimer == 0) {
                    damsel.state = DamselEnemyComponent.State.IDLE;
                    damsel.stateTimer = 3;
                }
                break;
            case BURST:
                int numBullets = 20;
                for (int i = 0; i < numBullets; i++) {
                    Entity bullet = Bullet.spawnBullet(entity.level, positionComponent.toVector2(), (float) (i * 2*Math.PI/numBullets), BulletType.ENEMY);
                    bullet.getComponent(LevelCollisionComponent.class).collidesWithLevel = false;
                }
                damsel.state = DamselEnemyComponent.State.IDLE;
                damsel.stateTimer = 3;
                break;
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
