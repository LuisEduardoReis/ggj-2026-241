package net.ggj2026.twofourone.gamelogic;

import net.ggj2026.twofourone.sprites.Sprite;
import net.ggj2026.twofourone.sprites.SpriteAssets;

import java.util.HashMap;
import java.util.Map;

public enum MaskType {
    SAN,
    LILITH,
    ONI;

    public static final Map<MaskType, Sprite> maskSprites = new HashMap<>();
    static {
        maskSprites.put(ONI, SpriteAssets.oniMaskSprite);
        maskSprites.put(SAN, SpriteAssets.sanMaskSprite);
        maskSprites.put(LILITH, SpriteAssets.lilithMaskSprite);
    }
}
