package net.ggj2026.twofourone.level;

public class PathfindingNode {
    public int distance = Integer.MAX_VALUE;

    public void reset() {
        this.distance = Integer.MAX_VALUE;
    }
}
