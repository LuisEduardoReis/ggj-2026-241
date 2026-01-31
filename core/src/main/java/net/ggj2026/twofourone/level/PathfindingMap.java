package net.ggj2026.twofourone.level;

import com.badlogic.gdx.math.Vector2;

import static net.ggj2026.twofourone.Util.eightDirections;

public class PathfindingMap {
    public Level level;
    public PathfindingNode[] map;

    public PathfindingMap(Level level) {
        this.level = level;
        map = new PathfindingNode[level.width * level.height];
        for (int i = 0; i < map.length; i++) {
            map[i] = new PathfindingNode();
        }
    }

    public void reset() {
        for (int i = 0; i < map.length; i++) {
            map[i].reset();
        }
    }

    public PathfindingNode getNode(int x, int y) {
        if (x < 0 || x >= this.level.width || y < 0 || y >= this.level.height) return null;
        return this.map[y * this.level.width + x];
    }

    public Vector2 getNextPositionFrom(float x, float y) {
        int ix = (int) Math.floor(x);
        int iy = (int) Math.floor(y);
        float targetX = this.level.width/2f;
        float targetY = this.level.height/2f;

        if (this.level.getTile(ix, iy) == this.level.boundaryTile) {
            return new Vector2(targetX, targetY);
        }

        int minDistance = Integer.MAX_VALUE;
        for (Vector2 direction : eightDirections) {
            PathfindingNode node = this.getNode((int) (ix + direction.x), (int) (iy + direction.y));
            if (node != null && node.distance < minDistance) {
                minDistance = node.distance;
                targetX = ix + direction.x + 0.5f;
                targetY = iy + direction.y + 0.5f;
            }
        }
        if (minDistance == Integer.MAX_VALUE) {
            return null;
        } else {
            return new Vector2(targetX, targetY);
        }
    }
}
