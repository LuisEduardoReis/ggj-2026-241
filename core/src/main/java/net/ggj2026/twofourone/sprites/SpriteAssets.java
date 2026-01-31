package net.ggj2026.twofourone.sprites;

import static net.ggj2026.twofourone.Assets.textureAtlas;

public class SpriteAssets {

    public static Sprite testSprite;
    public static Sprite testAnimatedSprite;
    public static Sprite playerSprite;
    public static Sprite oniMaskSprite;
    public static Sprite sanMaskSprite;
    public static Sprite lilithMaskSprite;
    public static Sprite kasperSprite;
    public static Sprite damselSprite;
    public static Sprite blueFireSprite;
    public static Sprite greenFireSprite;
    public static Sprite redFireSprite;
    public static Sprite smokeParticleSprite;
    public static Sprite shadowSprite;

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
        kasperSprite = new Sprite()
            .addState("default", new SpriteAnimation()
                .addFrame(textureAtlas.findRegion("enemies/kasper", 1))
                .addFrame(textureAtlas.findRegion("enemies/kasper", 2))
                .addFrame(textureAtlas.findRegion("enemies/kasper", 3))
            );
        damselSprite = new Sprite()
            .addState("default", new SpriteAnimation()
                .addFrame(textureAtlas.findRegion("enemies/damsel", 1))
                .addFrame(textureAtlas.findRegion("enemies/damsel", 2))
                .addFrame(textureAtlas.findRegion("enemies/damsel", 3))
                .addFrame(textureAtlas.findRegion("enemies/damsel", 4))
            );
        blueFireSprite = Sprite.staticSprite(textureAtlas.findRegion("bullets/blue_fire"));
        redFireSprite = Sprite.staticSprite(textureAtlas.findRegion("bullets/red_fire"));
        greenFireSprite = Sprite.staticSprite(textureAtlas.findRegion("bullets/green_fire"));
        smokeParticleSprite = Sprite.staticSprite(textureAtlas.findRegion("particles/smoke"));
        oniMaskSprite = Sprite.staticSprite(textureAtlas.findRegion("masks/oni"));
        lilithMaskSprite = Sprite.staticSprite(textureAtlas.findRegion("masks/lilith"));
        sanMaskSprite = Sprite.staticSprite(textureAtlas.findRegion("masks/san"));
        shadowSprite = Sprite.staticSprite(textureAtlas.findRegion("shadow"));
    }
}
