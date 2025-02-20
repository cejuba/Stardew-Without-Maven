package fr.cejuba.stardew.object;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;


public class Chest extends Entity {

    GamePanel gamePanel;

    public Chest(GamePanel gamePanel) {
        super(gamePanel);

        name = "Chest";
        image = setup("/object/chest");
    }

}
