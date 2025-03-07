package fr.cejuba.stardew.object.shield;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;

public class BlueShield extends Entity {
    public BlueShield(GamePanel gamePanel) {
        super(gamePanel);

        type = type_shield;
        name = "Blue Shield";
        down1 = setup("/object/shield_blue", gamePanel.tileSize, gamePanel.tileSize);
        defenseValue = 2;

        description = "[" + name + "]\n" + "What a strange\nshield .";

    }
}
