package fr.cejuba.stardew.entity;

import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.main.KeyHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class Player extends Entity {

    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;
    public boolean attackCanceled = false;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        super(gamePanel); // Call the constructor of the parent class

        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);

        solidArea = new Rectangle(8, 16, 32, 32);
        solidAreaDefaultX = (int) solidArea.getX();
        solidAreaDefaultY = (int) solidArea.getY();

        attackArea.setWidth(36);
        attackArea.setHeight(36);

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }

    public void setKeyHandler(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
    }

    public void setDefaultValues() {
        worldX = gamePanel.tileSize * 23;
        worldY = gamePanel.tileSize * 21;
        speed = 4;
        direction = "up";

        maxLife = 6;
        life = 3;
    }

    public void getPlayerImage() {
        try {
            System.out.println("Loading player images");

            up0 = setup("player/walking/Back_0", gamePanel.tileSize, gamePanel.tileSize);
            up1 = setup("player/walking/Back_1", gamePanel.tileSize, gamePanel.tileSize);
            down0 = setup("player/walking/Face_0", gamePanel.tileSize, gamePanel.tileSize);
            down1 = setup("player/walking/Face_1", gamePanel.tileSize, gamePanel.tileSize);
            right0 = setup("player/walking/Right_0", gamePanel.tileSize, gamePanel.tileSize);
            right1 = setup("player/walking/Right_1", gamePanel.tileSize, gamePanel.tileSize);
            left0 = setup("player/walking/Right_0", gamePanel.tileSize, gamePanel.tileSize);
            left1 = setup("player/walking/Right_0", gamePanel.tileSize, gamePanel.tileSize);

            System.out.println("Player images loaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getPlayerAttackImage() {
        try {
            System.out.println("Loading player attack images");

            attackUp0 = setup("player/attacking/boy_attack_up_1", gamePanel.tileSize, gamePanel.tileSize*2);
            attackUp1 = setup("player/attacking/boy_attack_up_2", gamePanel.tileSize, gamePanel.tileSize*2);
            attackDown0 = setup("player/attacking/boy_attack_down_1", gamePanel.tileSize, gamePanel.tileSize*2);
            attackDown1 = setup("player/attacking/boy_attack_down_2", gamePanel.tileSize, gamePanel.tileSize*2);
            attackRight0 = setup("player/attacking/boy_attack_right_1", gamePanel.tileSize*2, gamePanel.tileSize);
            attackRight1 = setup("player/attacking/boy_attack_right_2", gamePanel.tileSize*2, gamePanel.tileSize);
            attackLeft0 = setup("player/attacking/boy_attack_left_1", gamePanel.tileSize*2, gamePanel.tileSize);
            attackLeft1 = setup("player/attacking/boy_attack_left_2", gamePanel.tileSize*2, gamePanel.tileSize);

            System.out.println("Player attack images loaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void update() {

        if(attacking){
            attacking();
        }
        else if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed || keyHandler.enterPressed) {
            if (keyHandler.upPressed) {
                direction = "up";
            } else if (keyHandler.downPressed) {
                direction = "down";
            } else if (keyHandler.leftPressed) {
                direction = "left";
            } else if (keyHandler.rightPressed) {
                direction = "right";
            }

            collisionActivated = false;
            gamePanel.collisionChecker.checkTile(this);

            // Object Collision
            int objectIndex = gamePanel.collisionChecker.checkObject(this, true);
            pickUpObject(objectIndex);

            // NPC Collision
            int npcIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);
            interactNPC(npcIndex);

            // Monster Collision
            int monsterIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.monster);
            contactMonster(monsterIndex);

            // Event checker
            gamePanel.eventHandler.checkEvent();

            if (!collisionActivated && !keyHandler.enterPressed) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            if(keyHandler.enterPressed && !attackCanceled){
                gamePanel.playSoundEffect(7);
                attacking = true;
                spriteCounter = 0;
            }

            attackCanceled = false;
            gamePanel.keyHandler.enterPressed = false;

            spriteCounter++;
            if (spriteCounter > 10) {
                spriteNumber = (spriteNumber == 0) ? 1 : 0;
                spriteCounter = 0;
            }
        }

        // Invincibility
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void attacking(){
        spriteCounter++;
        if (spriteCounter<=5){
            spriteNumber = 1;
        }
        if (5 < spriteCounter && spriteCounter <= 25){
            spriteNumber = 2;

            // Saving the current position of the player
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = (int) solidArea.getWidth();
            int solidAreaHeight = (int) solidArea.getHeight();

            // Adjusting the attack area
            switch(direction){
                case "up" -> worldY -= (int) attackArea.getHeight();
                case "down" -> worldY += (int) attackArea.getHeight();
                case "left" -> worldX -= (int) attackArea.getWidth();
                case "right" -> worldX += (int) attackArea.getWidth();
            }

            solidArea.setWidth(attackArea.getWidth());
            solidArea.setHeight(attackArea.getHeight());

            int monsterIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.monster);
            damageMonster(monsterIndex);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.setWidth(solidAreaWidth);
            solidArea.setHeight(solidAreaHeight);

        }
        if (spriteCounter > 25){
            spriteNumber = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }
    public void pickUpObject(int index) {
        if (index != 999) {
            // TBD
        }
    }

    public void interactNPC(int index) {
        if(gamePanel.keyHandler.enterPressed){
            if (index != 999) {
                attackCanceled = true;
                gamePanel.gameState = gamePanel.dialogueState;
                gamePanel.npc[index].speak();
            }
        }
    }

    public void contactMonster(int index) {
        if (index != 999) {
            if(!invincible){
                gamePanel.playSoundEffect(6);
                life -= 1;
                invincible = true;
            }
        }
    }

    public void damageMonster(int index) {
        if (index != 999) {
            System.out.println("Monster hit");
            if(!gamePanel.monster[index].invincible){
                gamePanel.playSoundEffect(5);
                gamePanel.monster[index].life -= 1;
                gamePanel.monster[index].invincible = true;
                gamePanel.monster[index].damageReaction();

                if(gamePanel.monster[index].life == 0){
                    gamePanel.monster[index].dying = true;
                }
            }
        }
    }

    public void draw(GraphicsContext graphicsContext) {
        Image image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up" :
                if(!attacking){
                    image = (spriteNumber == 0) ? up0 : up1;
                }
                else{
                    tempScreenY -= gamePanel.tileSize;
                    image = (spriteNumber == 0) ? attackUp0 : attackUp1;
                }
                break;
            case "down" :
                if(!attacking){
                    image = (spriteNumber == 0) ? down0 : down1;
                }
                else{
                    image = (spriteNumber == 0) ? attackDown0 : attackDown1;
                }
                break;
            case "left" :
                if(!attacking){
                    image = (spriteNumber == 0) ? left0 : left1;
                }
                else{
                    tempScreenX -= gamePanel.tileSize;
                    image = (spriteNumber == 0) ? attackLeft0 : attackLeft1;
                }
                break;
            case "right" :
                if(!attacking){
                    image = (spriteNumber == 0) ? right0 : right1;
                }
                else{
                    image = (spriteNumber == 0) ? attackRight0 : attackRight1;
                }
                break;
        }

        if(invincible){
            graphicsContext.setGlobalAlpha(0.2);
        }

        if (image != null) {
            graphicsContext.drawImage(image, tempScreenX, tempScreenY);
        }

        graphicsContext.setGlobalAlpha(1.0);

        /* Draw player's invicibility : maybe can do with KeyHandler
        graphicsContext.setFont(new Font("Arial", 26));
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText("Invincible:" + invincibleCounter, 10, 400);
         */
    }
}
