package fr.cejuba.stardew.object.lighting;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.main.Type;

public class Lantern extends LightingItem {

    public Lantern(GamePanel gamePanel) {
        super(gamePanel);

        setType(Type.LIGHT);
        name = "Lantern";
        down2 = setup("object/lantern", gamePanel.getTileSize(), gamePanel.getTileSize());
        description = "[" + name + "]\nLight your way.";
        price = 200;
        lightRadius = 250;
    }
}
