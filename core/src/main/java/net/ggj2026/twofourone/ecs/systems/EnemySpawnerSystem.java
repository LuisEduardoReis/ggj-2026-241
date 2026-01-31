package net.ggj2026.twofourone.ecs.systems;

import net.ggj2026.twofourone.Util;
import net.ggj2026.twofourone.ecs.components.PositionComponent;
import net.ggj2026.twofourone.ecs.entities.Enemy;
import net.ggj2026.twofourone.ecs.entities.Entity;
import net.ggj2026.twofourone.level.Level;

import java.util.Collections;

public class EnemySpawnerSystem extends AbstractSystem {

    float enemySpawnTimer = 0;
    float enemySpawnDelay = 0.5f;

    protected EnemySpawnerSystem() {
        super(Collections.emptyList());

        this.visitsEntities = false;
    }

    @Override
    public void update(Level level, float delta) {
        this.enemySpawnTimer = Util.stepTo(this.enemySpawnTimer, 0, delta);
        if (this.enemySpawnTimer == 0) {
            this.enemySpawnTimer = this.enemySpawnDelay;

            Entity enemy = Enemy.instance(level);
            float spawnDirection = Util.randomRange(0, (float) (2*Math.PI));
            float spawnX = (level.width/2f) + (float) (level.width*0.6f * Math.cos(spawnDirection));
            float spawnY = (level.height/2f) - (float) (level.width*0.6f * Math.sin(spawnDirection));
            enemy.getComponent(PositionComponent.class).set(spawnX, spawnY);
            level.addEntity(enemy);
        }
    }
}
