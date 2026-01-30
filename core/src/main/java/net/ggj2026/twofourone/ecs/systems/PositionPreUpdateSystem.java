package net.ggj2026.twofourone.ecs.systems;

import net.ggj2026.twofourone.ecs.components.PositionComponent;
import net.ggj2026.twofourone.ecs.entities.Entity;

import java.util.Collections;

public class PositionPreUpdateSystem extends AbstractSystem {
    public PositionPreUpdateSystem() {
        super(Collections.singletonList(PositionComponent.class));
    }

    @Override
    protected void processUpdate(Entity entity, float delta) {
        PositionComponent position = entity.getComponent(PositionComponent.class);
        position.px = position.x;
        position.py = position.y;
    }
}
