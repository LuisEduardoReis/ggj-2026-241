package net.ggj2026.twofourone.ecs.systems;

import net.ggj2026.twofourone.Util;
import net.ggj2026.twofourone.ecs.components.*;
import net.ggj2026.twofourone.ecs.entities.Entity;

import java.util.Collections;

public class MaskPickupSystem extends AbstractSystem {

    protected MaskPickupSystem() {
        super(Collections.singletonList(MaskPickupComponent.class));
    }

    @Override
    protected void processUpdate(Entity entity, float delta) {
        SpriteComponent spriteComponent = entity.getComponent(SpriteComponent.class);
        MaskPickupComponent maskPickup = entity.getComponent(MaskPickupComponent.class);

        spriteComponent.states.get(1).y = 0.5f + (float) (0.15 * Math.sin(2*Math.PI * entity.level.t));

        maskPickup.ttl = Util.stepTo(maskPickup.ttl, 0, delta);
        if (maskPickup.ttl == 0) {
            entity.remove = true;
        }
        if (maskPickup.ttl < 1) {
            spriteComponent.states.get(1).alpha = maskPickup.ttl;
        }
    }
}
