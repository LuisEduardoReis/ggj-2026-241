package net.ggj2026.twofourone.ecs.components;

public class VelocityComponent implements Component {
    public float vx = 0;
    public float vy = 0;

    public void set(float vx, float vy) {
        this.vx = vx;
        this.vy = vy;
    }
}
