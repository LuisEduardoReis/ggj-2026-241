package net.ggj2026.twofourone.ecs.systems;

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

        spriteComponent.states.get(0).y = (float) (0.15 * Math.sin(2*Math.PI * entity.level.t));
    }
}
