package net.ggj2026.twofourone.sprites;

import static net.ggj2026.twofourone.Assets.textureAtlas;

public class SpriteAssets {

    public static Sprite testSprite;
    public static Sprite testAnimatedSprite;
    public static Sprite oniMaskSprite;
    public static Sprite enemyTestSprite;
    public static Sprite kasperSprite;
    public static Sprite blueFireSprite;
    public static Sprite redFireSprite;
    public static Sprite smokeParticleSprite;

    public static void initSprites() {
        testSprite = Sprite.staticSprite(textureAtlas.findRegion("test"));
        testAnimatedSprite = new Sprite()
                .addState("default", new SpriteAnimation()
                        .addFrame(textureAtlas.findRegion("test_animated_sprite", 1))
                        .addFrame(textureAtlas.findRegion("test_animated_sprite", 2))
                        .addFrame(textureAtlas.findRegion("test_animated_sprite", 3))
                        .addFrame(textureAtlas.findRegion("test_animated_sprite", 4))
                );
        oniMaskSprite = Sprite.staticSprite(textureAtlas.findRegion("masks/oni"));
        enemyTestSprite = Sprite.staticSprite(textureAtlas.findRegion("enemies/test_enemy"));
        kasperSprite = Sprite.staticSprite(textureAtlas.findRegion("enemies/kasper"));
        blueFireSprite = Sprite.staticSprite(textureAtlas.findRegion("bullets/blue_fire"));
        redFireSprite = Sprite.staticSprite(textureAtlas.findRegion("bullets/red_fire"));
        smokeParticleSprite = Sprite.staticSprite(textureAtlas.findRegion("particles/smoke"));
    }
}
