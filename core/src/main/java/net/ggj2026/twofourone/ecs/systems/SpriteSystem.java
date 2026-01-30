package net.ggj2026.twofourone.ecs.systems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Affine2;
import net.ggj2026.twofourone.ecs.components.Component;
import net.ggj2026.twofourone.ecs.components.PositionComponent;
import net.ggj2026.twofourone.ecs.components.SpriteComponent;
import net.ggj2026.twofourone.ecs.entities.Entity;
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
        SpriteState state = spriteComponent.state;

        if (spriteComponent.sprite != null && state.animated) {
            SpriteAnimation spriteAnimation = spriteComponent.sprite.getState(state.state);

            state.animationTimer += delta;
            if (state.animationTimer > state.animationDelay) {
                state.animationTimer -= state.animationDelay;
                state.animationIndex++;
                state.animationIndex %= spriteAnimation.frames.size();
            }
        }
    }

    private static final Affine2 affine2 = new Affine2();

    @Override
    protected void processSpriteBatch(Entity entity, SpriteBatch spriteBatch) {
        SpriteComponent spriteComponent = entity.getComponent(SpriteComponent.class);
        PositionComponent positionComponent = entity.getComponent(PositionComponent.class);
        SpriteState state = spriteComponent.state;
        SpriteFrame frame = spriteComponent.sprite.getState(state.state).frames.get(state.animationIndex);

        if (spriteComponent.sprite == null || !state.visible) return;

        affine2.idt();
        affine2.translate(positionComponent.x, positionComponent.y);
        affine2.rotate(state.rotation);
        affine2.scale(state.scaleX, state.scaleY);
        affine2.scale(frame.scaleX, frame.scaleY);
        affine2.translate(-state.width/2, -state.height/2);
        spriteBatch.draw(frame.textureRegion, state.width, state.height, affine2);
    }
}
