package fr.cejuba.stardew.object.money;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.main.Type;

public class BronzeCoin extends Entity {

    GamePanel gamePanel;
    public BronzeCoin(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        setType(Type.PICKUPONLY);
        name = "Bronze Coin";
        value = 1;
        down2 = setup("object/coin_bronze", gamePanel.tileSize, gamePanel.tileSize);
    }

    public boolean use(Entity entity) {

        gamePanel.playSoundEffect(1);
        gamePanel.ui.addMessage("Gold + " + value);
        gamePanel.player.gold += value;
        return true;
    }
}
