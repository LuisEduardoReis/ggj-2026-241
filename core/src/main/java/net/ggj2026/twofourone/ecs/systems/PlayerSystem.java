package net.ggj2026.twofourone.ecs.systems;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import net.ggj2026.twofourone.Util;
import net.ggj2026.twofourone.controllers.GameController;
import net.ggj2026.twofourone.ecs.components.*;
import net.ggj2026.twofourone.ecs.entities.Entity;
import net.ggj2026.twofourone.ecs.entities.Bullet;
import net.ggj2026.twofourone.gamelogic.BulletType;
import net.ggj2026.twofourone.gamelogic.MaskType;

import java.util.Collections;
import java.util.Objects;

public class PlayerSystem extends AbstractSystem {
    protected PlayerSystem() {
        super(Collections.singletonList(PlayerComponent.class));
    }

    @Override
    public void processUpdate(Entity entity, float delta) {
        PlayerComponent player = entity.getComponent(PlayerComponent.class);
        SpriteComponent sprite = entity.getComponent(SpriteComponent.class);
        PositionComponent position = entity.getComponent(PositionComponent.class);
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
        if (controller.getShootingDown()) {
            float dir = controller.getLookDir(position.x, position.y, entity.level.gameScreen.camera);

            if (MaskType.ONI.equals(player.currentMask)) {
                if (player.bulletTimer == 0) {
                    player.bulletTimer = player.bulletDelay;
                    Bullet.spawnBullet(entity.level, position, dir, BulletType.HIGH_DAMAGE);
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
            } else {
                if (player.bulletTimer == 0) {
                    player.bulletTimer = player.bulletDelay;
                    Bullet.spawnBullet(entity.level, position, dir, BulletType.NORMAL);
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
    }

    @Override
    protected void processShapeRenderer(Entity entity, ShapeRenderer shapeRenderer) {
        PlayerComponent player = entity.getComponent(PlayerComponent.class);
        PositionComponent position = entity.getComponent(PositionComponent.class);

        if (player.currentMask != null) {
            float width = player.maskTimer / player.maskDelay;
            shapeRenderer.setColor(1, 0, 0, 1);
            shapeRenderer.rect(position.x - 0.5f, position.y + 0.6f, width, 0.1f);
        }

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
