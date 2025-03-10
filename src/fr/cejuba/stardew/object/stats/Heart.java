package fr.cejuba.stardew.object.stats;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.main.Type;

public class Heart extends Entity {

    GamePanel gamePanel;
    public Heart(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        setType(Type.CONSUMABLE);
        name = "Heart";
        value = 2;
        down2 = setup("object/heart_full", gamePanel.tileSize, gamePanel.tileSize);
        image = setup("object/heart_full", gamePanel.tileSize, gamePanel.tileSize);
        image2 = setup("object/heart_half", gamePanel.tileSize, gamePanel.tileSize);
        image3 = setup("object/heart_blank", gamePanel.tileSize, gamePanel.tileSize);
    }

    public boolean use(Entity entity) {
        gamePanel.playSoundEffect(2);
        gamePanel.ui.addMessage("Life + " + value);
        entity.life += value;
        return true;
    }
}
