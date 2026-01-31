package net.ggj2026.twofourone.ecs.components;

import com.badlogic.gdx.math.Vector2;
import net.ggj2026.twofourone.gamelogic.MaskType;

public class EnemyComponent implements Component {

    public Vector2 targetPosition = null;

    public MaskType maskType = null;
    public float health = 100;
}
