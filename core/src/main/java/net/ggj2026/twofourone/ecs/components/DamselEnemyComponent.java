package net.ggj2026.twofourone.ecs.components;

public class DamselEnemyComponent implements Component {

    public enum State {
        IDLE,
        SPIRAL,
        BURST,
    }

    public State state = State.IDLE;
    public float stateTimer = 10f;
    public float fireDirection = 0;
    public float fireRotationSpeed = (float) (2*Math.PI) / 2;
}
