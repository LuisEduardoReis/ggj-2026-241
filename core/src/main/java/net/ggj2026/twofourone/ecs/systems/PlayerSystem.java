package net.ggj2026.twofourone.ecs.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import net.ggj2026.twofourone.ecs.components.*;
import net.ggj2026.twofourone.ecs.entities.Entity;
import net.ggj2026.twofourone.ecs.entities.TestBullet;

import java.util.Collections;

public class PlayerSystem extends AbstractSystem {
    protected PlayerSystem() {
        super(Collections.singletonList(PlayerComponent.class));
    }

    @Override
    public void processUpdate(Entity entity, float delta) {
        PlayerComponent player = entity.getComponent(PlayerComponent.class);
        PositionComponent position = entity.getComponent(PositionComponent.class);

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            position.y += player.speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            position.y -= player.speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            position.x -= player.speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            position.x += player.speed * delta;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Entity bullet = TestBullet.instance(entity.level);
            bullet.getComponent(PositionComponent.class).set(position.x, position.y);
            bullet.getComponent(VelocityComponent.class).set(bullet.getComponent(BulletComponent.class).speed, 0);
            entity.level.addEntity(bullet);
        }
    }
}
