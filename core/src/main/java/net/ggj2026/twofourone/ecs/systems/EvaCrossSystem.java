package net.ggj2026.twofourone.ecs.systems;

import net.ggj2026.twofourone.ecs.components.EvaCrossComponent;
import net.ggj2026.twofourone.ecs.components.ParticleComponent;
import net.ggj2026.twofourone.ecs.components.SpriteComponent;
import net.ggj2026.twofourone.ecs.entities.Entity;
import net.ggj2026.twofourone.sprites.SpriteAnimation;
import net.ggj2026.twofourone.sprites.SpriteState;

import java.util.Collections;

public class EvaCrossSystem extends AbstractSystem {

    protected EvaCrossSystem() {
        super(Collections.singletonList(EvaCrossComponent.class));
    }

    @Override
    protected void processUpdate(Entity entity, float delta) {
        SpriteComponent spriteComponent = entity.getComponent(SpriteComponent.class);
        SpriteState spriteState = spriteComponent.states.get(0);

        if ("default".equals(spriteState.state) && spriteState.isAnimationEnded(spriteComponent.sprites.get(0))) {
            spriteState.state = "standing";
            spriteState.animationLoop = true;
            spriteState.startAnimation();
        }
    }
}
