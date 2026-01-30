package net.ggj2026.twofourone.ecs.components;

import net.ggj2026.twofourone.level.Tile;

public class LevelCollisionComponent implements Component {
    public boolean collidesWithLevel = true;
    public float radius = 0.25f;

    public boolean isTileSolid(Tile tile) {
        return tile.type.solid;
    }
}
