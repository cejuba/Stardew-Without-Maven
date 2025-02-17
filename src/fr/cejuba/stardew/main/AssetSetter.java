package fr.cejuba.stardew.main;

import fr.cejuba.stardew.object.Boots;
import fr.cejuba.stardew.object.Chest;
import fr.cejuba.stardew.object.Door;
import fr.cejuba.stardew.object.Key;

public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject() {
        gamePanel.superObject[0] = new Key(gamePanel);
        gamePanel.superObject[0].worldX = 23 * gamePanel.tileSize;
        gamePanel.superObject[0].worldY = 7 * gamePanel.tileSize;

        gamePanel.superObject[1] = new Key(gamePanel);
        gamePanel.superObject[1].worldX = 23 * gamePanel.tileSize;
        gamePanel.superObject[1].worldY = 40 * gamePanel.tileSize;

        gamePanel.superObject[2] = new Key(gamePanel);
        gamePanel.superObject[2].worldX = 37 * gamePanel.tileSize;
        gamePanel.superObject[2].worldY = 7 * gamePanel.tileSize;

        gamePanel.superObject[3] = new Door(gamePanel);
        gamePanel.superObject[3].worldX = 10 * gamePanel.tileSize;
        gamePanel.superObject[3].worldY = 11 * gamePanel.tileSize;

        gamePanel.superObject[4] = new Door(gamePanel);
        gamePanel.superObject[4].worldX = 8 * gamePanel.tileSize;
        gamePanel.superObject[4].worldY = 28 * gamePanel.tileSize;

        gamePanel.superObject[5] = new Door(gamePanel);
        gamePanel.superObject[5].worldX = 12 * gamePanel.tileSize;
        gamePanel.superObject[5].worldY = 22 * gamePanel.tileSize;

        gamePanel.superObject[6] = new Chest(gamePanel);
        gamePanel.superObject[6].worldX = 10 * gamePanel.tileSize;
        gamePanel.superObject[6].worldY = 7 * gamePanel.tileSize;

        gamePanel.superObject[7] = new Boots(gamePanel);
        gamePanel.superObject[7].worldX = 37 * gamePanel.tileSize;
        gamePanel.superObject[7].worldY = 42 * gamePanel.tileSize;
    }


}
