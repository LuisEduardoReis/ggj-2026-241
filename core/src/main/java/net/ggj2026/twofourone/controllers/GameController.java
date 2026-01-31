package net.ggj2026.twofourone.controllers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;

public interface GameController {

    void update();

    float getMoveAxisX();
    float getMoveAxisY();

    float getLookDir(float x, float y, Viewport viewport);
    float getLookNormal();

    boolean getShootingDown();
    boolean getShootingPressed();

    boolean getUseButtonDown();
    boolean getUseButtonPressed();

    boolean getRestartButtonDown();
    boolean getRestartButtonPressed();

    boolean getStartButtonDown();
    boolean getStartButtonPressed();

}
