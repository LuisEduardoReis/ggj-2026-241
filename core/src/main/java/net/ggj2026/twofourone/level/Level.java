package net.ggj2026.twofourone.level;

import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import net.ggj2026.twofourone.Assets;
import net.ggj2026.twofourone.GameScreen;
import net.ggj2026.twofourone.controllers.GameController;
import net.ggj2026.twofourone.ecs.components.PositionComponent;
import net.ggj2026.twofourone.ecs.entities.Enemy;
import net.ggj2026.twofourone.ecs.entities.Entity;
import net.ggj2026.twofourone.ecs.entities.Player;
import net.ggj2026.twofourone.ecs.systems.Systems;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static net.ggj2026.twofourone.level.TileType.*;

public class Level {

    public GameScreen gameScreen;

    public List<Entity> entities;
    public List<Entity> newEntities;
    public Systems entitySystems;

    public int width, height;
    public Tile[] tiles;
    public Tile[] overlayTiles;
    public Tile boundaryTile;

    public PathfindingMap pathfindingMap;

    public Level(GameScreen gameScreen) {
        this.gameScreen = gameScreen;

        this.entities = new LinkedList<>();
        this.newEntities = new LinkedList<>();
        this.entitySystems = new Systems();

        TiledMap map = new TmxMapLoader(new InternalFileHandleResolver()).load("map.tmx");
        this.width = (int) map.getProperties().get("width");
        this.height = (int) map.getProperties().get("height");

        TiledMapTileLayer mapTiles = (TiledMapTileLayer) map.getLayers().get("tiles");
        TiledMapTileLayer overlayTiles = (TiledMapTileLayer) map.getLayers().get("tiles_overlay");
        MapObjects mapObjects = map.getLayers().get("objects").getObjects();

        this.tiles = new Tile[this.width * this.height];
        this.overlayTiles = new Tile[this.width * this.height];
        for (int i = 0; i < this.width * this.height; i++) {
            this.tiles[i] = new Tile(null);
            this.overlayTiles[i] = new Tile(null);
        }
        for (int y = 0; y < this.height; y ++) {
            for (int x = 0; x < this.width; x ++) {
                Tile tile = getTile(x,y);
                TiledMapTileLayer.Cell cell = mapTiles.getCell(x, y);
                if (cell != null) {
                    tile.type = getTileTypeById(cell.getTile().getId() - 1);
                } else {
                    tile.type = getTileType("empty");
                }
            }
        }

        this.boundaryTile = new Tile(getTileType("empty"));

        pathfindingMap = new PathfindingMap(this);

        Entity enemy = this.addEntity(Enemy.instance(this));
        enemy.getComponent(PositionComponent.class).set(this.width/2f+1, this.height/2f+1);
    }

    public void createPlayer(GameController controller) {
        Entity player = Player.instance(this, controller);
        this.addEntity(player);
        player.getComponent(PositionComponent.class).set(this.width/2f, this.height/2f);
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
