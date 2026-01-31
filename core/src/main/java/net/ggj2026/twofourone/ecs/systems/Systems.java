package net.ggj2026.twofourone.ecs.systems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import net.ggj2026.twofourone.ecs.entities.Entity;
import net.ggj2026.twofourone.level.Level;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Systems {
    private final Collection<AbstractSystem> systems;
    private final Level level;

    public Systems(Level level) {
        this.level = level;
        this.systems = new ArrayList<>();

        this.systems.add(new PositionPreUpdateSystem());
        this.systems.add(new PlayerSystem());
        this.systems.add(new SpriteSystem());
        this.systems.add(new ParticleSystem());
        this.systems.add(new BulletSystem());
        this.systems.add(new MaskPickupSystem());
        this.systems.add(new VelocitySystem());
        this.systems.add(new PathfindingCalculationSystem());
        this.systems.add(new EnemySystem());
        this.systems.add(new EnemySpawnerSystem());
        this.systems.add(new EntityCollisionsSystem());
        this.systems.add(new LevelCollisionsSystem());
    }

    public void update(Collection<Entity> entities, float delta) {
        for (AbstractSystem system : this.systems) {
            system.update(this.level, delta);
            if (system.visitsEntities) {
                for (Entity entity : entities) {
                    system.visitUpdate(entity, delta);
                }
            }
        }
    }

    public void renderSprites(Collection<Entity> entities, SpriteBatch spriteBatch) {
        List<Entity> zSortedEntities = entities.stream()
            .sorted((a, b) -> Float.compare(a.z, b.z))
            .collect(Collectors.toList());

        for (AbstractSystem system : this.systems) {
            if (system.visitsEntities) {
                for (Entity entity : zSortedEntities) {
                    system.visitSpriteBatch(entity, spriteBatch);
                }
            }
        }
    }

    public void renderShapes(Collection<Entity> entities, ShapeRenderer shapeRenderer) {
        for (AbstractSystem system : this.systems) {
            if (system.visitsEntities) {
                for (Entity entity : entities) {
                    system.visitShapeRenderer(entity, shapeRenderer);
                }
            }
        }
    }
}
