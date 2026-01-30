package net.ggj2026.twofourone.controllers;

import com.badlogic.gdx.graphics.OrthographicCamera;

public interface GameController {

    void update();

    float getMoveAxisX();
    float getMoveAxisY();

    float getLookDir(float x, float y, OrthographicCamera camera);
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
