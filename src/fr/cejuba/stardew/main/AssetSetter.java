package fr.cejuba.stardew.main;

import fr.cejuba.stardew.entity.OldMan;
import fr.cejuba.stardew.entity.monster.GreenSlime;
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
    }

    public void setNPC() {
        gamePanel.npc[0] = new OldMan(gamePanel);
        gamePanel.npc[0].worldX = gamePanel.tileSize * 21;
        gamePanel.npc[0].worldY = gamePanel.tileSize * 21;
    }

    public void setMonster() {
        int i = 0;
        gamePanel.monster[i] = new GreenSlime(gamePanel);
        gamePanel.monster[i].worldX = gamePanel.tileSize * 33;
        gamePanel.monster[i].worldY = gamePanel.tileSize * 36;
        i++;
        gamePanel.monster[i] = new GreenSlime(gamePanel);
        gamePanel.monster[i].worldX = gamePanel.tileSize * 23;
        gamePanel.monster[i].worldY = gamePanel.tileSize * 37;
        i++;
        gamePanel.monster[i] = new GreenSlime(gamePanel);
        gamePanel.monster[i].worldX = gamePanel.tileSize * 24;
        gamePanel.monster[i].worldY = gamePanel.tileSize * 37;
        i++;
        gamePanel.monster[i] = new GreenSlime(gamePanel);
        gamePanel.monster[i].worldX = gamePanel.tileSize * 34;
        gamePanel.monster[i].worldY = gamePanel.tileSize * 42;
        i++;
        gamePanel.monster[i] = new GreenSlime(gamePanel);
        gamePanel.monster[i].worldX = gamePanel.tileSize * 38;
        gamePanel.monster[i].worldY = gamePanel.tileSize * 42;
    }
}
