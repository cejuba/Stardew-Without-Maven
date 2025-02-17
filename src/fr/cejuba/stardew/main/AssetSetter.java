package fr.cejuba.stardew.main;

import fr.cejuba.stardew.entity.npc.OldMan;
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

    }

    public void setNPC() {

        gamePanel.npc[0] = new OldMan(gamePanel);
        gamePanel.npc[0].worldX = gamePanel.tileSize*21;
        gamePanel.npc[0].worldY = gamePanel.tileSize*21;

    }


}
