package net.ggj2026.twofourone.level;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

import static net.ggj2026.twofourone.Assets.getTileTextureById;
import static net.ggj2026.twofourone.Assets.textureAtlas;

public class TileType {

    public static Map<String, TileType> TILETYPES = new HashMap<>();


    static {
        TILETYPES.put("empty", new TileType().setName("empty").setSolid(false));
        TILETYPES.put("boundary", new TileType().setName("boundary").setSolid(false).setSolidToPlayer(true));
        TILETYPES.put("test", new TileType().setName("test").setSolid(true).setTexture(textureAtlas.findRegion("test")));
        for (int i = 0; i < 255; i++) {
            String id = String.format("id%d", i);
            TILETYPES.put(id, new TileType().setName(id).setTexture(getTileTextureById(i)));
        }
        TILETYPES.get("id0").solid = true;
        TILETYPES.get("id4").solid = true;
    }

    public static TileType getTileType(String name) {
        if (TILETYPES.containsKey(name)) {
            return TILETYPES.get(name);
        } else {
            return TILETYPES.get("test");
        }
    }

    public static TileType getTileTypeById(int id) {
        return getTileType(String.format("id%d", id));
    }

    public boolean solid = false;
    public boolean solidToPlayer = false;
    public String name;
    public TextureRegion texture;

    TileType() {}

    private TileType setSolid(boolean solid) {
        this.solid = solid;
        return this;
    }

    private TileType setSolidToPlayer(boolean solid) {
        this.solidToPlayer = solid;
        return this;
    }

    private TileType setName(String name) {
        this.name = name;
        return this;
    }

    private TileType setTexture(TextureRegion texture) {
        this.texture = texture;
        return this;
    }
}
