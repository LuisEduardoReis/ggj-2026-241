package net.ggj2026.twofourone.ecs.entities;

import net.ggj2026.twofourone.ecs.components.Component;
import net.ggj2026.twofourone.level.Level;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class Entity {
    private static int ID_COUNTER = 0;

    public Map<Class<? extends Component>, Component> components;

    public final int id;
    public Level level;
    public boolean remove;

    public Entity(Level level) {
        this.components = new HashMap<>();
        this.id = ID_COUNTER++;
        this.level = level;
        this.remove = false;
    }

    public Entity addComponent(Component component) {
        this.components.put(component.getClass(), component);
        return this;
    }

    public boolean hasComponents(Collection<Class<? extends Component>> componentClasses) {
        for (Class<? extends Component> componentClass : componentClasses) {
            if (!this.components.containsKey(componentClass)) {
                return false;
            }
        }
        return true;
    }

    public <T extends Component> T getComponent(Class<T> component) {
        //noinspection unchecked
        return (T) this.components.get(component);
    }
}
