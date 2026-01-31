package net.ggj2026.twofourone.ecs.components;

import net.ggj2026.twofourone.gamelogic.MaskType;

public class MaskPickupComponent implements Component {
    public MaskType type = MaskType.ONI;

    public float ttl = 5;
}
