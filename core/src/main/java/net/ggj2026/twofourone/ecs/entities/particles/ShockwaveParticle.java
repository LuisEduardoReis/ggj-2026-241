package net.ggj2026.twofourone.ecs.entities.particles;

import com.badlogic.gdx.math.Vector2;
import net.ggj2026.twofourone.ecs.components.EvaCrossComponent;
import net.ggj2026.twofourone.ecs.components.ParticleComponent;
import net.ggj2026.twofourone.ecs.components.PositionComponent;
import net.ggj2026.twofourone.ecs.components.SpriteComponent;
import net.ggj2026.twofourone.ecs.entities.Entity;
import net.ggj2026.twofourone.level.Level;
import net.ggj2026.twofourone.sprites.EntityZ;
import net.ggj2026.twofourone.sprites.SpriteAssets;

public class ShockwaveParticle {
    public static Entity instance(Level level) {
        Entity particle = new Entity(level)
            .addComponent(new ParticleComponent())
            .addComponent(new PositionComponent())
            .addComponent(new SpriteComponent());
        particle.z = EntityZ.PARTICLES;

        SpriteComponent spriteComponent = particle.getComponent(SpriteComponent.class);
        spriteComponent.addSprite(SpriteAssets.ringSprite);
        spriteComponent.states.get(0).width = 1f;
        spriteComponent.states.get(0).height = 0.5f;
        spriteComponent.states.get(0).scale = 0;
        spriteComponent.states.get(0).scaleV = 10;

        ParticleComponent particleComponent = particle.getComponent(ParticleComponent.class);
        particleComponent.lifetime = 2;
        particleComponent.fadeout = true;

        return particle;
    }
}
