package fr.cejuba.stardew.entity.npc;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.main.GameState;
import fr.cejuba.stardew.main.Type;
import fr.cejuba.stardew.object.Lantern;
import fr.cejuba.stardew.object.Tent;
import fr.cejuba.stardew.object.boots.Boots;
import fr.cejuba.stardew.object.Key;
import fr.cejuba.stardew.object.consumable.RedPotion;
import fr.cejuba.stardew.object.shield.BlueShield;
import fr.cejuba.stardew.object.shield.RustyShield;
import fr.cejuba.stardew.object.weapon.Axe;
import fr.cejuba.stardew.object.weapon.Sword;

public class Merchant extends Entity {

    GamePanel gamePanel;
    public Merchant(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = Type.NPC;
        direction = "down";
        speed = 1;

        getImage();
        setDialogue();
        setItems();
    }

    public void getImage() {
        try {
            System.out.println("Loading player images...");

            up1 = setup("npc/merchant", gamePanel.tileSize, gamePanel.tileSize);
            up2 = setup("npc/merchant", gamePanel.tileSize, gamePanel.tileSize);
            down1 = setup("npc/merchant", gamePanel.tileSize, gamePanel.tileSize);
            down2 = setup("npc/merchant", gamePanel.tileSize, gamePanel.tileSize);
            right1 = setup("npc/merchant", gamePanel.tileSize, gamePanel.tileSize);
            right2 = setup("npc/merchant", gamePanel.tileSize, gamePanel.tileSize);
            left1 = setup("npc/merchant", gamePanel.tileSize, gamePanel.tileSize);
            left2 = setup("npc/merchant", gamePanel.tileSize, gamePanel.tileSize);

            System.out.println("Player images loaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDialogue() {
        dialogues[0] = "He he, so you found me ! I have some good stuff for you. \n" +
                "Do you want to trade ?";
    }

    public void setItems() {

        inventory.add(new RedPotion(gamePanel));
        inventory.add(new Key(gamePanel));
        inventory.add(new Sword(gamePanel));
        inventory.add(new Axe(gamePanel));
        inventory.add(new RustyShield(gamePanel));
        inventory.add(new BlueShield(gamePanel));
        inventory.add(new Boots(gamePanel));
        inventory.add(new Lantern(gamePanel));
        inventory.add(new Tent(gamePanel));
    }

    @Override
    public void speak(){
        super.speak();
        gamePanel.gameState = GameState.TRADE;
        gamePanel.ui.npc = this;
    }
}
