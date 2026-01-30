package net.ggj2026.twofourone.level;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import net.ggj2026.twofourone.GameScreen;
import net.ggj2026.twofourone.ecs.entities.Entity;
import net.ggj2026.twofourone.ecs.entities.Player;
import net.ggj2026.twofourone.ecs.systems.Systems;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Level {

    public GameScreen gameScreen;

    public List<Entity> entities;
    public List<Entity> newEntities;
    public Systems entitySystems;
    public Entity player;

    public int width, height;

    public Level(GameScreen gameScreen) {
        this.gameScreen = gameScreen;

        this.entities = new LinkedList<>();
        this.newEntities = new LinkedList<>();
        this.entitySystems = new Systems();
        this.player = this.addEntity(Player.instance(this));

        this.width = 16;
        this.height = 16;
    }

    public Entity addEntity(Entity entity) {
        this.newEntities.add(entity);
        return entity;
    }

    public void update(float delta) {
        this.entitySystems.update(this.entities, delta);

        this.entities.addAll(this.newEntities);
        this.newEntities.clear();

        if (this.entities.stream().anyMatch(entity -> entity.remove)) {
            this.entities = this.entities.stream()
                    .filter(entity -> !entity.remove)
                    .collect(Collectors.toList());
        }
    }

    public void renderSprites(SpriteBatch spriteBatch) {
        this.entitySystems.renderSprites(this.entities, spriteBatch);
    }

    public void renderShapes(ShapeRenderer shapeRenderer) {
        this.entitySystems.renderShapes(this.entities, shapeRenderer);
    }

    public void renderDebug(ShapeRenderer shapeRenderer) {
    }
}
