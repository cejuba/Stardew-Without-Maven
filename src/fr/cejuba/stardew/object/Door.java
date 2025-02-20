package fr.cejuba.stardew.object;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;

public class Door extends Entity {

    GamePanel gamePanel;

    public Door(GamePanel gamePanel) {
        super(gamePanel);

        name = "Door";
        down1 = setup("object/door");
        collision = true;

        solidArea.setX(0);
        solidArea.setY(16);
        solidArea.setWidth(48);
        solidArea.setHeight(32);
        solidAreaDefaultX = (int) solidArea.getX();
        solidAreaDefaultY = (int) solidArea.getY();
    }

}
