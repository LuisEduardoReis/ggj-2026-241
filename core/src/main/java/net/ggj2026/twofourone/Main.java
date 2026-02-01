package net.ggj2026.twofourone;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import net.ggj2026.twofourone.sprites.SpriteAssets;

public class Main extends Game {
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;
	public static final int TILE_SIZE = 16;
	public static boolean DEBUG = false;

	public static final int FPS = 60;

	public GameScreen gameScreen;

	@Override
	public void create () {
		Assets.createAssets();
		SpriteAssets.initSprites();

		this.gameScreen = new GameScreen();
		this.setScreen(this.gameScreen);
	}

    @Override
    public void render() {
        super.render();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            boolean fullScreen = Gdx.graphics.isFullscreen();
            Graphics.DisplayMode currentMode = Gdx.graphics.getDisplayMode();
            if (fullScreen) {
                Gdx.graphics.setWindowedMode(Main.WIDTH/2, Main.HEIGHT/2);
            } else {
                Gdx.graphics.setFullscreenMode(currentMode);
            }
        }
    }
}
