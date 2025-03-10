package fr.cejuba.stardew.main;

import fr.cejuba.stardew.entity.npc.Merchant;
import fr.cejuba.stardew.entity.npc.OldMan;
import fr.cejuba.stardew.entity.monster.GreenJunimo;
import fr.cejuba.stardew.object.Chest;
import fr.cejuba.stardew.object.Tent;
import fr.cejuba.stardew.object.money.BronzeCoin;
import fr.cejuba.stardew.object.Door;
import fr.cejuba.stardew.object.consumable.RedPotion;
import fr.cejuba.stardew.object.shield.BlueShield;
import fr.cejuba.stardew.object.stats.Heart;
import fr.cejuba.stardew.object.stats.ManaCrystal;
import fr.cejuba.stardew.object.weapon.Axe;
import fr.cejuba.stardew.tile.interactive.DryTree;

public class AssetSetter {

    private final GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject() {
        int mapNumber = 0;
        int i = 0;
        gamePanel.object[mapNumber][i] = new BronzeCoin(gamePanel);
        gamePanel.object[mapNumber][i].worldX = gamePanel.getTileSize() * 25;
        gamePanel.object[mapNumber][i].worldY = gamePanel.getTileSize() * 23;
        i++;
        gamePanel.object[mapNumber][i] = new BronzeCoin(gamePanel);
        gamePanel.object[mapNumber][i].worldX = gamePanel.getTileSize() * 21;
        gamePanel.object[mapNumber][i].worldY = gamePanel.getTileSize() * 19;
        i++;
        gamePanel.object[mapNumber][i] = new BronzeCoin(gamePanel);
        gamePanel.object[mapNumber][i].worldX = gamePanel.getTileSize() * 26;
        gamePanel.object[mapNumber][i].worldY = gamePanel.getTileSize() * 21;
        i++;
        gamePanel.object[mapNumber][i] = new Tent(gamePanel);
        gamePanel.object[mapNumber][i].worldX = gamePanel.getTileSize() * 19;
        gamePanel.object[mapNumber][i].worldY = gamePanel.getTileSize() * 20;
        i++;
        gamePanel.object[mapNumber][i] = new Axe(gamePanel);
        gamePanel.object[mapNumber][i].worldX = gamePanel.getTileSize() * 33;
        gamePanel.object[mapNumber][i].worldY = gamePanel.getTileSize() * 21;
        i++;
        gamePanel.object[mapNumber][i] = new BlueShield(gamePanel);
        gamePanel.object[mapNumber][i].worldX = gamePanel.getTileSize() * 35;
        gamePanel.object[mapNumber][i].worldY = gamePanel.getTileSize() * 21;
        i++;
        gamePanel.object[mapNumber][i] = new RedPotion(gamePanel);
        gamePanel.object[mapNumber][i].worldX = gamePanel.getTileSize() * 22;
        gamePanel.object[mapNumber][i].worldY = gamePanel.getTileSize() * 27;
        i++;
        gamePanel.object[mapNumber][i] = new Heart(gamePanel);
        gamePanel.object[mapNumber][i].worldX = gamePanel.getTileSize() * 22;
        gamePanel.object[mapNumber][i].worldY = gamePanel.getTileSize() * 29;
        i++;
        gamePanel.object[mapNumber][i] = new ManaCrystal(gamePanel);
        gamePanel.object[mapNumber][i].worldX = gamePanel.getTileSize() * 22;
        gamePanel.object[mapNumber][i].worldY = gamePanel.getTileSize() * 31;
        i++;
        gamePanel.object[mapNumber][i] = new Door(gamePanel);
        gamePanel.object[mapNumber][i].worldX = gamePanel.getTileSize() * 14;
        gamePanel.object[mapNumber][i].worldY = gamePanel.getTileSize() * 28;
        i++;
        gamePanel.object[mapNumber][i] = new Door(gamePanel);
        gamePanel.object[mapNumber][i].worldX = gamePanel.getTileSize() * 10;
        gamePanel.object[mapNumber][i].worldY = gamePanel.getTileSize() * 12;
        i++;
        gamePanel.object[mapNumber][i] = new Chest(gamePanel, new RedPotion(gamePanel));
        gamePanel.object[mapNumber][i].worldX = gamePanel.getTileSize() * 27;
        gamePanel.object[mapNumber][i].worldY = gamePanel.getTileSize() * 16;
        i++;
        gamePanel.object[mapNumber][i] = new RedPotion(gamePanel);
        gamePanel.object[mapNumber][i].worldX = gamePanel.getTileSize() * 21;
        gamePanel.object[mapNumber][i].worldY = gamePanel.getTileSize() * 20;
        i++;
        gamePanel.object[mapNumber][i] = new RedPotion(gamePanel);
        gamePanel.object[mapNumber][i].worldX = gamePanel.getTileSize() * 20;
        gamePanel.object[mapNumber][i].worldY = gamePanel.getTileSize() * 20;
        i++;
        gamePanel.object[mapNumber][i] = new RedPotion(gamePanel);
        gamePanel.object[mapNumber][i].worldX = gamePanel.getTileSize() * 17;
        gamePanel.object[mapNumber][i].worldY = gamePanel.getTileSize() * 21;
    }

