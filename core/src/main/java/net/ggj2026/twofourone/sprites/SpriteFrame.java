package net.ggj2026.twofourone.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteFrame {
    public TextureRegion textureRegion;
    public float scaleX = 1;
    public float scaleY = 1;

    public SpriteFrame(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public SpriteFrame setScale(float x, float y) {
        this.scaleX = x;
        this.scaleY = y;
        return this;
    }
}
