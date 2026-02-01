package net.ggj2026.twofourone.gamelogic;

import net.ggj2026.twofourone.Util;
import net.ggj2026.twofourone.sprites.Sprite;
import net.ggj2026.twofourone.sprites.SpriteAssets;

import java.util.HashMap;
import java.util.Map;

public enum MaskType {
    SAN,
    LILITH,
    TORU,
    ONI;

    public static final Map<MaskType, Sprite> maskSprites = new HashMap<>();
    static {
        maskSprites.put(ONI, SpriteAssets.oniMaskSprite);
        maskSprites.put(SAN, SpriteAssets.sanMaskSprite);
        maskSprites.put(LILITH, SpriteAssets.lilithMaskSprite);
        maskSprites.put(TORU, SpriteAssets.toruMaskSprite);
    }

    public static MaskType randomMaskType() {
        return MaskType.values()[Util.randomRangeInt(0, MaskType.values().length)];
    }
}
