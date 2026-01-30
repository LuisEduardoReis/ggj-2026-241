package net.ggj2026.twofourone.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static net.ggj2026.twofourone.Assets.textureAtlas;

public class SpriteAssets {

    public static Sprite testSprite;
    public static Sprite testAnimatedSprite;
    public static Sprite playerSprite;
    public static Sprite enemyTestSprite;

    public static void initSprites() {
        testSprite = Sprite.staticSprite(textureAtlas.findRegion("test"));
        testAnimatedSprite = new Sprite()
                .addState("default", new SpriteAnimation()
                        .addFrame(textureAtlas.findRegion("test_animated_sprite", 1))
                        .addFrame(textureAtlas.findRegion("test_animated_sprite", 2))
                        .addFrame(textureAtlas.findRegion("test_animated_sprite", 3))
                        .addFrame(textureAtlas.findRegion("test_animated_sprite", 4))
                );
        playerSprite = Sprite.staticSprite(textureAtlas.findRegion("masks/oni"));
        enemyTestSprite = Sprite.staticSprite(textureAtlas.findRegion("enemies/test_enemy"));
    }
}
