package fr.cejuba.stardew.object;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;

public class Object extends Entity {

    protected GamePanel gamePanel;
    public Object(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;
    }
}
