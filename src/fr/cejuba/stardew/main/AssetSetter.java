package fr.cejuba.stardew.main;

import fr.cejuba.stardew.entity.OldMan;
import fr.cejuba.stardew.object.Door;

public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject() {
        gamePanel.objects[0] = new Door(gamePanel);
        gamePanel.objects[0].worldX = gamePanel.tileSize * 21;
        gamePanel.objects[0].worldY = gamePanel.tileSize * 22;

        gamePanel.objects[1] = new Door(gamePanel);
        gamePanel.objects[1].worldX = gamePanel.tileSize * 23;
        gamePanel.objects[1].worldY = gamePanel.tileSize * 25;
    }

    public void setNPC() {
        gamePanel.npc[0] = new OldMan(gamePanel);
        gamePanel.npc[0].worldX = gamePanel.tileSize * 21;
        gamePanel.npc[0].worldY = gamePanel.tileSize * 21;
    }
}
