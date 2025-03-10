package fr.cejuba.stardew.entity.npc;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.main.Type;

import java.util.Random;

public class OldMan extends Entity {

    GamePanel gamePanel;
    public OldMan(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        setType(Type.NPC);
        direction = "down";
        speed = 2;

        getImage();
        setDialogue();
    }

    public void getImage() {
        try {
            System.out.println("Loading player images...");

            up1 = setup("npc/oldman_up_1", gamePanel.getTileSize(), gamePanel.getTileSize());
            up2 = setup("npc/oldman_up_2", gamePanel.getTileSize(), gamePanel.getTileSize());
            down1 = setup("npc/oldman_down_1", gamePanel.getTileSize(), gamePanel.getTileSize());
            down2 = setup("npc/oldman_down_2", gamePanel.getTileSize(), gamePanel.getTileSize());
            right1 = setup("npc/oldman_right_1", gamePanel.getTileSize(), gamePanel.getTileSize());
            right2 = setup("npc/oldman_right_2", gamePanel.getTileSize(), gamePanel.getTileSize());
            left1 = setup("npc/oldman_left_1", gamePanel.getTileSize(), gamePanel.getTileSize());
            left2 = setup("npc/oldman_left_2", gamePanel.getTileSize(), gamePanel.getTileSize());

            System.out.println("Player images loaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDialogue() {
        dialogues[0] = "Hello there!";
        dialogues[1] = "I don't know what to do...I don't know what \n to do...I don't know what to do...I don't know what to do...I don't know what to do...";
    }

    public void setAction() {

        if(onPath){
            int goalCol = 12;
            int goalRow = 9;

            // int goalCol = (gamePanel.player.worldX + gamePanel.player.solidArea.getX()) / gamePanel.tileSize;
            // int goalRow = (gamePanel.player.worldY + gamePanel.player.solidArea.getY()) / gamePanel.tileSize;

            searchPath(goalCol, goalRow);
        }
        else {
            actionLockCounter++;
            if (actionLockCounter == 100) {
                Random random = new Random();
                int i = random.nextInt(100) + 1; // Pick a number from 1 to 100

                if (i <= 25) {
                    direction = "up";
                } else if (i <= 50) {
                    direction = "down";
                } else if (i <= 75) {
                    direction = "left";
                } else {
                    direction = "right";
                }

                actionLockCounter = 0;
            }
        }
    }

    @Override
    public void speak() {
        super.speak();

        onPath = true;
    }
}
