package net.ggj2026.twofourone.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import net.ggj2026.twofourone.Main;
import net.ggj2026.twofourone.Util;

public class KeyboardMouseController implements GameController {

    boolean lmbD, rmbD;
    boolean lmbP, rmbP;

    @Override
    public void update() {
        boolean lmb = Gdx.input.isButtonPressed(Input.Buttons.LEFT);
        boolean rmb = Gdx.input.isButtonPressed(Input.Buttons.RIGHT);

        lmbP = lmb && !lmbD;
        lmbD = lmb;

        rmbP = rmb && !rmbD;
        rmbD = rmb;
    }

    @Override
    public float getMoveAxisX() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) return -1;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) return 1;
        return 0;
    }


    @Override
    public float getMoveAxisY() {
        if (Gdx.input.isKeyPressed(Input.Keys.S)) return 1;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) return -1;
        return 0;
    }

    Vector3 lookVector = new Vector3();
    @Override
    public float getLookDir(float x, float y, OrthographicCamera camera) {
        Vector3 unprojectedCoords = camera.unproject(lookVector.set(Gdx.input.getX(), Gdx.input.getY(), 0));
        return Util.pointDirection(x, y, unprojectedCoords.x, unprojectedCoords.y);
    }

    @Override
    public float getLookNormal() {
        return 1;
    }

    @Override
    public boolean getShootingDown() { return lmbD; }
    @Override
    public boolean getShootingPressed() { return lmbP; }

    @Override
    public boolean getUseButtonDown() { return rmbD; }
    @Override
    public boolean getUseButtonPressed() { return rmbP; }

    @Override
    public boolean getRestartButtonDown() {
        return Gdx.input.isKeyPressed(Input.Keys.R);
    }

    @Override
    public boolean getRestartButtonPressed() {
        return Gdx.input.isKeyJustPressed(Input.Keys.R);
    }

    @Override
    public boolean getStartButtonDown() {
        return Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isKeyPressed(Input.Keys.ENTER);
    }

    @Override
    public boolean getStartButtonPressed() {
        return Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER);
    }

    @Override
    public String toString() {
        return "Keyboard and Mouse Controller";
    }

}
