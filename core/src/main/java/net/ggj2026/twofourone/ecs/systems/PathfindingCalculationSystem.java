package net.ggj2026.twofourone.ecs.systems;

import com.badlogic.gdx.math.Vector3;
import net.ggj2026.twofourone.ecs.components.PathfindingTargetComponent;
import net.ggj2026.twofourone.ecs.components.PositionComponent;
import net.ggj2026.twofourone.ecs.entities.Entity;
import net.ggj2026.twofourone.level.Level;
import net.ggj2026.twofourone.level.PathfindingMap;
import net.ggj2026.twofourone.level.PathfindingNode;
import net.ggj2026.twofourone.level.Tile;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

import static net.ggj2026.twofourone.Util.getNodeId;

public class PathfindingCalculationSystem extends AbstractSystem {

    protected PathfindingCalculationSystem() {
        super(Arrays.asList(PathfindingTargetComponent.class, PositionComponent.class));
    }

    @Override
    protected void processUpdate(Entity entity, float delta) {
        Level level = entity.level;
        PathfindingMap pathfindingMap = level.pathfindingMap;
        PositionComponent position = entity.getComponent(PositionComponent.class);

        Set<String> visited = new HashSet<>();
        Deque<Vector3> toVisit = new LinkedBlockingDeque<>();
        toVisit.add(new Vector3((int) Math.floor(position.px), (int) Math.floor(position.py), 0));

        while(!toVisit.isEmpty()) {
            Vector3 current = toVisit.pop();
            Tile tile = level.getTile(current.x, current.y);
            String nodeId = getNodeId((int) current.x, (int) current.y);
            PathfindingNode node = pathfindingMap.getNode((int) current.x, (int) current.y);

            if (visited.contains(nodeId) || tile.type.solid || tile == level.boundaryTile || node.distance < current.z) continue;

            node.distance = (int) current.z;
            visited.add(nodeId);
            toVisit.add(new Vector3(current.x + 1, current.y, current.z + 1));
            toVisit.add(new Vector3(current.x - 1, current.y, current.z + 1));
            toVisit.add(new Vector3(current.x, current.y + 1, current.z + 1));
            toVisit.add(new Vector3(current.x, current.y - 1, current.z + 1));
        }
    }
}
