package fr.cejuba.stardew.object.shield;

import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.main.Type;

public class BlueShield extends Shield {
    public BlueShield(GamePanel gamePanel) {
        super(gamePanel);

        setType(Type.SHIELD);
        name = "Blue Shield";
        down2 = setup("/object/shield_blue", gamePanel.getTileSize(), gamePanel.getTileSize());
        defenseValue = 2;

        description = "[" + name + "]\n" + "What a strange\nshield .";

        price = 250;
    }
}
