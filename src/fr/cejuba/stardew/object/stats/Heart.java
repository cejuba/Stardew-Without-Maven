package fr.cejuba.stardew.object.stats;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.main.Type;

public class Heart extends Entity {

    private final GamePanel gamePanel;
    public Heart(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        setType(Type.CONSUMABLE);
        name = "Heart";
        value = 2;
        down2 = setup("object/heart_full", gamePanel.getTileSize(), gamePanel.getTileSize());
        image = setup("object/heart_full", gamePanel.getTileSize(), gamePanel.getTileSize());
        image2 = setup("object/heart_half", gamePanel.getTileSize(), gamePanel.getTileSize());
        image3 = setup("object/heart_blank", gamePanel.getTileSize(), gamePanel.getTileSize());
    }

    public boolean use(Entity entity) {
        gamePanel.playSoundEffect(2);
        gamePanel.ui.addMessage("Life + " + value);
        entity.life += value;
        return true;
    }
}
