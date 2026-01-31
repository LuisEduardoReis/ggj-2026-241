package net.ggj2026.twofourone.controllers;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.OrthographicCamera;
import net.ggj2026.twofourone.Util;

public class XBox360Controller implements GameController {

    Controller controller;
    boolean useButtonD, restartButtonD, shootButtonD, startButtonD;
    boolean useButtonP, restartButtonP, shootButtonP, startButtonP;

    public XBox360Controller(Controller c) {
        this.controller = c;
    }

    public void update() {
        boolean useButton = controller.getButton(XBox360Pad.BUTTON_A);
        boolean restartButton = controller.getButton(XBox360Pad.BUTTON_Y);
        boolean shootButton = Math.abs(controller.getAxis(XBox360Pad.AXIS_RIGHT_TRIGGER)) > 0.5;
        boolean startButton = controller.getButton(XBox360Pad.BUTTON_START);

        useButtonP = useButton && !useButtonD;
        useButtonD = useButton;

        restartButtonP = restartButton && !restartButtonD;
        restartButtonD = restartButton;

        shootButtonP = shootButton && !shootButtonD;
        shootButtonD = shootButton;

        startButtonP = startButton && !startButtonD;
        startButtonD = startButton;
    };

    @Override
    public float getMoveAxisX() { return controller.getAxis(XBox360Pad.AXIS_LEFT_X); }

    @Override
    public float getMoveAxisY() { return controller.getAxis(XBox360Pad.AXIS_LEFT_Y); }

    @Override
    public float getLookDir(float x, float y, OrthographicCamera camera) {
        float rax = controller.getAxis(XBox360Pad.AXIS_RIGHT_X);
        float ray = controller.getAxis(XBox360Pad.AXIS_RIGHT_Y);
        return - (float) Math.atan2(ray, rax);
    }

    @Override
    public float getLookNormal() {
        float rax = controller.getAxis(XBox360Pad.AXIS_RIGHT_X);
        float ray = controller.getAxis(XBox360Pad.AXIS_RIGHT_Y);
        return Util.pointDistance(0, 0, rax, ray);
    }


    @Override
    public boolean getUseButtonDown() { return useButtonD; }
    @Override
    public boolean getUseButtonPressed() { return useButtonP; }

    @Override
    public boolean getRestartButtonDown() { return restartButtonD; }
    @Override
    public boolean getRestartButtonPressed() { return restartButtonP; }

    @Override
    public boolean getShootingDown() { 	return shootButtonD; }
    @Override
    public boolean getShootingPressed() { return shootButtonP; }

    @Override
    public boolean getStartButtonDown() { return startButtonD; }
    @Override
    public boolean getStartButtonPressed() { return startButtonP; }


    @Override
    public String toString() {
        return controller.toString();
    }

}
