package net.ggj2026.twofourone.effects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import net.ggj2026.twofourone.Util;
import net.ggj2026.twofourone.ecs.components.PositionComponent;

public class Lightning {

    public static void draw(ShapeRenderer shapeRenderer, Vector2 source, Vector2 target, Color color) {
        float lx = source.x;
        float ly = source.y;
        float dist = Util.pointDistance(source.x, source.y, target.x, target.y);
        float nx = (target.x - source.x) / dist;
        float ny = (target.y - source.y) / dist;
        int steps = 10;

        for (int i = 0; i < steps; i++) {
            float ax = source.x + (i + 1) * nx * dist / steps + Util.randomRange(-0.5f, 0.5f) * ny;
            float ay = source.y + (i + 1) * ny * dist / steps + Util.randomRange(-0.5f, 0.5f) * nx;
            shapeRenderer.setColor(color);
            shapeRenderer.rectLine(lx, ly, ax, ay, 0.15f);
            shapeRenderer.setColor(1f, 1f, 1f, 1f);
            shapeRenderer.rectLine(lx, ly, ax, ay, 0.05f);
            lx = ax;
            ly = ay;
        }
    }
}
