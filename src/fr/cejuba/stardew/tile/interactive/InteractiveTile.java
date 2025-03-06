package fr.cejuba.stardew.tile.interactive;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;

public class InteractiveTile extends Entity {
    GamePanel gamePanel;
    public boolean destructible = false;

    public InteractiveTile(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;
    }

    public boolean isCorrectItem(Entity entity) {
        return false;
    }
    public void update(){

    }
}
