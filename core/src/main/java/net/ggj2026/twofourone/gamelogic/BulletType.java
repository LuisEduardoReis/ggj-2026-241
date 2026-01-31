package net.ggj2026.twofourone.gamelogic;

import net.ggj2026.twofourone.sprites.Sprite;
import net.ggj2026.twofourone.sprites.SpriteAssets;

import java.util.HashMap;
import java.util.Map;

public enum BulletType {
    NORMAL,
    HIGH_DAMAGE;

    public static final Map<BulletType, Sprite> bulletSprites = new HashMap<>();
    static {
        bulletSprites.put(NORMAL, SpriteAssets.blueFireSprite);
        bulletSprites.put(HIGH_DAMAGE, SpriteAssets.redFireSprite);
    }
}
