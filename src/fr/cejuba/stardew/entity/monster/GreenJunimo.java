package fr.cejuba.stardew.entity.monster;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.object.BronzeCoin;
import fr.cejuba.stardew.object.projectile.Rock;
import fr.cejuba.stardew.object.stats.Heart;
import fr.cejuba.stardew.object.stats.ManaCrystal;

import java.util.Random;

public class GreenJunimo extends Entity {

    GamePanel gamePanel;
    public GreenJunimo(GamePanel gamePanel){
        super(gamePanel);

        this.gamePanel = gamePanel;

        type = type_monster;
        name = "Green Junimo";
        defaultSpeed = 1;
        speed = defaultSpeed;
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

        up1 = setup("/monster/junimo_down_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/monster/junimo_down_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/monster/junimo_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/monster/junimo_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/monster/junimo_down_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/monster/junimo_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/monster/junimo_down_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/monster/junimo_down_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    /*
    public void update(){
        super.update();

        int xDistance = Math.abs(worldX - gamePanel.player.worldX);
        int yDistance = Math.abs(worldY - gamePanel.player.worldY);
        int tileDistance = (int) Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2)) / gamePanel.tileSize;

        if(onPath && tileDistance < 5){
            int i = new Random().nextInt(100) + 1;
            if(i > 50){
                onPath = true;
            }
        }
        if(onPath && tileDistance > 20){
            onPath = false;
        }
    }
     */

    public void setAction() {

        if(onPath){
            int goalCol = 12;
            int goalRow = 9;

            // int goalCol = (gamePanel.player.worldX + gamePanel.player.solidArea.getX()) / gamePanel.tileSize;
            // int goalRow = (gamePanel.player.worldY + gamePanel.player.solidArea.getY()) / gamePanel.tileSize;

            searchPath(goalCol, goalRow);

            // Shot randomly a rock
            int i = new Random().nextInt(200) + 1;
            if (i > 197 && !projectile.alive && shotAvailableCounter == 30){
                projectile.set(worldX, worldY, direction, true, this);
                // gamePanel.projectileList.add(projectile);

                // Check vacancy
                for (int j = 0; j < gamePanel.projectile[1].length; j++) {
                    if (gamePanel.projectile[gamePanel.currentMap][j] == null) {
                        gamePanel.projectile[gamePanel.currentMap][j] = projectile;
                        break;
                    }
                }
                shotAvailableCounter = 0;
            }
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
            int j = new Random().nextInt(200) + 1;
            if (j > 100 && !projectile.alive && shotAvailableCounter == 30){
                projectile.set(worldX, worldY, direction, true, this);
                // gamePanel.projectileList.add(projectile);

                // Check vacancy
                for (int k = 0; k < gamePanel.projectile[1].length; k++) {
                    if (gamePanel.projectile[gamePanel.currentMap][k] == null) {
                        gamePanel.projectile[gamePanel.currentMap][k] = projectile;
                        break;
                    }
                }
                shotAvailableCounter = 0;
            }

        }
    }

    public void damageReaction() {
        // When the monster is hit it run away
        actionLockCounter = 0;
        direction = gamePanel.player.direction;
        // onPath = true : Comment the top line and uncomment this one if you want the monster to run on you
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
