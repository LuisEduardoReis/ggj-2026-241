package net.ggj2026.twofourone.ecs.components;

import net.ggj2026.twofourone.ecs.entities.Entity;

import java.util.List;

public class KohEnemyComponent implements Component {

    public float range = 10;
    public List<Entity> targets = null;
}
