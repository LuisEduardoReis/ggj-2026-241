package net.ggj2026.twofourone.ecs.systems;

import net.ggj2026.twofourone.ecs.components.PositionComponent;
import net.ggj2026.twofourone.ecs.components.VelocityComponent;
import net.ggj2026.twofourone.ecs.entities.Entity;

import java.util.Arrays;

public class VelocitySystem extends AbstractSystem {
    public VelocitySystem() {
        super(Arrays.asList(VelocityComponent.class, PositionComponent.class));
    }

    @Override
    protected void processUpdate(Entity entity, float delta) {
        PositionComponent position = entity.getComponent(PositionComponent.class);
        VelocityComponent velocity = entity.getComponent(VelocityComponent.class);

        velocity.ex *= velocity.ef;
        velocity.ey *= velocity.ef;
        position.x += (velocity.vx + velocity.ex) * delta;
        position.y += (velocity.vy + velocity.ey) * delta;
    }
}
