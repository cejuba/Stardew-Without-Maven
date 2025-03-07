package fr.cejuba.stardew.object;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;

public class Boots extends Entity {

    public Boots(GamePanel gamePanel) {
        super(gamePanel);
        name = "Boots";
        down1 = setup("/object/boots", gamePanel.tileSize, gamePanel.tileSize);

        description = "[" + name + "]\n" + "It's a pair of boots.";

        price = 100;
    }
}
