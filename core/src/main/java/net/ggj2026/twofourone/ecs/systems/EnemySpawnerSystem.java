package net.ggj2026.twofourone.ecs.systems;

import net.ggj2026.twofourone.Util;
import net.ggj2026.twofourone.ecs.components.DamselEnemyComponent;import net.ggj2026.twofourone.ecs.components.EnemyComponent;
import net.ggj2026.twofourone.ecs.components.PositionComponent;
import net.ggj2026.twofourone.ecs.entities.Entity;
import net.ggj2026.twofourone.ecs.entities.enemies.DamselEnemy;import net.ggj2026.twofourone.ecs.entities.enemies.KasperEnemy;
import net.ggj2026.twofourone.gamelogic.EnemyStage;import net.ggj2026.twofourone.level.Level;

import java.util.Collections;

public class EnemySpawnerSystem extends AbstractSystem {

    EnemyStage stage = EnemyStage.START;

    boolean stageTimerActive = true;
    float stageTimer = 0;
    float stageDelay = 2 * 60;

    int maxEnemies = 0;
    float enemySpawnTimer = 0;
    float enemySpawnDelay = 0.5f;

    protected EnemySpawnerSystem() {
        super(Collections.emptyList());

        this.visitsEntities = false;
    }

    @Override
    public void update(Level level, float delta) {

        this.stageTimer = Util.stepTo(this.stageTimer, 0, delta);
        this.enemySpawnTimer = Util.stepTo(this.enemySpawnTimer, 0, delta);

        long numEnemies = level.entities.stream()
            .filter(entity -> entity.hasComponent(EnemyComponent.class))
            .count();
        long numDamsels = level.entities.stream()
            .filter(entity -> entity.hasComponent(DamselEnemyComponent.class))
            .count();

        switch (this.stage) {
            case START:
                break;
            case DEFAULT:
                if (this.stageTimer > 0 && this.enemySpawnTimer == 0 && numEnemies < maxEnemies) {
                    this.enemySpawnTimer = this.enemySpawnDelay;
                    this.spawnEnemy(level, KasperEnemy.instance(level, Math.random() < 0.2));
                }
                break;
            case DAMSEL:
                if (numDamsels > 0 && this.enemySpawnTimer == 0 && numEnemies < 3) {
                    this.enemySpawnTimer = this.enemySpawnDelay;
                    this.spawnEnemy(level, KasperEnemy.instance(level, true));
                }
                break;
        }

        if (numEnemies == 0) {
            switch (this.stage) {
                case START:
                    transitionStage(EnemyStage.DEFAULT, level);
                    break;
                case DEFAULT:
                    if (Math.random() < 0.5) {
                        transitionStage(EnemyStage.DAMSEL, level);
                    } else {
                        transitionStage(EnemyStage.DEFAULT, level);
                    }
                    break;
                case DAMSEL:
                    transitionStage(EnemyStage.DEFAULT, level);
                    break;
            }
        }
    }

    private void transitionStage(EnemyStage enemyStage, Level level) {
        this.stage = enemyStage;
        this.stageTimer = this.stageDelay;

        switch (this.stage) {
            case DEFAULT:
                this.maxEnemies += 5;
                break;
            case DAMSEL:
                this.spawnEnemy(level, DamselEnemy.instance(level));
                break;
        }
    }

    private Entity spawnEnemy(Level level, Entity enemy) {
        level.addEntity(enemy);
        float spawnDirection = Util.randomRange(0, (float) (2*Math.PI));
        float spawnX = (level.width/2f) + (float) (level.width*0.6f * Math.cos(spawnDirection));
        float spawnY = (level.height/2f) - (float) (level.width*0.6f * Math.sin(spawnDirection));
        enemy.getComponent(PositionComponent.class).set(spawnX, spawnY);
        return enemy;
    }
}
