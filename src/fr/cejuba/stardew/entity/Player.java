package fr.cejuba.stardew.entity;

import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.main.KeyHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.awt.*;

public class Player extends Entity {

    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        super(gamePanel); // Call the constructor of the parent class

        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);

        solidArea = new Rectangle(8, 16, 32, 32);
        solidAreaDefaultX = (int) solidArea.getX();
        solidAreaDefaultY = (int) solidArea.getY();

        setDefaultValues();
        getPlayerImage();
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
            System.out.println("Loading player images...");

            up0 = setup("player/Back_0");
            up1 = setup("player/Back_1");
            down0 = setup("player/Face_0");
            down1 = setup("player/Face_1");
            right0 = setup("player/Right_0");
            right1 = setup("player/Right_1");
            left0 = setup("player/Right_0");
            left1 = setup("player/Right_0");

            System.out.println("Player images loaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed || keyHandler.enterPressed) {
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

    public void pickUpObject(int index) {
        if (index != 999) {
            // TBD
        }
    }

    public void interactNPC(int index) {
        if (index != 999) {
            if (gamePanel.keyHandler.enterPressed) {
                gamePanel.gameState = gamePanel.dialogueState;
                gamePanel.npc[index].speak();
            }
        }
    }

    public void contactMonster(int index) {
        if (index != 999) {
            if(!invincible){
                life -= 1;
                invincible = true;
            }
        }
    }

    public void draw(GraphicsContext graphicsContext) {
        Image image = null;
        switch (direction) {
            case "up" -> image = (spriteNumber == 0) ? up0 : up1;
            case "down" -> image = (spriteNumber == 0) ? down0 : down1;
            case "left" -> image = (spriteNumber == 0) ? left0 : left1;
            case "right" -> image = (spriteNumber == 0) ? right0 : right1;
        }

        if(invincible){
            graphicsContext.setGlobalAlpha(0.2);
        }

        if (image != null) {
            graphicsContext.drawImage(image, screenX, screenY);
        }

        graphicsContext.setGlobalAlpha(1.0);

        /* Draw player's invicibility : maybe can do with KeyHandler
        graphicsContext.setFont(new Font("Arial", 26));
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText("Invincible:" + invincibleCounter, 10, 400);
         */
    }
}
