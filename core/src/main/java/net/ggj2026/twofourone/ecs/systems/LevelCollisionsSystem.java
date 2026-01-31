package net.ggj2026.twofourone.ecs.systems;

import net.ggj2026.twofourone.ecs.components.LevelCollisionComponent;
import net.ggj2026.twofourone.ecs.components.PositionComponent;
import net.ggj2026.twofourone.ecs.components.VelocityComponent;
import net.ggj2026.twofourone.ecs.entities.Entity;
import net.ggj2026.twofourone.level.Level;

import java.util.Arrays;


public class LevelCollisionsSystem extends AbstractSystem {
    protected LevelCollisionsSystem() {
        super(Arrays.asList(PositionComponent.class, LevelCollisionComponent.class));
    }

    @Override
    protected void processUpdate(Entity entity, float delta) {
        PositionComponent pos = entity.getComponent(PositionComponent.class);
        VelocityComponent velocity = entity.getComponent(VelocityComponent.class);
        LevelCollisionComponent collisionComponent = entity.getComponent(LevelCollisionComponent.class);

        Level level = entity.level;

        if (!collisionComponent.collidesWithLevel) return;

        int xc = (int) Math.floor(pos.x);
        int yc = (int) Math.floor(pos.y);
        float xr = pos.x - xc;
        float yr = pos.y - yc;

        if (collisionComponent.isTileSolid.apply(level.getTile(xc, yc))) {
            pos.x = pos.px;
            pos.y = pos.py;
            collisionComponent.handleLevelCollision.accept(0f, 0f);
        }

        if (collisionComponent.isTileSolid.apply(level.getTile(xc - 1, yc)) && xr < collisionComponent.radius) {
            xr = collisionComponent.radius;
            //entity.vx = 0;
            pos.x = xc + xr;

            collisionComponent.handleLevelCollision.accept(-1f, 0f);

            if (collisionComponent.bounce && velocity != null){
                velocity.vx = -velocity.vx;
            }
        }
        if (collisionComponent.isTileSolid.apply(level.getTile(xc + 1, yc)) && xr > 1 - collisionComponent.radius) {
            xr = 1 - collisionComponent.radius;
            //entity.vx = 0;
            pos.x = xc + xr;

            collisionComponent.handleLevelCollision.accept(+1f, 0f);

            if (collisionComponent.bounce && velocity != null){
                velocity.vx = -velocity.vx;
            }
        }

        if (collisionComponent.isTileSolid.apply(level.getTile(xc, yc - 1)) && yr < collisionComponent.radius) {
            yr = collisionComponent.radius;
            //entity.vy = 0;
            pos.y = yc + yr;

            collisionComponent.handleLevelCollision.accept(0f, -1f);

            if (collisionComponent.bounce && velocity != null){
                velocity.vy = -velocity.vy;
            }
        }
        if (collisionComponent.isTileSolid.apply(level.getTile(xc, yc + 1)) && yr > 1 - collisionComponent.radius) {
            yr = 1 - collisionComponent.radius;
            //entity.vy = 0;
            pos.y = yc + yr;

            collisionComponent.handleLevelCollision.accept(0f, 1f);

            if (collisionComponent.bounce && velocity != null){
                velocity.vy = -velocity.vy;
            }
        }
    }
}
