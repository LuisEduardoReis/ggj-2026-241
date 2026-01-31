package net.ggj2026.twofourone.ecs.entities;

import net.ggj2026.twofourone.controllers.GameController;
import net.ggj2026.twofourone.ecs.components.*;
import net.ggj2026.twofourone.level.Level;
import net.ggj2026.twofourone.sprites.EntityZ;
import net.ggj2026.twofourone.sprites.SpriteAssets;

public class Player {
    public static Entity instance(Level level, GameController controller) {
        Entity player = new Entity(level)
                .addComponent(new PlayerComponent())
                .addComponent(new PositionComponent())
                .addComponent(new SpriteComponent())
                .addComponent(new VelocityComponent())
                .addComponent(new LevelCollisionComponent())
                .addComponent(new EntityCollisionsComponent())
                .addComponent(new PathfindingTargetComponent());

        player.z = EntityZ.PLAYERS;

        PlayerComponent playerComponent = player.getComponent(PlayerComponent.class);
        playerComponent.controller = controller;

        player.getComponent(LevelCollisionComponent.class).isTileSolid = (tile) -> tile.type.solid || tile.type.solidToPlayer;

        SpriteComponent spriteComponent = player.getComponent(SpriteComponent.class);
        spriteComponent.addSprite(SpriteAssets.enemyTestSprite);
        spriteComponent.states.get(0).scale = 1;
        spriteComponent.addSprite(SpriteAssets.oniMaskSprite);
        spriteComponent.states.get(1).visible = false;
        spriteComponent.states.get(1).x = -0.5f/16f;
        spriteComponent.states.get(1).y = 0.2f;
        spriteComponent.states.get(1).scale = 0.75f;


        return player;
    }
}
