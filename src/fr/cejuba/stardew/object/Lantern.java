package fr.cejuba.stardew.object;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.main.Type;

public class Lantern extends Entity {

    public Lantern(GamePanel gamePanel) {
        super(gamePanel);

        setType(Type.LIGHT);
        name = "Lantern";
        down2 = setup("object/lantern", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\nLight your way.";
        price = 200;
        lightRadius = 250;
    }
}
