package fr.cejuba.stardew.entity.monster;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.object.BronzeCoin;
import fr.cejuba.stardew.object.projectile.Rock;
import fr.cejuba.stardew.object.stats.Heart;
import fr.cejuba.stardew.object.stats.ManaCrystal;

import java.util.Random;

public class GreenSlime extends Entity {

    GamePanel gamePanel;
    public GreenSlime(GamePanel gamePanel){
        super(gamePanel);

        this.gamePanel = gamePanel;

        type = type_monster;
        name = "Green Slime";
        speed = 1;
        maxLife = 4;
        life = maxLife;
        attack = 5;
        defense = 0;
        experience = 2;
        projectile = new Rock(gamePanel);

        solidArea.setX(3);
        solidArea.setY(18);
        solidArea.setWidth(42);
        solidArea.setHeight(30);
        solidAreaDefaultX = (int) solidArea.getX();
        solidAreaDefaultY = (int) solidArea.getY();

        getImage();
    }

    public void getImage(){

        up0 = setup("/monster/greenslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        up1 = setup("/monster/greenslime_down_2", gamePanel.tileSize, gamePanel.tileSize);
        down0 = setup("/monster/greenslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/monster/greenslime_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left0 = setup("/monster/greenslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/monster/greenslime_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right0 = setup("/monster/greenslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/monster/greenslime_down_2", gamePanel.tileSize, gamePanel.tileSize);
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
        // Shot randomly a rock
        int i = new Random().nextInt(100) + 1;
        if (i > 99 && !projectile.alive && shotAvalaibleCounter == 30){
            projectile.set(worldX, worldY, direction, true, this);
            gamePanel.projectileList.add(projectile);
            shotAvalaibleCounter = 0;
        }
    }

    public void damageReaction() {
        // When the monster is hit it run away
        actionLockCounter = 0;
        direction = gamePanel.player.direction;
    }

    public void checkDrop(){
        int i = new Random().nextInt(100) + 1;
        if(i < 50){
            dropItem(new BronzeCoin(gamePanel));
        }
        else if(i < 75){
            dropItem(new Heart(gamePanel));
        }
        else{
            dropItem(new ManaCrystal(gamePanel));
        }
    }
}
