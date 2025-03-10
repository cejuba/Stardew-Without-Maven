package fr.cejuba.stardew.object.weapon;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.main.Type;

public class Sword extends Entity {

    public Sword(GamePanel gamePanel) {
        super(gamePanel);

        type = Type.SWORD;
        name = "Sword";
        down2 = setup("/object/sword", gamePanel.tileSize, gamePanel.tileSize);
        attackValue = 1;

        attackArea.setWidth(36);
        attackArea.setHeight(36);

        description = "[" + name + "]\n" + "An old sword.";
        price = 50;

        knockBackPower = 2;
    }
}
