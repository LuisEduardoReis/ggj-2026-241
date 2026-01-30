package net.ggj2026.twofourone.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class Sprite {
    public Map<String, SpriteAnimation> states;

    public Sprite() {
        states = new HashMap<>();
    }

    public Sprite addState(String name, SpriteAnimation sprite) {
        if (states.isEmpty()) {
            states.put("default", sprite);
        }
        states.put(name, sprite);
        return this;
    }

    public SpriteAnimation getState(String name) {
        if (states.containsKey(name)) return states.get(name);
        else return states.get("default");
    }

    public static Sprite staticSprite(TextureRegion textureRegion) {
        return new Sprite().addState("default", new SpriteAnimation().addFrame(textureRegion));
    }
}
