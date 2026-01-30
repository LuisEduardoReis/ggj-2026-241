package net.ggj2026.twofourone.ecs.components;

public class PositionComponent implements Component {
    public float px = 0;
    public float py = 0;
    public float x = 0;
    public float y = 0;

    public void set(float x, float y) {
        this.px = this.x = x;
        this.py = this.y = y;
    }
}
