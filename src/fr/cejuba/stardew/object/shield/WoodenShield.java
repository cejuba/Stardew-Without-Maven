package fr.cejuba.stardew.object.shield;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;

public class WoodenShield extends Entity {
    public WoodenShield(GamePanel gamePanel) {
        super(gamePanel);

        name = "Wooden Shield";
        down1 = setup("/object/shield_wood", gamePanel.tileSize, gamePanel.tileSize);
        defenseValue = 1;

        description = "[" + name + "]\n" + "Made by wood.";

    }
}
