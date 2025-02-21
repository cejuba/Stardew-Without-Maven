package fr.cejuba.stardew.entity;

import fr.cejuba.stardew.main.GamePanel;

import java.util.Random;


public class OldMan extends Entity {

    public OldMan(GamePanel gamePanel) {
        super(gamePanel);

        direction = "down";
        speed = 1;

        getImage();
        setDialogue();
    }

    public void getImage() {
        try {
            System.out.println("Loading player images...");

            up0 = setup("npc/oldman_up_1");
            up1 = setup("npc/oldman_up_2");
            down0 = setup("npc/oldman_down_1");
            down1 = setup("npc/oldman_down_2");
            right0 = setup("npc/oldman_right_1");
            right1 = setup("npc/oldman_right_2");
            left0 = setup("npc/oldman_left_1");
            left1 = setup("npc/oldman_left_2");

            System.out.println("Player images loaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDialogue(){

        dialogues[0] = "Hello there!";
        dialogues[1] = "I don't know what to do...I don't know what \n to do...I don't know what to do...I don't know what to do...I don't know what to do...";

    }

    public void setAction(){

        actionLockCounter++;
        if (actionLockCounter == 100) {
            Random random = new Random();
            int i = random.nextInt(100)+1; // Pick a number from 1 to 100

            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75) {
                direction = "right";
            }

            actionLockCounter = 0;
        }
    }

    public void speak(){
        super.speak();
    }
}
