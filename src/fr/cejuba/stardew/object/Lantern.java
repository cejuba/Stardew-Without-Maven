package fr.cejuba.stardew.object;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;

public class Lantern extends Entity {

    public Lantern(GamePanel gamePanel) {
        super(gamePanel);

        type = type_light;
        name = "Lantern";
        down2 = setup("object/lantern", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\nLight your way.";
        price = 200;
        lightRadius = 250;
    }
}
