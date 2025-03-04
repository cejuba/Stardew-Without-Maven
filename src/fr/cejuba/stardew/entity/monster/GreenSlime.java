package fr.cejuba.stardew.entity.monster;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;

import java.util.Random;

public class GreenSlime extends Entity {

    public GreenSlime(GamePanel gamePanel){
        super(gamePanel);

        type = 2;
        name = "Green Slime";
        speed = 1;
        maxLife = 4;
        life = maxLife;

        solidArea.setX(3);
        solidArea.setY(18);
        solidArea.setWidth(42);
        solidArea.setHeight(30);
        solidAreaDefaultX = (int) solidArea.getX();
        solidAreaDefaultY = (int) solidArea.getY();

        getImage();
    }

    public void getImage(){

        up0 = setup("/monster/greenslime_down_1");
        up1 = setup("/monster/greenslime_down_2");
        down0 = setup("/monster/greenslime_down_1");
        down1 = setup("/monster/greenslime_down_2");
        left0 = setup("/monster/greenslime_down_1");
        left1 = setup("/monster/greenslime_down_2");
        right0 = setup("/monster/greenslime_down_1");
        right1 = setup("/monster/greenslime_down_2");
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
}
