package fr.cejuba.stardew.entity;

import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.main.KeyHandler;
import fr.cejuba.stardew.main.UtilityTool;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import java.io.InputStream;
import java.util.Objects;

public class Player extends Entity {

    GamePanel gamePanel;
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;

    public int hasKey = 0;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
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
    }

    public void getPlayerImage() {
        try {
            System.out.println("Loading player images...");

            up0 = setup("Back_0");
            up1 = setup("Back_1");
            down0 = setup("Face_0");
            down1 = setup("Face_1");
            right0 = setup("Right_0");
            right1 = setup("Right_1");
            left0 = setup("Right_0");
            left1 = setup("Right_0");

            System.out.println("Player images loaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Image setup(String imageName) {
        UtilityTool utilityTool = new UtilityTool();
        Image scaledImage = null;
        try {
            InputStream is = getClass().getResourceAsStream("/fr/cejuba/stardew/player/" + imageName + ".png");
            System.out.println("Loading image: /fr/cejuba/stardew/player/" + imageName + ".png");
            if (is == null) {
                System.out.println("Resource not found: " + imageName);
                throw new RuntimeException("Resource not found: " + imageName);
            }
            Image originalImage = new Image(is);
            scaledImage = utilityTool.scaleImage(originalImage, gamePanel.tileSize, gamePanel.tileSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scaledImage;
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

            int objectIndex = gamePanel.collisionChecker.checkObject(this, true);
            pickUpObject(objectIndex);

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
            String objectName = gamePanel.superObject[index].name;

            switch (objectName) {
                case "Key":
                    gamePanel.playSoundEffect(1);
                    hasKey++;
                    gamePanel.superObject[index] = null;
                    gamePanel.ui.showMessage("You got a key!");
                    break;
                case "Door":
                    if (hasKey > 0) {
                        gamePanel.playSoundEffect(3);
                        gamePanel.superObject[index] = null;
                        hasKey--;
                        gamePanel.ui.showMessage("You opened the door!");
                    } else {
                        gamePanel.ui.showMessage("You need a key!");
                    }
                    break;
                case "Boots":
                    gamePanel.playSoundEffect(2);
                    speed += 2;
                    gamePanel.ui.showMessage("You can now run faster!");
                    gamePanel.superObject[index] = null;
                    break;
                case "Chest":
                    gamePanel.ui.gameFinished = true;
                    gamePanel.stopMusic();
                    gamePanel.playSoundEffect(4);
                    break;
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
