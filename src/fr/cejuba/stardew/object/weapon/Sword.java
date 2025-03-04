package fr.cejuba.stardew.object.weapon;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;

public class Sword extends Entity {

    public Sword(GamePanel gamePanel) {
        super(gamePanel);

        name = "Sword";
        down1 = setup("/objects/sword", gamePanel.tileSize, gamePanel.tileSize);
        attackValue = 1;

    }
}
