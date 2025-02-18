package fr.cejuba.stardew.entity;

import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.main.UtilityTool;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import java.io.InputStream;

public class Entity {
    GamePanel gamePanel;

    public int worldX, worldY;
    public int speed;

    public Image up0, up1, down0, down1, left0, left1, right0, right1;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNumber = 1;

    public Rectangle solidArea = new Rectangle(0, 0, 48 ,48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionActivated = false;

    public int actionLockCounter = 0;
    public String[] dialogues = new String[20];
    int dialogueIndex = 0;

    // Character Status
    public int maxLife;
    public int life;

    public Entity(GamePanel gamePanel){
        this.gamePanel = gamePanel;

    }

    public void setAction() {}
    public void speak() {
        if(dialogues[dialogueIndex] == null){
            dialogueIndex = 0;
        }
        gamePanel.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gamePanel.player.direction){
            case"up" :
                direction = "down";
                break;
            case"down" :
                direction = "up";
                break;
            case"left" :
                direction = "right";
                break;
            case"right" :
                direction = "left";
                break;
        }
    }
    public void update(){
        setAction();

        collisionActivated = false;
        gamePanel.collisionChecker.checkTile(this);
        gamePanel.collisionChecker.checkObject(this, false);
        gamePanel.collisionChecker.checkPlayer(this);


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


    public void draw(GraphicsContext graphicsContext) {
        Image image = null;
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if (    worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX  &&
                worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY ) {

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
            graphicsContext.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize);
        }
    }

    public Image setup(String imageName) {
        UtilityTool utilityTool = new UtilityTool();
        Image scaledImage = null;
        try {
            InputStream is = getClass().getResourceAsStream("/fr/cejuba/stardew" + imageName + ".png");
            System.out.println("Loading image: /fr/cejuba/stardew" + imageName + ".png");
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
}
