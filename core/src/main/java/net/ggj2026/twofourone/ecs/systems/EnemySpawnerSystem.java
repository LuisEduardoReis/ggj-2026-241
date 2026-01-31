package net.ggj2026.twofourone.ecs.systems;

import net.ggj2026.twofourone.Util;
import net.ggj2026.twofourone.ecs.components.EnemyComponent;
import net.ggj2026.twofourone.ecs.components.PositionComponent;
import net.ggj2026.twofourone.ecs.entities.enemies.DamselEnemy;
import net.ggj2026.twofourone.ecs.entities.Entity;
import net.ggj2026.twofourone.ecs.entities.enemies.KasperEnemy;
import net.ggj2026.twofourone.level.Level;

import java.util.Collections;

public class EnemySpawnerSystem extends AbstractSystem {

    boolean spawnedBoss = false;
    float enemySpawnTimer = 0;
    float enemySpawnDelay = 0.5f;

    protected EnemySpawnerSystem() {
        super(Collections.emptyList());

        this.visitsEntities = false;
    }

    @Override
    public void update(Level level, float delta) {
        this.enemySpawnTimer = Util.stepTo(this.enemySpawnTimer, 0, delta);
        long numEnemies = level.entities.stream()
            .filter(entity -> entity.hasComponent(EnemyComponent.class))
            .count();

        if (!spawnedBoss) {
            spawnedBoss = true;
            this.spawnEnemy(level, DamselEnemy.instance(level));
        }

        if (this.enemySpawnTimer == 0 && numEnemies < 20) {
            this.enemySpawnTimer = this.enemySpawnDelay;

            this.spawnEnemy(level, KasperEnemy.instance(level));
        }
    }

    private void spawnEnemy(Level level, Entity enemy) {
        level.addEntity(enemy);
        float spawnDirection = Util.randomRange(0, (float) (2*Math.PI));
        float spawnX = (level.width/2f) + (float) (level.width*0.6f * Math.cos(spawnDirection));
        float spawnY = (level.height/2f) - (float) (level.width*0.6f * Math.sin(spawnDirection));
        enemy.getComponent(PositionComponent.class).set(spawnX, spawnY);
    }
}
