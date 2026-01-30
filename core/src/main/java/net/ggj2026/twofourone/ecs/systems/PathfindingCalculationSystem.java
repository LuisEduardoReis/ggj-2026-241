package net.ggj2026.twofourone.ecs.systems;

import net.ggj2026.twofourone.ecs.components.PathfindingTargetComponent;
import net.ggj2026.twofourone.ecs.components.PositionComponent;
import net.ggj2026.twofourone.ecs.entities.Entity;
import net.ggj2026.twofourone.level.PathfindingMap;

import java.util.Arrays;

public class PathfindingCalculationSystem extends AbstractSystem {

    protected PathfindingCalculationSystem() {
        super(Arrays.asList(PathfindingTargetComponent.class, PositionComponent.class));
    }

    @Override
    protected void processUpdate(Entity entity, float delta) {
        PathfindingMap pathfindingMap = entity.level.pathfindingMap;
        PositionComponent position = entity.getComponent(PositionComponent.class);
        // TODO fill flood map for this target
    }
}
