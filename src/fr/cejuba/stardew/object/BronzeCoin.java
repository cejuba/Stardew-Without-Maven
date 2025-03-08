package fr.cejuba.stardew.object;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;

public class BronzeCoin extends Entity {

    GamePanel gamePanel;
    public BronzeCoin(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = type_pickUpOnly;
        name = "Bronze Coin";
        value = 1;
        down2 = setup("object/coin_bronze", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void use(Entity entity) {

        gamePanel.playSoundEffect(1);
        gamePanel.ui.addMessage("Gold + " + value);
        gamePanel.player.gold += value;
    }
}
