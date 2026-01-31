package net.ggj2026.twofourone.ecs.entities;

import net.ggj2026.twofourone.ecs.components.*;
import net.ggj2026.twofourone.level.Level;
import net.ggj2026.twofourone.sprites.SpriteAssets;

public class Particle {
    public static Entity instance(Level level) {
        Entity particle = new Entity(level)
            .addComponent(new ParticleComponent())
            .addComponent(new PositionComponent())
            .addComponent(new VelocityComponent())
            .addComponent(new SpriteComponent())
            .addComponent(new LevelCollisionComponent());

        SpriteComponent spriteComponent = particle.getComponent(SpriteComponent.class);
        spriteComponent.addSprite(SpriteAssets.smokeParticleSprite);

        return particle;
    }
}
