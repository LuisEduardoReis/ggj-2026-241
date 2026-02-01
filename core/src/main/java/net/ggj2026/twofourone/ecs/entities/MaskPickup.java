package net.ggj2026.twofourone.ecs.entities;

import net.ggj2026.twofourone.Assets;
import net.ggj2026.twofourone.ecs.components.*;
import net.ggj2026.twofourone.gamelogic.MaskType;
import net.ggj2026.twofourone.level.Level;
import net.ggj2026.twofourone.sprites.EntityZ;
import net.ggj2026.twofourone.sprites.SpriteAssets;

public class MaskPickup {
    public static Entity instance(Level level, MaskType type) {
        Entity maskPickup = new Entity(level)
            .addComponent(new MaskPickupComponent())
            .addComponent(new PositionComponent())
            .addComponent(new VelocityComponent())
            .addComponent(new SpriteComponent())
            .addComponent(new EntityCollisionsComponent())
            .addComponent(new LevelCollisionComponent());
        maskPickup.z = EntityZ.PICKUPS;

        MaskPickupComponent maskPickupComponent = maskPickup.getComponent(MaskPickupComponent.class);
        maskPickupComponent.type = type;

        SpriteComponent spriteComponent = maskPickup.getComponent(SpriteComponent.class);
        spriteComponent.addSprite(SpriteAssets.shadowSprite);
        spriteComponent.states.get(0).alpha = 0.5f;
        spriteComponent.addSprite(MaskType.maskSprites.get(type));
        spriteComponent.states.get(1).scale = 1f;

        EntityCollisionsComponent entityCollisionsComponent = maskPickup.getComponent(EntityCollisionsComponent.class);
        entityCollisionsComponent.pushesOthers = false;
        entityCollisionsComponent.handleEntityCollision = (me, player) -> {
            if (player.hasComponent(PlayerComponent.class)) {
                me.remove = true;
                PlayerComponent playerComponent = player.getComponent(PlayerComponent.class);
                playerComponent.pickupMask(maskPickupComponent.type, player);
            }
        };

        return maskPickup;
    }
}
