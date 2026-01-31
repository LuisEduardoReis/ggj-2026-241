package net.ggj2026.twofourone.ecs.systems;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import net.ggj2026.twofourone.Assets;
import net.ggj2026.twofourone.Util;
import net.ggj2026.twofourone.ecs.components.*;
import net.ggj2026.twofourone.ecs.entities.Bullet;
import net.ggj2026.twofourone.ecs.entities.Entity;
import net.ggj2026.twofourone.effects.Lightning;
import net.ggj2026.twofourone.gamelogic.BulletType;
import net.ggj2026.twofourone.sprites.SpriteAssets;

import java.util.Collections;
import java.util.stream.Collectors;

public class KohEnemySystem extends AbstractSystem {
    protected KohEnemySystem() {
        super(Collections.singletonList(KohEnemyComponent.class));
    }

    @Override
    protected void processUpdate(Entity entity, float delta) {
        PositionComponent position = entity.getComponent(PositionComponent.class);
        EnemyComponent enemyComponent = entity.getComponent(EnemyComponent.class);
        KohEnemyComponent kohComponent = entity.getComponent(KohEnemyComponent.class);
        SpriteComponent spriteComponent = entity.getComponent(SpriteComponent.class);

        if (kohComponent.stage == 0 && enemyComponent.health < 2000) {
            kohComponent.stage++;
            spriteComponent.sprites.set(0, SpriteAssets.kohSprite2);
        }
        if (kohComponent.stage == 1 && enemyComponent.health < 1000) {
            kohComponent.stage++;
            spriteComponent.sprites.set(0, SpriteAssets.kohSprite3);
        }

        kohComponent.targets = entity.level.entities.stream()
            .filter(e -> e.hasComponent(PlayerComponent.class))
            .filter(e -> {
                PositionComponent playerPosition = e.getComponent(PositionComponent.class);
                float dist = Util.pointDistance(position.x, position.y, playerPosition.x, playerPosition.y);
                return dist < kohComponent.range;
            })
            .collect(Collectors.toList());

        for (Entity player : kohComponent.targets) {
            PlayerComponent playerComponent = player.getComponent(PlayerComponent.class);
            playerComponent.damage(kohComponent.damagePerSecond * delta, player);
        }
    }

    @Override
    protected void processShapeRenderer(Entity entity, ShapeRenderer shapeRenderer) {
        PositionComponent position = entity.getComponent(PositionComponent.class);
        KohEnemyComponent kohComponent = entity.getComponent(KohEnemyComponent.class);

        Vector2 source = position.toVector2();
        source.y += 1.67f;

        if (kohComponent.targets != null) {
            kohComponent.targets.forEach(target -> {
                PositionComponent targetPosition = target.getComponent(PositionComponent.class);
                Lightning.draw(shapeRenderer, source, targetPosition.toVector2(), Color.RED);
            });
        }
    }
}
