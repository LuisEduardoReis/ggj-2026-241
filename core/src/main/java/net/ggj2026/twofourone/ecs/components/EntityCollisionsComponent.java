package net.ggj2026.twofourone.ecs.components;

import net.ggj2026.twofourone.ecs.entities.Entity;

import java.util.function.BiConsumer;

public class EntityCollisionsComponent implements Component {
    public float radius = 0.45f;
    public float mass = 1;
    public boolean collidesWithOthers = true;
    public boolean pushesOthers = true;
    public BiConsumer<Entity, Entity> handleEntityCollision = (me, other) -> {};
}
