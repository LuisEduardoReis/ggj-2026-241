package net.ggj2026.twofourone.ecs.systems;

import net.ggj2026.twofourone.Util;
import net.ggj2026.twofourone.controllers.GameController;
import net.ggj2026.twofourone.ecs.components.*;
import net.ggj2026.twofourone.ecs.entities.Entity;
import net.ggj2026.twofourone.ecs.entities.Bullet;

import java.util.Collections;

public class PlayerSystem extends AbstractSystem {
    protected PlayerSystem() {
        super(Collections.singletonList(PlayerComponent.class));
    }

    @Override
    public void processUpdate(Entity entity, float delta) {
        PlayerComponent player = entity.getComponent(PlayerComponent.class);
        PositionComponent position = entity.getComponent(PositionComponent.class);
        GameController controller = player.controller;

        float lax = controller.getMoveAxisX(), lay = controller.getMoveAxisY();
        float deadzone = 0.25f;

        if(Math.abs(lay) > deadzone) position.y -= player.speed * delta * lay;
        if(Math.abs(lax) > deadzone) position.x += player.speed * delta * lax;

        player.bulletTimer = Util.stepTo(player.bulletTimer, 0, delta);
        if (controller.getShootingDown() && player.bulletTimer == 0) {
            player.bulletTimer = player.bulletDelay;
            Entity bullet = Bullet.instance(entity.level);

            float lookDir = controller.getLookDir(position.x, position.y, entity.level.gameScreen.camera);
            float bulletSpeed = bullet.getComponent(BulletComponent.class).speed;
            bullet.getComponent(PositionComponent.class).set(position.x, position.y);
            bullet.getComponent(VelocityComponent.class).set(
                (float) (bulletSpeed * Math.cos(lookDir)),
                (float) (bulletSpeed * Math.sin(lookDir))
            );
            entity.level.addEntity(bullet);
        }
    }
}
