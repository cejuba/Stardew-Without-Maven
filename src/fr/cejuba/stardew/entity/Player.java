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

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {

        super(gamePanel); // Call the constructor of the parent class

        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);

        solidArea = new Rectangle(8, 16, 32, 32);
        double solidAreaDefaultX = solidArea.getX();
        double solidAreaDefaultY = solidArea.getY();

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

            up0 = setup("/player/Back_0");
            up1 = setup("/player/Back_1");
            down0 = setup("/player/Face_0");
            down1 = setup("/player/Face_1");
            right0 = setup("/player/Right_0");
            right1 = setup("/player/Right_1");
            left0 = setup("/player/Right_0");
            left1 = setup("/player/Right_0");

            System.out.println("Player images loaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed) {
            if (keyHandler.upPressed) {
                direction = "up";
            } else if (keyHandler.downPressed) {
                direction = "down";
            } else if (keyHandler.leftPressed) {
                direction = "left";
            } else {
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

            // Event checker
            gamePanel.eventHandler.checkEvent();

            gamePanel.keyHandler.enterPressed = false;


            if (!collisionActivated) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNumber == 0) {
                    spriteNumber = 1;
                } else if (spriteNumber == 1) {
                    spriteNumber = 0;
                }
                spriteCounter = 0;
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
            if(gamePanel.keyHandler.enterPressed){
                gamePanel.gameState = gamePanel.dialogueState;
                gamePanel.npc[index].speak();
            }
        }
    }

    public void draw(GraphicsContext graphicsContext) {
        Image image = null;
        switch (direction) {
            case "up":
                if (spriteNumber == 0) {
                    image = up0;
                } else if (spriteNumber == 1) {
                    image = up1;
                }
                break;
            case "down":
                if (spriteNumber == 0) {
                    image = down0;
                } else if (spriteNumber == 1) {
                    image = down1;
                }
                break;
            case "left":
                if (spriteNumber == 0) {
                    image = left0;
                }
                if (spriteNumber == 1) {
                    image = left1;
                }
                break;
            case "right":
                if (spriteNumber == 0) {
                    image = right0;
                }
                if (spriteNumber == 1) {
                    image = right1;
                }
                break;
        }
        if (image != null) {
            graphicsContext.drawImage(image, screenX, screenY);
        }
    }
}
