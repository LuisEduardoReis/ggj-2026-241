package net.ggj2026.twofourone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static com.badlogic.gdx.graphics.Texture.TextureFilter.Nearest;

public class Assets {

    public static Texture testTexture;
    public static TextureAtlas textureAtlas;

    public static BitmapFont font;

    public static void createAssets() {
        font = new BitmapFont(Gdx.files.internal("font.fnt"));
        testTexture = new Texture("badlogic.jpg");
        textureAtlas = new TextureAtlas("spritesheet.atlas");
        textureAtlas.getTextures().forEach((texture) -> texture.setFilter(Nearest, Nearest));
    }
}
