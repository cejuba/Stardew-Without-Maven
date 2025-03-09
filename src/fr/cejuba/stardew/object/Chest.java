package fr.cejuba.stardew.object;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;

public class Chest extends Entity {

    GamePanel gamePanel;
    Entity loot;
    boolean opened = false;

    public Chest(GamePanel gamePanel, Entity loot) {
        super(gamePanel);
        this.gamePanel = gamePanel;
        this.loot = loot;

        type = type_obstacle;
        name = "Chest";
        image = setup("object/chest", gamePanel.tileSize, gamePanel.tileSize);
        image2 = setup("object/chest_opened", gamePanel.tileSize, gamePanel.tileSize);
        down1 = image;
        collision = true;

        solidArea.setX(4);
        solidArea.setY(16);
        solidArea.setWidth(40);
        solidArea.setHeight(32);
        solidAreaDefaultX = (int) solidArea.getX();
        solidAreaDefaultY = (int) solidArea.getY();
    }

    public void interact(){
        gamePanel.gameState = gamePanel.dialogueState;

        if(!opened){
            gamePanel.playSoundEffect(3);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("You found a " + loot.name + " in this chest.");

            if(gamePanel.player.inventory.size() == gamePanel.player.maxInventorySize){
                stringBuilder.append("\nBut you cannot carry any more.");
            } else {
                stringBuilder.append("\nYou obtained the " + loot.name + ".");
                gamePanel.player.inventory.add(loot);
                down1 = image2;
                opened = true;
            }
            gamePanel.ui.currentDialogue = stringBuilder.toString();
        }
        else{
            gamePanel.ui.currentDialogue = "This chest is empty.";
        }
    }
}
