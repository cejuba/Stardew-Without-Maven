package fr.cejuba.stardew.object.weapon;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;

public class Sword extends Entity {

    public Sword(GamePanel gamePanel) {
        super(gamePanel);

        name = "Sword";
        down1 = setup("/object/sword", gamePanel.tileSize, gamePanel.tileSize);
        attackValue = 1;

        description = "[" + name + "]\n" + "An old sword.";
    }
}
