package net.ggj2026.twofourone.ecs.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import net.ggj2026.twofourone.Assets;
import net.ggj2026.twofourone.Util;
import net.ggj2026.twofourone.controllers.GameController;
import net.ggj2026.twofourone.ecs.components.*;
import net.ggj2026.twofourone.ecs.entities.Entity;
import net.ggj2026.twofourone.ecs.entities.Bullet;
import net.ggj2026.twofourone.ecs.entities.particles.EvaCross;
import net.ggj2026.twofourone.ecs.entities.particles.SmokeParticle;
import net.ggj2026.twofourone.effects.Lightning;
import net.ggj2026.twofourone.gamelogic.BulletType;
import net.ggj2026.twofourone.gamelogic.MaskType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

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
        Vector2 bulletSpawnPoint = new Vector2(position.x, position.y);
        if (controller.getShootingDown()) {
            float dir = controller.getLookDir(position.x, position.y, entity.level.gameScreen.viewport);

            if (MaskType.ONI.equals(player.currentMask)) {
                if (player.bulletTimer == 0) {
                    Assets.oniFire.play();
                    player.bulletTimer = player.bulletDelay / 2;
                    Bullet.spawnBullet(entity.level, bulletSpawnPoint, dir, BulletType.HIGH_DAMAGE);
                }
            } else if (MaskType.TORU.equals(player.currentMask)){
                player.lightningTargets.clear();
                this.getLightningTargets(player.lightningTargets, new ArrayList<>(), entity,3, player.lightningRange);
                if (!player.lightningTargets.isEmpty()) {
                    for (Entity target : player.lightningTargets){
                        EnemyComponent enemy = target.getComponent(EnemyComponent.class);
                        enemy.damage(player.lightningDamage * delta);
                    }
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
            } else if (MaskType.LILITH.equals(player.currentMask)) {
                if (player.bulletTimer == 0) {
                    player.bulletTimer = player.bulletDelay * 2;

                    EvaCross.spawnEvaCross(entity.level, position.toVector2());
                    entity.level.gameScreen.cameraShake = 50;

                    for (Entity enemy : entity.level.entities) {
                        if (!enemy.hasComponent(EnemyComponent.class)) continue;
                        EnemyComponent enemyComponent = enemy.getComponent(EnemyComponent.class);
                        PositionComponent enemyPos = enemy.getComponent(PositionComponent.class);
                        VelocityComponent enemyVelocity = enemy.getComponent(VelocityComponent.class);
                        float dist = Util.pointDistance(position.x, position.y, enemyPos.x, enemyPos.y);
                        if (dist > 0 && dist < player.explosionRange) {
                            float effect = (1 - dist / player.explosionRange);
                            enemyVelocity.ex = (enemyPos.x - position.x) / dist * player.explosionForce * effect;
                            enemyVelocity.ey = (enemyPos.y - position.y) / dist * player.explosionForce * effect;
                            enemyComponent.damage(player.explosionDamage * effect);
                        }
                    }
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
            SmokeParticle.smokeExplosion(entity.level, position, 3, false);
        } else {
            player.health = Util.stepTo(player.health, player.maxHealth, player.healthRegen * delta);
        }

        // Debug
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            player.pickupMask(MaskType.randomMaskType(), entity);
        }
    }

    private static final Color lightningColor = new Color(0.5f, 0.5f, 1f, 1f);

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

        drawLightning(shapeRenderer, player.lightningTargets, entity);
    }

    private void drawLightning(ShapeRenderer renderer, List<Entity> targets, Entity origin){
        if (targets.isEmpty()){
            return;
        }
        Entity target = targets.get(0);
        targets.remove(0);
        PositionComponent position = origin.getComponent(PositionComponent.class);
        Lightning.draw(renderer, position.toVector2(), target.getComponent(PositionComponent.class).toVector2(), lightningColor);
        drawLightning(renderer, targets, target);
    }

    // recursively finds lightning targets that are no further than $range apart
    // avoids keeps visited list to avoid infinite ping pong between targets
    private void getLightningTargets(ArrayList<Entity> targets, ArrayList<Entity> visited, Entity entity, int maxChain, float range) {
        if (maxChain == 0) {
            return;
        }
        PositionComponent position = entity.getComponent(PositionComponent.class);
        float minDistance = Float.MAX_VALUE;
        Entity target = null;
        for (Entity enemy : entity.level.entities) {
            if (!enemy.hasComponent(EnemyComponent.class) || visited.contains(enemy)) continue;
            PositionComponent enemyPos = enemy.getComponent(PositionComponent.class);
            float dist = Util.pointDistance(position.x, position.y, enemyPos.x, enemyPos.y);
            if (dist < minDistance && dist < range) {
                target = enemy;
                minDistance = dist;
            }
        }

        if (target != null) {
            targets.add(target);
            visited.add(target);
            getLightningTargets(targets, visited, target, maxChain - 1 , range);
        }
    }
}
