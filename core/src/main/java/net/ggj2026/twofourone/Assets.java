package net.ggj2026.twofourone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.badlogic.gdx.graphics.Texture.TextureFilter.Nearest;
import static net.ggj2026.twofourone.Main.TILE_SIZE;

public class Assets {

    public static Texture testTexture;
    public static Texture tilesheet;
    public static TextureAtlas textureAtlas;
    static TextureRegion[][] tileTextures;

    public static BitmapFont font;

    public static void createAssets() {
        font = new BitmapFont(Gdx.files.internal("font.fnt"));
        testTexture = new Texture("badlogic.jpg");
        textureAtlas = new TextureAtlas("spritesheet.atlas");
        textureAtlas.getTextures().forEach((texture) -> texture.setFilter(Nearest, Nearest));

        tilesheet = new Texture("tilesheet.png");
        tileTextures = TextureRegion.split(tilesheet, TILE_SIZE, TILE_SIZE);
    }

    public static TextureRegion getTileTextureById(int id) {
        return tileTextures[id / tileTextures.length][id % tileTextures.length];
    }
}
