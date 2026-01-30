package net.ggj2026.twofourone.level;

public class PathfindingMap {
    public Level level;
    public PathfindingNode[] map;

    public PathfindingMap(Level level) {
        this.level = level;
        map = new PathfindingNode[level.width * level.height];
    }

    public PathfindingNode getNode(int x, int y) {
        if (x < 0 || x >= this.level.width || y < 0 || y >= this.level.height) return null;
        return this.map[y * this.level.width + x];
    }
}
