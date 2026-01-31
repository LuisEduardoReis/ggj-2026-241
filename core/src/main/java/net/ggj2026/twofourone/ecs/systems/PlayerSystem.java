package net.ggj2026.twofourone.ecs.systems;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import net.ggj2026.twofourone.Assets;
import net.ggj2026.twofourone.Util;
import net.ggj2026.twofourone.controllers.GameController;
import net.ggj2026.twofourone.ecs.components.*;
import net.ggj2026.twofourone.ecs.entities.Entity;
import net.ggj2026.twofourone.ecs.entities.Bullet;
import net.ggj2026.twofourone.ecs.entities.Particle;
import net.ggj2026.twofourone.gamelogic.BulletType;
import net.ggj2026.twofourone.gamelogic.MaskType;

import java.util.Collections;

import static net.ggj2026.twofourone.Util.DEG_TO_RAD;

public class PlayerSystem extends AbstractSystem {
    protected PlayerSystem() {
        super(Collections.singletonList(PlayerComponent.class));
    }

    @Override
    public void processUpdate(Entity entity, float delta) {
        PlayerComponent player = entity.getComponent(PlayerComponent.class);
        SpriteComponent sprite = entity.getComponent(SpriteComponent.class);
        PositionComponent position = entity.getComponent(PositionComponent.class);
        LevelCollisionComponent levelCollisionComponent = entity.getComponent(LevelCollisionComponent.class);
        GameController controller = player.controller;

        // Movement
        float lax = controller.getMoveAxisX(), lay = controller.getMoveAxisY();
        float deadzone = 0.25f;
        boolean moving = false;

        if(Math.abs(lay) > deadzone) {
            moving = true;
            position.y -= player.speed * delta * lay;
        }
        if(Math.abs(lax) > deadzone) {
            moving = true;
            sprite.states.get(0).mirrorX = lax > 0;
            position.x += player.speed * delta * lax;
        }
        if (moving) {
            sprite.states.get(0).animated = true;
            sprite.states.get(0).state = "walking";
            sprite.states.get(0).animationDelay = 1f / 5 / 3; // 1 second / 5 frames per second / 3 frames
        } else {
            sprite.states.get(0).animated = false;
            sprite.states.get(0).state = "standing";
        }

        // Shooty shooty
        player.bulletTimer = Util.stepTo(player.bulletTimer, 0, delta);
        player.lightingTarget = null;
        Vector2 bulletSpawnPoint = new Vector2(position.x, position.y);
        if (controller.getShootingDown()) {
            float dir = controller.getLookDir(position.x, position.y, entity.level.gameScreen.camera);

            if (MaskType.ONI.equals(player.currentMask)) {
                if (player.bulletTimer == 0) {
                    Assets.oniFire.play();
                    player.bulletTimer = player.bulletDelay / 2;
                    Bullet.spawnBullet(entity.level, bulletSpawnPoint, dir, BulletType.HIGH_DAMAGE);
                }
            } else if (MaskType.LILITH.equals(player.currentMask)){
                float minDistance = Float.MAX_VALUE;
                for (Entity enemy : entity.level.entities) {
                    if (!enemy.hasComponent(EnemyComponent.class)) continue;
                    PositionComponent enemyPos = enemy.getComponent(PositionComponent.class);
                    float dist = Util.pointDistance(position.x, position.y, enemyPos.x, enemyPos.y);
                    if (dist < minDistance) {
                        player.lightingTarget = enemy;
                        minDistance = dist;
                    }
                }
                if (player.lightingTarget != null) {
                    EnemyComponent enemy = player.lightingTarget.getComponent(EnemyComponent.class);
                    enemy.health = Util.stepTo(enemy.health, 0, player.lightningDamage * delta);
                }
            } else if (MaskType.SAN.equals(player.currentMask)){
                if (player.bulletTimer == 0) {
                    Assets.beamSound1.play();
                    Assets.beamSound1.play();
                    Assets.beamSound1.play();
                    player.bulletTimer = player.bulletDelay;
                    Bullet.spawnBullet(entity.level, bulletSpawnPoint, dir, BulletType.TRIPLE);
                    Bullet.spawnBullet(entity.level, bulletSpawnPoint, dir - 20 * DEG_TO_RAD, BulletType.TRIPLE);
                    Bullet.spawnBullet(entity.level, bulletSpawnPoint, dir + 20 * DEG_TO_RAD, BulletType.TRIPLE);
                }
            } else {
                if (player.bulletTimer == 0) {
                    Assets.beamSound1.play();
                    player.bulletTimer = player.bulletDelay;
                    Bullet.spawnBullet(entity.level, bulletSpawnPoint, dir, BulletType.NORMAL);
                }
            }
        }

        // Mask logic
        if (player.currentMask != null) {
            sprite.states.get(1).visible = true;

            player.maskTimer = Util.stepTo(player.maskTimer, 0, delta);
            if (player.maskTimer == 0) {
                player.currentMask = null;
            }
        } else {
            sprite.states.get(1).visible = false;
        }

        // Health
        if (player.health == 0) {
            entity.remove = true;
            Particle.smokeExplosion(entity.level, position, 3, false);
        } else {
            player.health = Util.stepTo(player.health, player.maxHealth, player.healthRegen * delta);
        }
    }

    @Override
    protected void processShapeRenderer(Entity entity, ShapeRenderer shapeRenderer) {
        PlayerComponent player = entity.getComponent(PlayerComponent.class);
        PositionComponent position = entity.getComponent(PositionComponent.class);

        // Health bar
        float healthBarWidth = player.health / player.maxHealth;
        if (player.health > 25 || entity.level.t % 0.5 < 0.4) {
            shapeRenderer.setColor(1f, 0, 0, 1);
            shapeRenderer.rect(position.x - 0.5f, position.y + 1.2f, healthBarWidth, 0.1f);
        }

        // Mask timer bar
        if (player.currentMask != null) {
            float maskTimerWidth = player.maskTimer / player.maskDelay;
            shapeRenderer.setColor(0.2f, 0.2f, 1, 1);
            shapeRenderer.rect(position.x - 0.5f, position.y + 1.35f, maskTimerWidth, 0.1f);
        }

        // Lightning attack
        if (player.lightingTarget != null) {
            PositionComponent targetPos = player.lightingTarget.getComponent(PositionComponent.class);
            float lx = position.x;
            float ly = position.y;
            float dist = Util.pointDistance(position.x, position.y, targetPos.x, targetPos.y);
            float nx = (targetPos.x - position.x) / dist;
            float ny = (targetPos.y - position.y) / dist;
            int steps = 10;

            for (int i = 0; i < steps; i++) {
                float ax = position.x + (i + 1) * nx * dist / steps + Util.randomRange(-0.5f, 0.5f) * ny;
                float ay = position.y + (i + 1) * ny * dist / steps + Util.randomRange(-0.5f, 0.5f) * nx;
                shapeRenderer.setColor(0.5f, 0.5f, 1f, 1f);
                shapeRenderer.rectLine(lx, ly, ax, ay, 0.15f);
                shapeRenderer.setColor(1f, 1f, 1f, 1f);
                shapeRenderer.rectLine(lx, ly, ax, ay, 0.05f);
                lx = ax;
                ly = ay;
            }
        }
    }
}
