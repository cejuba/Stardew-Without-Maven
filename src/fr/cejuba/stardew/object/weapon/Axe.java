package fr.cejuba.stardew.object.weapon;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.main.Type;

public class Axe extends Entity {
    public Axe(GamePanel gamePanel) {
        super(gamePanel);

        type = Type.AXE;
        name = "Woodcutter's Axe";
        down2 = setup("/object/axe", gamePanel.tileSize, gamePanel.tileSize);
        attackValue = 2;

        attackArea.setWidth(30);
        attackArea.setHeight(30);

        description = "[" + name + "]\nCan cut some trees.";

        price = 75;

        knockBackPower = 10;
    }
}
