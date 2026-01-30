package net.ggj2026.twofourone.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.List;

public class SpriteAnimation {

    public List<SpriteFrame> frames;

    public SpriteAnimation() {
        this.frames = new ArrayList<>();
    }

    public SpriteAnimation addFrame(TextureRegion textureRegion) {
        this.frames.add(new SpriteFrame(textureRegion));
        return this;
    }

    public SpriteAnimation addFrame(TextureRegion textureRegion, float scaleX, float scaleY) {
        this.frames.add(new SpriteFrame(textureRegion).setScale(scaleX, scaleY));
        return this;
    }
}
