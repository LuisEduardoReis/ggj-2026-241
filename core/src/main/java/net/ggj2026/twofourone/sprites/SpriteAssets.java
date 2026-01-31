package net.ggj2026.twofourone.sprites;

import static net.ggj2026.twofourone.Assets.textureAtlas;

public class SpriteAssets {

    public static Sprite testSprite;
    public static Sprite testAnimatedSprite;
    public static Sprite playerSprite;
    public static Sprite oniMaskSprite;
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
        playerSprite = new Sprite()
            .addState("standing", new SpriteAnimation()
                .addFrame(textureAtlas.findRegion("player", 1))
            )
            .addState("walking", new SpriteAnimation()
                .addFrame(textureAtlas.findRegion("player", 1))
                .addFrame(textureAtlas.findRegion("player", 2))
                .addFrame(textureAtlas.findRegion("player", 3))
            );
        oniMaskSprite = Sprite.staticSprite(textureAtlas.findRegion("masks/oni"));
        kasperSprite = new Sprite()
            .addState("default", new SpriteAnimation()
                .addFrame(textureAtlas.findRegion("enemies/kasper", 1))
                .addFrame(textureAtlas.findRegion("enemies/kasper", 2))
                .addFrame(textureAtlas.findRegion("enemies/kasper", 3))
            );
        blueFireSprite = Sprite.staticSprite(textureAtlas.findRegion("bullets/blue_fire"));
        redFireSprite = Sprite.staticSprite(textureAtlas.findRegion("bullets/red_fire"));
        smokeParticleSprite = Sprite.staticSprite(textureAtlas.findRegion("particles/smoke"));
    }
}
