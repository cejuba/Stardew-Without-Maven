package fr.cejuba.stardew.object.boots;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.main.Type;

public class Boots extends Entity {

    public Boots(GamePanel gamePanel) {
        super(gamePanel);
        type = Type.BOOTS;
        name = "Boots";
        down2 = setup("/object/boots", gamePanel.tileSize, gamePanel.tileSize);
        speedValue = 2;

        description = "[" + name + "]\n" + "It's a pair of boots.";

        price = 100;
    }
}
