package fr.cejuba.stardew.entity;

import fr.cejuba.stardew.main.GamePanel;

import java.util.Random;

public class OldMan extends Entity {

    public OldMan(GamePanel gamePanel) {
        super(gamePanel);

        type = 1;
        direction = "down";
        speed = 1;

        getImage();
        setDialogue();
    }

    public void getImage() {
        try {
            System.out.println("Loading player images...");

            up0 = setup("npc/oldman_up_1", gamePanel.tileSize, gamePanel.tileSize);
            up1 = setup("npc/oldman_up_2", gamePanel.tileSize, gamePanel.tileSize);
            down0 = setup("npc/oldman_down_1", gamePanel.tileSize, gamePanel.tileSize);
            down1 = setup("npc/oldman_down_2", gamePanel.tileSize, gamePanel.tileSize);
            right0 = setup("npc/oldman_right_1", gamePanel.tileSize, gamePanel.tileSize);
            right1 = setup("npc/oldman_right_2", gamePanel.tileSize, gamePanel.tileSize);
            left0 = setup("npc/oldman_left_1", gamePanel.tileSize, gamePanel.tileSize);
            left1 = setup("npc/oldman_left_2", gamePanel.tileSize, gamePanel.tileSize);

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

    @Override
    public void speak() {
        super.speak();
    }
}
