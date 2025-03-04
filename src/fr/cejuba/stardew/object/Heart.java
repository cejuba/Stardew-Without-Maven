package fr.cejuba.stardew.object;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;

public class Heart extends Entity {

    public Heart(GamePanel gamePanel) {
        super(gamePanel);

        name = "Heart";
        image = setup("object/heart_full", gamePanel.tileSize, gamePanel.tileSize);
        image2 = setup("object/heart_half", gamePanel.tileSize, gamePanel.tileSize);
        image3 = setup("object/heart_blank", gamePanel.tileSize, gamePanel.tileSize);
    }
}