    public void setNPC() {
        int mapNumber = 0;
        int i = 0;
        gamePanel.npc[mapNumber][i] = new OldMan(gamePanel);
        gamePanel.npc[mapNumber][i].worldX = gamePanel.getTileSize() * 21;
        gamePanel.npc[mapNumber][i].worldY = gamePanel.getTileSize() * 21;

        mapNumber = 1;
        i = 0;
        gamePanel.npc[mapNumber][i] = new Merchant(gamePanel);
        gamePanel.npc[mapNumber][i].worldX = gamePanel.getTileSize() * 12;
        gamePanel.npc[mapNumber][i].worldY = gamePanel.getTileSize() * 7;
    }

    public void setMonster() {
        int mapNumber = 0;

        int i = 0;
        gamePanel.monster[mapNumber][i] = new GreenJunimo(gamePanel);
        gamePanel.monster[mapNumber][i].worldX = gamePanel.getTileSize() * 33;
        gamePanel.monster[mapNumber][i].worldY = gamePanel.getTileSize() * 36;
        i++;
        gamePanel.monster[mapNumber][i] = new GreenJunimo(gamePanel);
        gamePanel.monster[mapNumber][i].worldX = gamePanel.getTileSize() * 23;
        gamePanel.monster[mapNumber][i].worldY = gamePanel.getTileSize() * 37;
        i++;
        gamePanel.monster[mapNumber][i] = new GreenJunimo(gamePanel);
        gamePanel.monster[mapNumber][i].worldX = gamePanel.getTileSize() * 24;
        gamePanel.monster[mapNumber][i].worldY = gamePanel.getTileSize() * 37;
        i++;
        gamePanel.monster[mapNumber][i] = new GreenJunimo(gamePanel);
        gamePanel.monster[mapNumber][i].worldX = gamePanel.getTileSize() * 34;
        gamePanel.monster[mapNumber][i].worldY = gamePanel.getTileSize() * 42;
        i++;
        gamePanel.monster[mapNumber][i] = new GreenJunimo(gamePanel);
        gamePanel.monster[mapNumber][i].worldX = gamePanel.getTileSize() * 38;
        gamePanel.monster[mapNumber][i].worldY = gamePanel.getTileSize() * 42;
    }

    public void setInteractiveTile() {
        int mapNumber = 0;
        for (int i = 0; i < 3; i++) {
            gamePanel.interactiveTile[mapNumber][i] = new DryTree(gamePanel,gamePanel.getTileSize() * (27+i), gamePanel.getTileSize() * 12);
            gamePanel.interactiveTile[mapNumber][i+3] = new DryTree(gamePanel,gamePanel.getTileSize() * (31+i), gamePanel.getTileSize() * 12);
        }
        gamePanel.interactiveTile[mapNumber][6] = new DryTree(gamePanel,gamePanel.getTileSize() * 21, gamePanel.getTileSize() * 22);
    }
}
