package net.ggj2026.twofourone;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import net.ggj2026.twofourone.controllers.GameController;
import net.ggj2026.twofourone.controllers.KeyboardMouseController;
import net.ggj2026.twofourone.controllers.XBox360Controller;
import net.ggj2026.twofourone.gamelogic.MaskType;
import net.ggj2026.twofourone.level.Level;

import java.util.ArrayList;

import static net.ggj2026.twofourone.Assets.font;

public class GameScreen extends ScreenAdapter {

    public SpriteBatch spriteBatch;
    public ShapeRenderer shapeRenderer;

    public OrthographicCamera camera;
    public Viewport viewport;

    ArrayList<GameController> controllers;
    public Level level;

    public float cameraScale = 40f / Main.WIDTH;
    public float cameraShake = 0;

    public float gameOverDelay = 2;
    public float gameOverFadeTimer = gameOverDelay;
    public float gameOverTitleTimer = gameOverDelay;

    public float titleDelay = 5;
    public float titleTimer = titleDelay;

    public Music currentMusic = null;

    public GameScreen() {
        this.spriteBatch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();

        this.level = new Level(this);

        this.camera = new OrthographicCamera(Main.WIDTH, Main.HEIGHT);
        this.viewport = new FitViewport(Main.WIDTH, Main.HEIGHT);
        this.viewport.setCamera(this.camera);

        this.controllers = new ArrayList<>();
        for(Controller c : Controllers.getControllers()) controllers.add(new XBox360Controller(c));
        if (this.controllers.isEmpty()) {
            controllers.add(new KeyboardMouseController());
        }

        for (int i = 0; i < this.controllers.size(); i++) {
            GameController controller = this.controllers.get(i);
            this.level.createPlayer(controller, i);
        }

        playMusic(Assets.defaultMusic);
    }

    @Override
    public void render(float delta) {
        delta = Math.min(1.5f / Main.FPS, delta);
        ScreenUtils.clear(0.2f,0.2f,0.2f,1);
        this.viewport.apply();

        float shakeX = Util.randomRange(-1, 1) * this.cameraShake * this.cameraScale;
        float shakeY = Util.randomRange(-1, 1) * this.cameraShake * this.cameraScale;
        this.cameraShake = Util.stepTo(this.cameraShake, 0, 100 * delta);
        this.camera.position.set(this.level.width/2f + shakeX, this.level.height/2f + shakeY,0);
        this.camera.zoom = cameraScale;
        this.camera.update();

        this.controllers.forEach(GameController::update);
        this.level.update(delta);

        this.spriteBatch.setProjectionMatrix(camera.combined);
        this.shapeRenderer.setProjectionMatrix(camera.combined);

        this.spriteBatch.begin();
        this.spriteBatch.setColor(Color.WHITE);
        this.level.renderSprites(this.spriteBatch);
        this.spriteBatch.end();

        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.level.renderShapes(this.shapeRenderer);
        this.shapeRenderer.end();

        // Overlay
        this.camera.position.set(Main.WIDTH/2f,Main.HEIGHT/2f,0);
        this.camera.zoom = 1;
        this.camera.update();
        this.spriteBatch.setProjectionMatrix(camera.combined);
        this.shapeRenderer.setProjectionMatrix(camera.combined);

        // Gameover
        this.spriteBatch.begin();
        if (this.level.gameOver) {
            this.gameOverFadeTimer = Util.stepTo(this.gameOverFadeTimer, 0, delta);
            if (this.gameOverFadeTimer == 0) {
                this.gameOverTitleTimer = Util.stepTo(this.gameOverTitleTimer, 0, delta);
            }

            this.spriteBatch.setColor(0,0,0, 0.8f * (1f - (this.gameOverFadeTimer / this.gameOverDelay)));
            this.spriteBatch.draw(Assets.fillTexture, 0, 0);

            font.getData().setScale(6);
            font.setColor(1, 1, 1, 1f - (this.gameOverTitleTimer / this.gameOverDelay));
            Util.drawTextCentered(this.spriteBatch, font, "Game Over", Main.WIDTH/2f,Main.HEIGHT/2f);
        }

        // Message
        if (this.level.message != null) {
            font.getData().setScale(4);
            font.setColor(1, 1, 1, Util.getFadeSequenceAlpha(this.level.messageTimer, this.level.messageDelay, 1));
            Util.drawTextCentered(this.spriteBatch, font, this.level.message, Main.WIDTH/2f,Main.HEIGHT*0.75f);
        }

        // Title
        if (this.titleTimer > 0) {
            this.titleTimer = Util.stepTo(this.titleTimer, 0, delta);
            float titleFadeAlpha = Util.getFadeSequenceAlpha(this.titleTimer, this.titleDelay, 1);

            this.spriteBatch.setColor(1, 1, 1, titleFadeAlpha);
            float radius = Main.WIDTH * 0.1f;
            float size = 100;
            for (int i = 0; i < MaskType.values().length; i++) {
                MaskType maskType = MaskType.values()[i];
                TextureRegion maskTexture = MaskType.maskSprites.get(maskType).getState("default").frames.get(0).textureRegion;
                float dir = (float) (this.level.t + i * 2*Math.PI / MaskType.values().length);
                float x = (float) (Main.WIDTH / 2f + radius * Math.cos(dir));
                float y = (float) (Main.HEIGHT / 2f + radius * Math.sin(dir));
                this.spriteBatch.draw(maskTexture, x - size/2, y - size/2, size, size);
            }

            this.spriteBatch.setColor(1, 1, 1, titleFadeAlpha);
            this.spriteBatch.draw(Assets.titleTexture, 0, 0);
        }
        this.spriteBatch.end();

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

    public void playMusic(Music music) {
        if (this.currentMusic != null) {
            this.currentMusic.stop();
        }
        music.setLooping(true);
        music.play();
        this.currentMusic = music;
    }
    public void stopMusic() {
        if (this.currentMusic != null) {
            this.currentMusic.stop();
        }
        this.currentMusic = null;
    }
}
