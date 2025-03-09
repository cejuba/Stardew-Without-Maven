package fr.cejuba.stardew.main;

import fr.cejuba.stardew.entity.npc.Merchant;
import fr.cejuba.stardew.entity.npc.OldMan;
import fr.cejuba.stardew.entity.monster.GreenJunimo;
import fr.cejuba.stardew.object.Chest;
import fr.cejuba.stardew.object.money.BronzeCoin;
import fr.cejuba.stardew.object.Door;
import fr.cejuba.stardew.object.consumable.RedPotion;
import fr.cejuba.stardew.object.shield.BlueShield;
import fr.cejuba.stardew.object.stats.Heart;
import fr.cejuba.stardew.object.stats.ManaCrystal;
import fr.cejuba.stardew.object.weapon.Axe;
import fr.cejuba.stardew.tile.interactive.DryTree;

public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject() {
        int mapNumber = 0;
        int i = 0;
        gamePanel.object[mapNumber][i] = new BronzeCoin(gamePanel);
        gamePanel.object[mapNumber][i].worldX = gamePanel.tileSize * 25;
        gamePanel.object[mapNumber][i].worldY = gamePanel.tileSize * 23;
        i++;
        gamePanel.object[mapNumber][i] = new BronzeCoin(gamePanel);
        gamePanel.object[mapNumber][i].worldX = gamePanel.tileSize * 21;
        gamePanel.object[mapNumber][i].worldY = gamePanel.tileSize * 19;
        i++;
        gamePanel.object[mapNumber][i] = new BronzeCoin(gamePanel);
        gamePanel.object[mapNumber][i].worldX = gamePanel.tileSize * 26;
        gamePanel.object[mapNumber][i].worldY = gamePanel.tileSize * 21;
        i++;
        gamePanel.object[mapNumber][i] = new Axe(gamePanel);
        gamePanel.object[mapNumber][i].worldX = gamePanel.tileSize * 33;
        gamePanel.object[mapNumber][i].worldY = gamePanel.tileSize * 21;
        i++;
        gamePanel.object[mapNumber][i] = new BlueShield(gamePanel);
        gamePanel.object[mapNumber][i].worldX = gamePanel.tileSize * 35;
        gamePanel.object[mapNumber][i].worldY = gamePanel.tileSize * 21;
        i++;
        gamePanel.object[mapNumber][i] = new RedPotion(gamePanel);
        gamePanel.object[mapNumber][i].worldX = gamePanel.tileSize * 22;
        gamePanel.object[mapNumber][i].worldY = gamePanel.tileSize * 27;
        i++;
        gamePanel.object[mapNumber][i] = new Heart(gamePanel);
        gamePanel.object[mapNumber][i].worldX = gamePanel.tileSize * 22;
        gamePanel.object[mapNumber][i].worldY = gamePanel.tileSize * 29;
        i++;
        gamePanel.object[mapNumber][i] = new ManaCrystal(gamePanel);
        gamePanel.object[mapNumber][i].worldX = gamePanel.tileSize * 22;
        gamePanel.object[mapNumber][i].worldY = gamePanel.tileSize * 31;
        i++;
        gamePanel.object[mapNumber][i] = new Door(gamePanel);
        gamePanel.object[mapNumber][i].worldX = gamePanel.tileSize * 14;
        gamePanel.object[mapNumber][i].worldY = gamePanel.tileSize * 28;
        i++;
        gamePanel.object[mapNumber][i] = new Door(gamePanel);
        gamePanel.object[mapNumber][i].worldX = gamePanel.tileSize * 10;
        gamePanel.object[mapNumber][i].worldY = gamePanel.tileSize * 12;
        i++;
        gamePanel.object[mapNumber][i] = new Chest(gamePanel, new RedPotion(gamePanel));
        gamePanel.object[mapNumber][i].worldX = gamePanel.tileSize * 27;
        gamePanel.object[mapNumber][i].worldY = gamePanel.tileSize * 16;
        i++;
        gamePanel.object[mapNumber][i] = new RedPotion(gamePanel);
        gamePanel.object[mapNumber][i].worldX = gamePanel.tileSize * 21;
        gamePanel.object[mapNumber][i].worldY = gamePanel.tileSize * 20;
        i++;
        gamePanel.object[mapNumber][i] = new RedPotion(gamePanel);
        gamePanel.object[mapNumber][i].worldX = gamePanel.tileSize * 20;
        gamePanel.object[mapNumber][i].worldY = gamePanel.tileSize * 20;
        i++;
        gamePanel.object[mapNumber][i] = new RedPotion(gamePanel);
        gamePanel.object[mapNumber][i].worldX = gamePanel.tileSize * 17;
        gamePanel.object[mapNumber][i].worldY = gamePanel.tileSize * 21;
    }

    public void setNPC() {
        int mapNumber = 0;
        int i = 0;
        gamePanel.npc[mapNumber][i] = new OldMan(gamePanel);
        gamePanel.npc[mapNumber][i].worldX = gamePanel.tileSize * 21;
        gamePanel.npc[mapNumber][i].worldY = gamePanel.tileSize * 21;

        mapNumber = 1;
        i = 0;
        gamePanel.npc[mapNumber][i] = new Merchant(gamePanel);
        gamePanel.npc[mapNumber][i].worldX = gamePanel.tileSize * 12;
        gamePanel.npc[mapNumber][i].worldY = gamePanel.tileSize * 7;
    }

    public void setMonster() {
        int mapNumber = 0;

        int i = 0;
        gamePanel.monster[mapNumber][i] = new GreenJunimo(gamePanel);
        gamePanel.monster[mapNumber][i].worldX = gamePanel.tileSize * 33;
        gamePanel.monster[mapNumber][i].worldY = gamePanel.tileSize * 36;
        i++;
        gamePanel.monster[mapNumber][i] = new GreenJunimo(gamePanel);
        gamePanel.monster[mapNumber][i].worldX = gamePanel.tileSize * 23;
        gamePanel.monster[mapNumber][i].worldY = gamePanel.tileSize * 37;
        i++;
        gamePanel.monster[mapNumber][i] = new GreenJunimo(gamePanel);
        gamePanel.monster[mapNumber][i].worldX = gamePanel.tileSize * 24;
        gamePanel.monster[mapNumber][i].worldY = gamePanel.tileSize * 37;
        i++;
        gamePanel.monster[mapNumber][i] = new GreenJunimo(gamePanel);
        gamePanel.monster[mapNumber][i].worldX = gamePanel.tileSize * 34;
        gamePanel.monster[mapNumber][i].worldY = gamePanel.tileSize * 42;
        i++;
        gamePanel.monster[mapNumber][i] = new GreenJunimo(gamePanel);
        gamePanel.monster[mapNumber][i].worldX = gamePanel.tileSize * 38;
        gamePanel.monster[mapNumber][i].worldY = gamePanel.tileSize * 42;
    }

    public void setInteractiveTile() {
        int mapNumber = 0;
        for (int i = 0; i < 3; i++) {
            gamePanel.interactiveTile[mapNumber][i] = new DryTree(gamePanel,gamePanel.tileSize * (27+i), gamePanel.tileSize * 12);
            gamePanel.interactiveTile[mapNumber][i+3] = new DryTree(gamePanel,gamePanel.tileSize * (31+i), gamePanel.tileSize * 12);
        }
        gamePanel.interactiveTile[mapNumber][6] = new DryTree(gamePanel,gamePanel.tileSize * 21, gamePanel.tileSize * 22);
    }
}
