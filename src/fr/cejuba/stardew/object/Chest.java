package fr.cejuba.stardew.object;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;

public class Chest extends Entity {

    public Chest(GamePanel gamePanel) {
        super(gamePanel);
        name = "Chest";
        image = setup("/object/chest", gamePanel.tileSize, gamePanel.tileSize);
    }
}
