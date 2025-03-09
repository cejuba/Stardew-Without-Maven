package fr.cejuba.stardew.object;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;

public class Door extends Entity {

    GamePanel gamePanel;

    public Door(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = type_obstacle;
        name = "Door";
        down2 = setup("object/door", gamePanel.tileSize, gamePanel.tileSize);
        collision = true;

        solidArea.setX(0);
        solidArea.setY(16);
        solidArea.setWidth(48);
        solidArea.setHeight(32);
        solidAreaDefaultX = (int) solidArea.getX();
        solidAreaDefaultY = (int) solidArea.getY();
    }
    public void interact(){
        gamePanel.gameState = gamePanel.dialogueState;
        gamePanel.ui.currentDialogue = "You need a key to open this.";
    }
}
