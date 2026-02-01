package net.ggj2026.twofourone.ecs.systems;

import net.ggj2026.twofourone.Util;
import net.ggj2026.twofourone.ecs.components.*;
import net.ggj2026.twofourone.ecs.entities.Entity;
import net.ggj2026.twofourone.level.Level;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;


public class EntityCollisionsSystem extends AbstractSystem {
    protected EntityCollisionsSystem() {
        super(Arrays.asList(PositionComponent.class, EntityCollisionsComponent.class));
    }

    Collection<Class<? extends Component>> requiredCollisionComponents = Collections.singletonList(EntityCollisionsComponent.class);
    @Override
    protected void processUpdate(Entity entity, float delta) {
        PositionComponent pos = entity.getComponent(PositionComponent.class);
        EntityCollisionsComponent collisionComponent = entity.getComponent(EntityCollisionsComponent.class);

        Level level = entity.level;

        if (!collisionComponent.collidesWithOthers) return;

        for (Entity other : level.entities) {
            if (other == entity) continue;
            if (!other.hasComponents(requiredCollisionComponents)) continue;

            EntityCollisionsComponent otherCollisionComponent = other.getComponent(EntityCollisionsComponent.class);
            if (!otherCollisionComponent.collidesWithOthers) continue;

            PositionComponent opos = other.getComponent(PositionComponent.class);

            float dist = Util.pointDistance(pos.x, pos.y, opos.x, opos.y);
            if (dist < (collisionComponent.radius + otherCollisionComponent.radius) && dist > 0) {
                collisionComponent.handleEntityCollision.accept(entity, other);
                if (collisionComponent.pushesOthers && otherCollisionComponent.pushesOthers) {
                    float dx = (pos.x - opos.x) / dist;
                    float dy = (pos.y - opos.y) / dist;
                    opos.x -= dx * (collisionComponent.radius + otherCollisionComponent.radius - dist) * 0.5f;
                    opos.y -= dy * (collisionComponent.radius + otherCollisionComponent.radius - dist) * 0.5f;
                }
            }
        }

    }
}
