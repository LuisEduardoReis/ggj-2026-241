package net.ggj2026.twofourone.ecs.components;

import net.ggj2026.twofourone.level.Tile;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class LevelCollisionComponent implements Component {
    public float radius = 0.3f;
    public boolean collidesWithLevel = true;

    public Function<Tile, Boolean> isTileSolid = (tile) -> tile.type.solid;
    public BiConsumer<Float, Float> handleLevelCollision = (x,y) -> {};
}
