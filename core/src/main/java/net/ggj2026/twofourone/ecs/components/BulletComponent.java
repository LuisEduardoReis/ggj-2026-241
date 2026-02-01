package net.ggj2026.twofourone.ecs.components;

public class BulletComponent implements Component {

    public float t = 0;
    public float lifetime = 5f;
    public boolean friendly = true;

    public float speed = 10;
    public float damage = 100;

    public float trailTimer = 0;
    public float trailDelay = 0.1f;

}
