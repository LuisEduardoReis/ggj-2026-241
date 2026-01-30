package net.ggj2026.twofourone.ecs.components;

import net.ggj2026.twofourone.sprites.Sprite;
import net.ggj2026.twofourone.sprites.SpriteState;

import java.util.ArrayList;
import java.util.List;

public class SpriteComponent implements Component {
    public List<Sprite> sprites = new ArrayList<>();
    public List<SpriteState> states = new ArrayList<>();

    public void addSprite(Sprite sprite) {
        this.sprites.add(sprite);
        this.states.add(new SpriteState());
    }
}
