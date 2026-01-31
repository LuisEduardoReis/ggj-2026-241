package net.ggj2026.twofourone.ecs.systems;

import net.ggj2026.twofourone.ecs.components.ParticleComponent;
import net.ggj2026.twofourone.ecs.entities.Entity;

import java.util.Collections;

public class ParticleSystem extends AbstractSystem {

    protected ParticleSystem() {
        super(Collections.singletonList(ParticleComponent.class));
    }

    @Override
    protected void processUpdate(Entity entity, float delta) {
        ParticleComponent particle = entity.getComponent(ParticleComponent.class);

        particle.t += delta;
        if (particle.t > particle.lifetime) {
            entity.remove = true;
        }
    }
}
