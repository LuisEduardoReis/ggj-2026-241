package net.ggj2026.twofourone.ecs.systems;

import net.ggj2026.twofourone.Util;
import net.ggj2026.twofourone.ecs.components.BulletComponent;
import net.ggj2026.twofourone.ecs.components.PositionComponent;
import net.ggj2026.twofourone.ecs.components.SpriteComponent;
import net.ggj2026.twofourone.ecs.entities.Entity;
import net.ggj2026.twofourone.ecs.entities.Particle;
import net.ggj2026.twofourone.sprites.EntityZ;

import java.util.Collections;

public class BulletSystem extends AbstractSystem {

    public static float REMOVE_BORDER_RADIUS = 10;
    protected BulletSystem() {
        super(Collections.singletonList(BulletComponent.class));
    }

    @Override
    protected void processUpdate(Entity entity, float delta) {
        PositionComponent position = entity.getComponent(PositionComponent.class);
        BulletComponent bullet = entity.getComponent(BulletComponent.class);
        SpriteComponent sprite = entity.getComponent(SpriteComponent.class);

        bullet.t += delta;
        if (bullet.t > bullet.lifetime) {
            entity.remove = true;
        }

        if (!Util.isBetween(position.x, -REMOVE_BORDER_RADIUS, entity.level.width + REMOVE_BORDER_RADIUS) ||
            !Util.isBetween(position.y, -REMOVE_BORDER_RADIUS, entity.level.height + REMOVE_BORDER_RADIUS)) {
            entity.remove = true;
        }

        bullet.trailTimer = Util.stepTo(bullet.trailTimer, 0, delta);
        if (bullet.trailTimer == 0) {
            bullet.trailTimer = bullet.trailDelay;

            Entity particle = Particle.instance(entity.level);
            entity.level.addEntity(particle);

            particle.z = entity.z;
            PositionComponent particlePosition = particle.getComponent(PositionComponent.class);
            SpriteComponent particleSprite = particle.getComponent(SpriteComponent.class);
            particlePosition.x = position.x;
            particlePosition.y = position.y;
            particleSprite.sprites.set(0, sprite.sprites.get(0));
            particleSprite.states.get(0).animated = true;
            particleSprite.states.get(0).rotationDelta = (float) (4 * 2*Math.PI);
            particleSprite.states.get(0).scale = 0.5f;
            particleSprite.states.get(0).scaleV = -1f;
        }
    }
}
