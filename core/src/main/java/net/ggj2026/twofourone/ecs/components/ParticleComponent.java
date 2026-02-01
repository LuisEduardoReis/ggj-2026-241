package net.ggj2026.twofourone.ecs.components;

public class ParticleComponent implements Component {
    public float t = 0;
    public float lifetime = 1;

    public boolean fadeout = false;
    public float fadeoutDelay = 1;
}
