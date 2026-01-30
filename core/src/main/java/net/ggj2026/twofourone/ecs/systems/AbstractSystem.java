package net.ggj2026.twofourone.ecs.systems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import net.ggj2026.twofourone.ecs.components.Component;
import net.ggj2026.twofourone.ecs.entities.Entity;

import java.util.Collection;

public abstract class AbstractSystem {

    public final Collection<Class<? extends Component>> requiredComponents;

    protected AbstractSystem(Collection<Class<? extends Component>> requiredComponents) {
        this.requiredComponents = requiredComponents;
    }

    public final void visitUpdate(Entity entity, float delta) {
        if (entity.hasComponents(this.requiredComponents)) {
            this.processUpdate(entity, delta);
        }
    }
    protected void processUpdate(Entity entity, float delta) {}

    public final  void visitSpriteBatch(Entity entity, SpriteBatch spriteBatch) {
        if (entity.hasComponents(this.requiredComponents)) {
            this.processSpriteBatch(entity, spriteBatch);
        }
    }
    protected void processSpriteBatch(Entity entity, SpriteBatch spriteBatch) {}

    public final void visitShapeRenderer(Entity entity, ShapeRenderer shapeRenderer) {
        if (entity.hasComponents(this.requiredComponents)) {
            this.processShapeRenderer(entity, shapeRenderer);
        }
    }
    protected void processShapeRenderer(Entity entity, ShapeRenderer shapeRenderer) {}
}
