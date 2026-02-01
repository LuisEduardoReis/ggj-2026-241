package net.ggj2026.twofourone.ecs.entities;

import net.ggj2026.twofourone.Util;
import net.ggj2026.twofourone.ecs.components.*;
import net.ggj2026.twofourone.level.Level;
import net.ggj2026.twofourone.sprites.EntityZ;
import net.ggj2026.twofourone.sprites.SpriteAssets;

public class Sludge {
    public static Entity instance(Level level) {
        Entity bullet = new Entity(level)
                .addComponent(new PositionComponent())
                .addComponent(new SpriteComponent())
                .addComponent(new ParticleComponent())
                .addComponent(new EntityCollisionsComponent());

        bullet.z = EntityZ.GROUND;
        SpriteComponent spriteComponent = bullet.getComponent(SpriteComponent.class);
        spriteComponent.addSprite(SpriteAssets.sludgeSprite);
        spriteComponent.states.get(0).scale = Util.randomRange(1.5f, 2f);

        EntityCollisionsComponent entityCollisionsComponent = bullet.getComponent(EntityCollisionsComponent.class);
        entityCollisionsComponent.radius = 0.2f;
        entityCollisionsComponent.pushesOthers = false;
        entityCollisionsComponent.handleEntityCollision = (me, other) -> {
            if (other.hasComponent(PlayerComponent.class)) {
                PlayerComponent player = other.getComponent(PlayerComponent.class);
                player.sludgedTimer = 0.05f;
            }
        };

        ParticleComponent particleComponent = bullet.getComponent(ParticleComponent.class);
        particleComponent.lifetime = 10 + Util.randomRange(0,2);
        particleComponent.fadeout = true;
        particleComponent.fadeoutDelay = 1;

        return bullet;
    }
}
