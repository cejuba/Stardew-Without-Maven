package fr.cejuba.stardew.object.shield;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;

public class RustyShield extends Entity {
    public RustyShield(GamePanel gamePanel) {
        super(gamePanel);

        type = type_shield;
        name = "Rusty Shield";
        down2 = setup("/object/rustyShield", gamePanel.tileSize, gamePanel.tileSize);
        defenseValue = 1;

        description = "[" + name + "]\n" + "A rusty shield.";
        price = 35;

    }
}
