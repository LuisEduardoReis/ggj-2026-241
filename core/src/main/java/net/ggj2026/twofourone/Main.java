package net.ggj2026.twofourone;

import com.badlogic.gdx.Game;
import net.ggj2026.twofourone.sprites.SpriteAssets;

public class Main extends Game {
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;
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
}
