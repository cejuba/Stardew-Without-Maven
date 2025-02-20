package fr.cejuba.stardew.object;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;

public class Key extends Entity {
    GamePanel gamePanel;

    public Key(GamePanel gamePanel) {
        super(gamePanel);

        this.gamePanel = gamePanel;
        name = "Key";
        down1 = setup("/object/key");
    }

}
