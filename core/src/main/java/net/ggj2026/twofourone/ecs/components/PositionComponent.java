package net.ggj2026.twofourone.ecs.components;

public class PositionComponent implements Component {
    public float x = 0;
    public float y = 0;

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
