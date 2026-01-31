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

        if(Math.abs(lay) > deadzone) position.y -= player.speed * delta * lay;
        if(Math.abs(lax) > deadzone) position.x += player.speed * delta * lax;

        // Shooty shooty
        player.bulletTimer = Util.stepTo(player.bulletTimer, 0, delta);
        if (controller.getShootingDown() && player.bulletTimer == 0) {
            player.bulletTimer = player.bulletDelay;
            float dir = controller.getLookDir(position.x, position.y, entity.level.gameScreen.camera);
            if (MaskType.ONI.equals(player.currentMask)) {
                Bullet.spawnBullet(entity.level, position, dir, BulletType.HIGH_DAMAGE);
            } else {
                Bullet.spawnBullet(entity.level, position, dir, BulletType.NORMAL);
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
            shapeRenderer.setColor(255, 0, 0, 1);
            shapeRenderer.rect(position.x - 0.5f, position.y + 0.6f, width, 0.1f);
        }
    }
}
