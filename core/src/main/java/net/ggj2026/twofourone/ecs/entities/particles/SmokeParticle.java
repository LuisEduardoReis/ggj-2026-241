package net.ggj2026.twofourone.ecs.entities.particles;

import net.ggj2026.twofourone.Util;
import net.ggj2026.twofourone.ecs.components.*;
import net.ggj2026.twofourone.ecs.entities.Entity;
import net.ggj2026.twofourone.level.Level;
import net.ggj2026.twofourone.sprites.EntityZ;
import net.ggj2026.twofourone.sprites.SpriteAssets;

public class SmokeParticle {
    public static Entity instance(Level level) {
        Entity particle = new Entity(level)
            .addComponent(new ParticleComponent())
            .addComponent(new PositionComponent())
            .addComponent(new VelocityComponent())
            .addComponent(new SpriteComponent())
            .addComponent(new LevelCollisionComponent());
        particle.z = EntityZ.PARTICLES;

        SpriteComponent spriteComponent = particle.getComponent(SpriteComponent.class);
        spriteComponent.addSprite(SpriteAssets.smokeParticleSprite);

        LevelCollisionComponent levelCollisionComponent = particle.getComponent(LevelCollisionComponent.class);
        levelCollisionComponent.collidesWithLevel = false;

        return particle;
    }

    public static void smokeExplosion(
        Level level,
        PositionComponent position,
        float scale,
        boolean collideWithLevel
    ) {
        for (int i = 0; i < 10; i++) {
            Entity particle = SmokeParticle.instance(level);
            level.addEntity(particle);

            PositionComponent particlePosition = particle.getComponent(PositionComponent.class);
            VelocityComponent particleVelocity = particle.getComponent(VelocityComponent.class);
            SpriteComponent particleSprite = particle.getComponent(SpriteComponent.class);
            LevelCollisionComponent levelCollisionComponent = particle.getComponent(LevelCollisionComponent.class);

            float dir = Util.randomRange(0, (float) (2*Math.PI));
            float force = Util.randomRange(0.5f, 3) * scale;
            particlePosition.x = position.x;
            particlePosition.y = position.y;

            particleVelocity.vx = (float) (force * Math.cos(dir));
            particleVelocity.vy = (float) (force * Math.sin(dir));
            particleSprite.states.get(0).animated = true;
            particleSprite.states.get(0).rotationDelta = (float) (4 * 2*Math.PI);
            particleSprite.states.get(0).scale = scale;
            particleSprite.states.get(0).scaleV = -scale;
            levelCollisionComponent.collidesWithLevel = collideWithLevel;
        }
    }
}
