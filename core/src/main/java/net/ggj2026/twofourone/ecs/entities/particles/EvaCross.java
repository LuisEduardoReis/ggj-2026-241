package net.ggj2026.twofourone.ecs.entities.particles;

import com.badlogic.gdx.math.Vector2;
import net.ggj2026.twofourone.Assets;
import net.ggj2026.twofourone.ecs.components.*;
import net.ggj2026.twofourone.ecs.entities.Entity;import net.ggj2026.twofourone.level.Level;
import net.ggj2026.twofourone.sprites.EntityZ;
import net.ggj2026.twofourone.sprites.SpriteAssets;

public class EvaCross {
    public static Entity instance(Level level) {
        Entity particle = new Entity(level)
            .addComponent(new ParticleComponent())
            .addComponent(new EvaCrossComponent())
            .addComponent(new PositionComponent())
            .addComponent(new SpriteComponent());
        particle.z = EntityZ.PARTICLES;

        SpriteComponent spriteComponent = particle.getComponent(SpriteComponent.class);
        spriteComponent.addSprite(SpriteAssets.evaCrossSprite);
        spriteComponent.states.get(0).state = "default";
        spriteComponent.states.get(0).animated = true;
        spriteComponent.states.get(0).animationLoop = false;
        spriteComponent.states.get(0).animationDelay = 1 / 16f;
        spriteComponent.states.get(0).width = 3;
        spriteComponent.states.get(0).height = 6;
        spriteComponent.states.get(0).y = 3;

        ParticleComponent particleComponent = particle.getComponent(ParticleComponent.class);
        particleComponent.lifetime = 2;
        particleComponent.fadeout = true;
        Assets.evaCross.play();

        return particle;
    }

    public static void spawnEvaCross(Level level, Vector2 target) {
        for (int i = 0; i < 3; i++) {
            Entity shockwave = ShockwaveParticle.instance(level);
            level.addEntity(shockwave);
            PositionComponent shockwavePosition = shockwave.getComponent(PositionComponent.class);
            shockwavePosition.set(target.x, target.y);
            SpriteComponent shockwaveSprite = shockwave.getComponent(SpriteComponent.class);
            shockwaveSprite.states.get(0).scaleV = 2.5f + i * 2.5f;
        }

        Entity evaCross = EvaCross.instance(level);
        level.addEntity(evaCross);
        PositionComponent evaCrossPosition = evaCross.getComponent(PositionComponent.class);
        evaCrossPosition.set(target.x, target.y);
    }
}
