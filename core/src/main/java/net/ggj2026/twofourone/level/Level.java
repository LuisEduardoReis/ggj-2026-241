package net.ggj2026.twofourone.level;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import net.ggj2026.twofourone.Assets;
import net.ggj2026.twofourone.GameScreen;
import net.ggj2026.twofourone.ecs.entities.Entity;
import net.ggj2026.twofourone.ecs.entities.Player;
import net.ggj2026.twofourone.ecs.systems.Systems;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static net.ggj2026.twofourone.level.TileType.EMPTY_TILE;
import static net.ggj2026.twofourone.level.TileType.getTileType;

public class Level {

    public GameScreen gameScreen;

    public List<Entity> entities;
    public List<Entity> newEntities;
    public Systems entitySystems;
    public Entity player;

    public int width, height;
    public Tile[] tiles;
    public Tile[] overlayTiles;
    public Tile boundaryTile;

    public Level(GameScreen gameScreen) {
        this.gameScreen = gameScreen;

        this.entities = new LinkedList<>();
        this.newEntities = new LinkedList<>();
        this.entitySystems = new Systems();
        this.player = this.addEntity(Player.instance(this));

        this.width = 16;
        this.height = 16;

        this.tiles = new Tile[this.width * this.height];
        this.overlayTiles = new Tile[this.width * this.height];
        for (int i = 0; i < this.width * this.height; i++) {
            this.tiles[i] = new Tile(Math.random() > 0.5 ? getTileType("empty") : getTileType("test"));
            this.overlayTiles[i] = new Tile(null);
        }
        this.boundaryTile = new Tile(getTileType("empty"));
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
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                Tile tile = this.getTile(x, y);
                if (tile.type != null && tile.type.texture != null)
                    spriteBatch.draw(tile.type.texture, x, y, 1, 1);

                Tile overlayTile = this.getTileOverlay(x, y);
                if (overlayTile.type != null && overlayTile.type.texture != null)
                    spriteBatch.draw(overlayTile.type.texture, x, y, 1, 1);
            }
        }

        this.entitySystems.renderSprites(this.entities, spriteBatch);
    }

    public void renderShapes(ShapeRenderer shapeRenderer) {
        /*for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                Tile tile = this.getTile(x,y);
                shapeRenderer.setColor(tile.type.solid ? Color.BLACK : Color.GRAY);
                shapeRenderer.rect(x, y, 1,1);
            }
        }*/

        this.entitySystems.renderShapes(this.entities, shapeRenderer);
    }

    public void renderDebug(ShapeRenderer shapeRenderer) {
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || x >= this.width || y < 0 || y >= this.height) return this.boundaryTile;
        return this.tiles[y * this.width + x];
    }

    public Tile getTile(float x, float y) {
        return getTile((int) Math.floor(x), (int) Math.floor(y));
    }

    public Tile getTileOverlay(int x, int y) {
        if (x < 0 || x >= this.width || y < 0 || y >= this.height) return this.boundaryTile;
        return this.overlayTiles[y * this.width + x];
    }

    public Tile getTileOverlay(float x, float y) {
        return getTileOverlay((int) Math.floor(x), (int) Math.floor(y));
    }

}
