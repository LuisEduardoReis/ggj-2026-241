package net.ggj2026.twofourone.sprites;

public class SpriteState {
    public String state = null;
    public boolean visible = true;

    public float x = 0;
    public float y = 0;
    public float scale = 1f;
    public float scaleV = 0f;
    public boolean mirrorX = false;
    public float width = 1f;
    public float height = 1f;
    public float rotation = 0;
    public float rotationDelta = 0;
    public float alpha = 1;

    public boolean animated = true;
    public float animationTimer = 0;
    public int animationIndex = 0;
    public float animationDelay = 1f;
}
