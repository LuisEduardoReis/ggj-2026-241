package net.ggj2026.twofourone.ecs.components;

import net.ggj2026.twofourone.Assets;
import net.ggj2026.twofourone.Util;
import net.ggj2026.twofourone.controllers.GameController;
import net.ggj2026.twofourone.ecs.entities.Entity;
import net.ggj2026.twofourone.gamelogic.MaskType;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerComponent implements Component {

    public GameController controller = null;

    public float healthRegen = 100 / 30f; // 100 health every 30 s
    public float maxHealth = 100;
    public float health = maxHealth;
    public float speed = 6;

    public float bulletTimer = 0;
    public float bulletDelay = 1 / 3f;

    public float maskTimer = 0;
    public float maskDelay = 5;
    public MaskType currentMask = null;

    public float lightningDamage = 200; // 200 per second
    public float lightningRange = 15;
    public ArrayList<Entity> lightningTargets = new ArrayList<>();

    public float explosionRange = 8;
    public float explosionDamage = 100;
    public float explosionForce = 30;

    public static List<Color> ColorAssignment = Arrays.asList(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.MAGENTA);

    public void damage(float value, Entity player) {
        player.level.gameScreen.cameraShake = 20;
        this.health = Util.stepTo(this.health, 0, value);
        Assets.hitHurt.play();
    }

    public void pickupMask(MaskType type, Entity player) {
        this.currentMask = type;
        this.maskTimer = this.maskDelay;
        SpriteComponent playerSprite = player.getComponent(SpriteComponent.class);
        playerSprite.sprites.set(1, MaskType.maskSprites.get(type));
        Assets.maskPickup.play();
    }
}
