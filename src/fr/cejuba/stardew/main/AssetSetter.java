package fr.cejuba.stardew.main;

import fr.cejuba.stardew.entity.OldMan;
import fr.cejuba.stardew.entity.monster.GreenSlime;
import fr.cejuba.stardew.object.BronzeCoin;
import fr.cejuba.stardew.object.consumable.RedPotion;
import fr.cejuba.stardew.object.shield.BlueShield;
import fr.cejuba.stardew.object.stats.Heart;
import fr.cejuba.stardew.object.stats.ManaCrystal;
import fr.cejuba.stardew.object.weapon.Axe;
import fr.cejuba.stardew.tile.interactive.DryTree;
import fr.cejuba.stardew.tile.interactive.InteractiveTile;

public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject() {
        int i = 0;
        gamePanel.object[i] = new BronzeCoin(gamePanel);
        gamePanel.object[i].worldX = gamePanel.tileSize * 25;
        gamePanel.object[i].worldY = gamePanel.tileSize * 23;
        i++;
        gamePanel.object[i] = new BronzeCoin(gamePanel);
        gamePanel.object[i].worldX = gamePanel.tileSize * 21;
        gamePanel.object[i].worldY = gamePanel.tileSize * 19;
        i++;
        gamePanel.object[i] = new BronzeCoin(gamePanel);
        gamePanel.object[i].worldX = gamePanel.tileSize * 26;
        gamePanel.object[i].worldY = gamePanel.tileSize * 21;
        i++;
        gamePanel.object[i] = new Axe(gamePanel);
        gamePanel.object[i].worldX = gamePanel.tileSize * 33;
        gamePanel.object[i].worldY = gamePanel.tileSize * 21;
        i++;
        gamePanel.object[i] = new BlueShield(gamePanel);
        gamePanel.object[i].worldX = gamePanel.tileSize * 35;
        gamePanel.object[i].worldY = gamePanel.tileSize * 21;
        i++;
        gamePanel.object[i] = new RedPotion(gamePanel);
        gamePanel.object[i].worldX = gamePanel.tileSize * 22;
        gamePanel.object[i].worldY = gamePanel.tileSize * 27;
        i++;
        gamePanel.object[i] = new Heart(gamePanel);
        gamePanel.object[i].worldX = gamePanel.tileSize * 22;
        gamePanel.object[i].worldY = gamePanel.tileSize * 29;
        i++;
        gamePanel.object[i] = new ManaCrystal(gamePanel);
        gamePanel.object[i].worldX = gamePanel.tileSize * 22;
        gamePanel.object[i].worldY = gamePanel.tileSize * 31;
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

    public void setInteractiveTile() {
        for (int i = 0; i < 7; i++) {
            gamePanel.interactiveTile[i] = new DryTree(gamePanel,gamePanel.tileSize * (27+i), gamePanel.tileSize *12);
        }
        gamePanel.interactiveTile[7] = new DryTree(gamePanel,gamePanel.tileSize * 21, gamePanel.tileSize * 22);
    }
}
