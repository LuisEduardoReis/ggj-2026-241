package net.ggj2026.twofourone.ecs.systems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import net.ggj2026.twofourone.ecs.entities.Entity;

import java.util.ArrayList;
import java.util.Collection;

public class Systems {
    public Collection<AbstractSystem> systems;

    public Systems() {
        this.systems = new ArrayList<>();

        this.systems.add(new PositionPreUpdateSystem());
        this.systems.add(new PlayerSystem());
        this.systems.add(new SpriteSystem());
        this.systems.add(new BulletSystem());
        this.systems.add(new VelocitySystem());
        this.systems.add(new LevelCollisionsSystem());
    }

    public void update(Collection<Entity> entities, float delta) {
        for (AbstractSystem system : this.systems) {
            for (Entity entity : entities) {
                system.visitUpdate(entity, delta);
            }
        }
    }

    public void renderSprites(Collection<Entity> entities, SpriteBatch spriteBatch) {
        for (AbstractSystem system : this.systems) {
            for (Entity entity : entities) {
                system.visitSpriteBatch(entity, spriteBatch);
            }
        }
    }

    public void renderShapes(Collection<Entity> entities, ShapeRenderer shapeRenderer) {
        for (AbstractSystem system : this.systems) {
            for (Entity entity : entities) {
                system.visitShapeRenderer(entity, shapeRenderer);
            }
        }
    }
}
