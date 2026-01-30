package net.ggj2026.twofourone.ecs.components;

import net.ggj2026.twofourone.sprites.Sprite;
import net.ggj2026.twofourone.sprites.SpriteState;

public class SpriteComponent implements Component {
    public Sprite sprite = null;
    public SpriteState state = new SpriteState();
}
