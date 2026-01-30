package net.ggj2026.twofourone.ecs.components;

import net.ggj2026.twofourone.controllers.GameController;

public class PlayerComponent implements Component {
    public float speed = 10;
    public GameController controller = null;
}
