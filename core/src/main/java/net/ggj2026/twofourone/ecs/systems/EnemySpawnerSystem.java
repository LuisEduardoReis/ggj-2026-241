package net.ggj2026.twofourone.ecs.systems;

import net.ggj2026.twofourone.Util;
import net.ggj2026.twofourone.ecs.components.DamselEnemyComponent;
import net.ggj2026.twofourone.ecs.components.EnemyComponent;
import net.ggj2026.twofourone.ecs.components.PositionComponent;
import net.ggj2026.twofourone.ecs.components.VelocityComponent;
import net.ggj2026.twofourone.ecs.entities.Entity;
import net.ggj2026.twofourone.ecs.entities.enemies.DamselEnemy;
import net.ggj2026.twofourone.ecs.entities.enemies.KasperEnemy;
import net.ggj2026.twofourone.gamelogic.EnemyStage;
import net.ggj2026.twofourone.level.Level;

import java.util.*;

public class EnemySpawnerSystem extends AbstractSystem {

    EnemyStage stage = EnemyStage.GRACE;
    float stageTimer = 3f;

    List<EnemyStage> stageOrder = Arrays.asList(
        EnemyStage.DEFAULT,
        EnemyStage.RUSH,
        EnemyStage.DAMSEL,
        EnemyStage.DEFAULT,
        EnemyStage.RUSH,
        EnemyStage.DAMSEL,
        EnemyStage.DEFAULT,
        EnemyStage.RUSH
    );
    int stageIndex = 0;
    List<String> ominousMessages = Arrays.asList(
        "You are not ready",
        "Turn back",
        "You will die",
        "You are alone",
        "Pathetic",
        "GYAAAAAAAAAAAAAAAAHHHHHHHH!"
    );
    int ominousMessagesIndex = 0;

    int maxEnemies = 0;
    float enemySpawnTimer = 0;
    float enemySpawnDelay = 0.5f;

    protected EnemySpawnerSystem() {
        super(Collections.emptyList());

        this.visitsEntities = false;
    }

    @Override
    public void update(Level level, float delta) {

        if (level.gameOver) return;

        this.stageTimer = Util.stepTo(this.stageTimer, 0, delta);
        this.enemySpawnTimer = Util.stepTo(this.enemySpawnTimer, 0, delta);

        long numEnemies = level.entities.stream()
            .filter(entity -> entity.hasComponent(EnemyComponent.class))
            .count();
        long numDamsels = level.entities.stream()
            .filter(entity -> entity.hasComponent(DamselEnemyComponent.class))
            .count();

        switch (this.stage) {
            case GRACE:
                if (this.stageTimer == 0) {
                    enterStage(stageOrder.get(stageIndex++), level);
                    stageIndex %= stageOrder.size();
                }
                break;
            case DEFAULT:
                if (this.stageTimer > 0 && this.enemySpawnTimer == 0 && numEnemies < maxEnemies) {
                    this.enemySpawnTimer = this.enemySpawnDelay;
                    this.spawnEnemyOutOfBounds(level, KasperEnemy.instance(level, Math.random() < 0.2));
                }
                if (this.stageTimer == 0 && numEnemies == 0) {
                    this.enterStage(EnemyStage.GRACE, level);
                }
                break;
            case RUSH:
                if (numEnemies == 0) {
                    this.enterStage(EnemyStage.GRACE, level);
                }
                break;
            case DAMSEL:
                if (numDamsels > 0 && this.enemySpawnTimer == 0 && numEnemies < 3) {
                    this.enemySpawnTimer = this.enemySpawnDelay;
                    this.spawnEnemyOutOfBounds(level, KasperEnemy.instance(level, Math.random() < 0.2));
                }
                if (numEnemies == 0) {
                    this.enterStage(EnemyStage.GRACE, level);
                }
                break;
        }
    }

    private void enterStage(EnemyStage enemyStage, Level level) {
        this.stage = enemyStage;

        switch (this.stage) {
            case GRACE:
                this.stageTimer = 3f;
                break;
            case DEFAULT:
                this.stageTimer = 30f;
                this.maxEnemies += 5;
                level.showMessage(ominousMessages.get(ominousMessagesIndex++));
                ominousMessagesIndex %= ominousMessages.size();
                break;
            case RUSH:
                for (int i = 0; i < 40; i++) {
                    this.spawnEnemyOutOfBounds(level, KasperEnemy.instance(level, Math.random() < 0.2));
                }
                level.showMessage(ominousMessages.get(ominousMessagesIndex++));
                ominousMessagesIndex %= ominousMessages.size();
                break;
            case DAMSEL:
                float spawnDirection = Util.randomRange(0, (float) (2*Math.PI));
                Entity entity = this.spawnEnemy(level, DamselEnemy.instance(level), (float) level.width / 2, (float) level.height / 2);
                float speed = entity.getComponent(EnemyComponent.class).speed;
                entity.getComponent(VelocityComponent.class).set((float) (Math.cos(spawnDirection) * speed), (float) (Math.sin(spawnDirection) * speed));
                level.showMessage("<Code Blue>");
                break;
        }
    }

    private Entity spawnEnemy(Level level, Entity enemy, float x, float y) {
        level.addEntity(enemy);
        enemy.getComponent(PositionComponent.class).set(x, y);
        return enemy;
    }

    private Entity spawnEnemyOutOfBounds(Level level, Entity enemy) {
        level.addEntity(enemy);
        float spawnDirection = Util.randomRange(0, (float) (2*Math.PI));
        float spawnX = (level.width/2f) + (float) (level.width*0.6f * Math.cos(spawnDirection));
        float spawnY = (level.height/2f) - (float) (level.width*0.6f * Math.sin(spawnDirection));
        enemy.getComponent(PositionComponent.class).set(spawnX, spawnY);
        return enemy;
    }
}
