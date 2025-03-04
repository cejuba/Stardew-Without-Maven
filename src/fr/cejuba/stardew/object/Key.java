package fr.cejuba.stardew.object;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;

public class Key extends Entity {

    public Key(GamePanel gamePanel) {
        super(gamePanel);
        name = "Key";
        down1 = setup("/object/key");
    }
}
