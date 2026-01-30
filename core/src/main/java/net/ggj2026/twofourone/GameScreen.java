package net.ggj2026.twofourone;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import net.ggj2026.twofourone.ecs.components.PositionComponent;
import net.ggj2026.twofourone.level.Level;

public class GameScreen extends ScreenAdapter {

    public SpriteBatch spriteBatch;
    public ShapeRenderer shapeRenderer;

    public OrthographicCamera camera;
    public Viewport viewport;

    public Level level;

    public float cameraScale = 12f / Main.WIDTH;

    public GameScreen() {
        this.spriteBatch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();

        this.level = new Level(this);

        this.camera = new OrthographicCamera(Main.WIDTH, Main.HEIGHT);
        this.viewport = new FitViewport(Main.WIDTH, Main.HEIGHT);
    }

    @Override
    public void render(float delta) {
        delta = Math.min(2f / Main.FPS, delta);
        this.level.update(delta);

        ScreenUtils.clear(0,0,0,1);
        this.viewport.apply();

        PositionComponent cameraPos = this.level.player.getComponent(PositionComponent.class);
        this.camera.position.set(cameraPos.x, cameraPos.y,0);
        this.camera.zoom = cameraScale;
        this.camera.update();

        this.spriteBatch.setProjectionMatrix(camera.combined);
        this.shapeRenderer.setProjectionMatrix(camera.combined);

        this.spriteBatch.begin();
        this.level.renderSprites(this.spriteBatch);
        this.spriteBatch.end();

        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        this.level.renderShapes(this.shapeRenderer);
        this.shapeRenderer.end();

        if (Main.DEBUG) {
            this.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            this.level.renderDebug(shapeRenderer);
            this.shapeRenderer.end();
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        this.viewport.update(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();

        this.spriteBatch.dispose();
        this.shapeRenderer.dispose();
    }
}
