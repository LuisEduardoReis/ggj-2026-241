package net.ggj2026.twofourone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.badlogic.gdx.graphics.Texture.TextureFilter.Nearest;
import static net.ggj2026.twofourone.Main.TILE_SIZE;

public class Assets {

    public static Texture testTexture;
    public static Texture tilesheet;
    public static Texture fillTexture;
    public static TextureAtlas textureAtlas;
    static TextureRegion[][] tileTextures;
    public static com.badlogic.gdx.audio.Music menuMusic;

    public static Sound hitHurt;
    public static Sound enemyDeath;
    public static Sound beamSound1;
    public static Sound damselFire;
    public static Sound damselDeath;
    public static Sound damselSpawn;



    public static BitmapFont font;

    public static void createAssets() {
        font = new BitmapFont(Gdx.files.internal("font.fnt"));
        testTexture = new Texture("badlogic.jpg");
        textureAtlas = new TextureAtlas("spritesheet.atlas");
        textureAtlas.getTextures().forEach((texture) -> texture.setFilter(Nearest, Nearest));

        tilesheet = new Texture("tilesheet.png");
        tileTextures = TextureRegion.split(tilesheet, TILE_SIZE, TILE_SIZE);

        Pixmap p = new Pixmap(Main.WIDTH, Main.HEIGHT, Pixmap.Format.RGBA8888);
        p.setColor(1, 1, 1, 1);
        p.fill();
        fillTexture = new Texture(p);
        p.dispose();

        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("soundtrack.wav"));
        menuMusic.setLooping(true);
        menuMusic.play();

        enemyDeath = Gdx.audio.newSound(Gdx.files.internal("./sounds/enemy-death.wav"));
        hitHurt = Gdx.audio.newSound(Gdx.files.internal("./sounds/hit-hurt.wav"));
        beamSound1 = Gdx.audio.newSound(Gdx.files.internal("./sounds/beam-sound-1.wav"));
        damselFire = Gdx.audio.newSound(Gdx.files.internal("./sounds/damsel-fire.wav"));
        damselSpawn = Gdx.audio.newSound(Gdx.files.internal("./sounds/damsel-spawn.wav"));
        damselDeath = Gdx.audio.newSound(Gdx.files.internal("./sounds/damsel-death.wav"));
    }

    public static TextureRegion getTileTextureById(int id) {
        return tileTextures[id / tileTextures.length][id % tileTextures.length];
    }
}
