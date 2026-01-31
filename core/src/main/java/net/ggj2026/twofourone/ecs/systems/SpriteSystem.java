package net.ggj2026.twofourone.ecs.systems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Affine2;
import net.ggj2026.twofourone.Util;
import net.ggj2026.twofourone.ecs.components.Component;
import net.ggj2026.twofourone.ecs.components.PositionComponent;
import net.ggj2026.twofourone.ecs.components.SpriteComponent;
import net.ggj2026.twofourone.ecs.entities.Entity;
import net.ggj2026.twofourone.sprites.Sprite;
import net.ggj2026.twofourone.sprites.SpriteAnimation;
import net.ggj2026.twofourone.sprites.SpriteFrame;
import net.ggj2026.twofourone.sprites.SpriteState;

import java.util.Collections;

public class SpriteSystem extends AbstractSystem {
    protected SpriteSystem() {
        super(Collections.singletonList(SpriteComponent.class));
    }

    @Override
    protected void processUpdate(Entity entity, float delta) {
        SpriteComponent spriteComponent = entity.getComponent(SpriteComponent.class);
        for (int i = 0; i < spriteComponent.sprites.size(); i++) {
            Sprite sprite = spriteComponent.sprites.get(i);
            SpriteState state = spriteComponent.states.get(i);

            if (state.animated) {
                SpriteAnimation spriteAnimation = sprite.getState(state.state);

                state.rotation += state.rotationDelta * delta;
                state.scale = Math.max(0, state.scale + state.scaleV * delta);

                state.animationTimer += delta;
                if (state.animationTimer > state.animationDelay) {
                    state.animationTimer -= state.animationDelay;
                    state.animationIndex++;
                    state.animationIndex %= spriteAnimation.frames.size();
                }
            } else {
                state.animationIndex = 0;
            }

        }

    }

    private static final Affine2 affine2 = new Affine2();

    @Override
    protected void processSpriteBatch(Entity entity, SpriteBatch spriteBatch) {
        SpriteComponent spriteComponent = entity.getComponent(SpriteComponent.class);
        PositionComponent positionComponent = entity.getComponent(PositionComponent.class);

        for (int i = 0; i < spriteComponent.sprites.size(); i++) {
            Sprite sprite = spriteComponent.sprites.get(i);
            SpriteState state = spriteComponent.states.get(i);
            SpriteFrame frame = sprite.getState(state.state).frames.get(state.animationIndex);

            if (!state.visible) continue;

            affine2.idt();
            affine2.translate(positionComponent.x, positionComponent.y);
            affine2.rotate(state.rotation * Util.RAD_TO_DEG);
            affine2.scale(state.scale, state.scale);
            if (state.mirrorX) {
                affine2.scale(-1, 1);
            }
            affine2.translate(state.x, state.y);
            affine2.translate(-state.width / 2, -state.height / 2);
            spriteBatch.draw(frame.textureRegion, state.width, state.height, affine2);
        }
    }
}
