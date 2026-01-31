package net.ggj2026.twofourone.ecs.components;

import com.badlogic.gdx.math.Vector2;
import net.ggj2026.twofourone.ecs.entities.Entity;
import net.ggj2026.twofourone.gamelogic.MaskType;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class EnemyComponent implements Component {

    public float speed = 2.5f;

    public float attackTimer = 0;
    public float attackDelay = 1;
    public float attackDamage = 25;

    public Vector2 targetPosition = null;

    public boolean pathfind = true;

    public float health = 100;

    public Consumer<Entity> onDeath = entity -> {};
}
