package net.ggj2026.twofourone.ecs.entities;

import net.ggj2026.twofourone.ecs.components.*;
import net.ggj2026.twofourone.level.Level;
import net.ggj2026.twofourone.sprites.EntityZ;
import net.ggj2026.twofourone.sprites.SpriteAssets;

public class MaskPickup {
    public static Entity instance(Level level) {
        Entity maskPickup = new Entity(level)
            .addComponent(new MaskPickupComponent())
            .addComponent(new PositionComponent())
            .addComponent(new VelocityComponent())
            .addComponent(new SpriteComponent())
            .addComponent(new EntityCollisionsComponent())
            .addComponent(new LevelCollisionComponent());
        maskPickup.z = EntityZ.PICKUPS;

        SpriteComponent spriteComponent = maskPickup.getComponent(SpriteComponent.class);
        spriteComponent.addSprite(SpriteAssets.oniMaskSprite);
        spriteComponent.states.get(0).scale = 0.75f;

        EntityCollisionsComponent entityCollisionsComponent = maskPickup.getComponent(EntityCollisionsComponent.class);
        entityCollisionsComponent.pushesOthers = false;
        entityCollisionsComponent.handleEntityCollision = (me, other) -> {
            if (other.hasComponent(PlayerComponent.class)) {
                me.remove = true;
                MaskPickupComponent maskPickupComponent = me.getComponent(MaskPickupComponent.class);
                PlayerComponent player = other.getComponent(PlayerComponent.class);
                player.currentMask = maskPickupComponent.type;
                player.maskTimer = player.maskDelay;
            }
        };

        return maskPickup;
    }
}
