package fr.cejuba.stardew.entity;

import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.main.UtilityTool;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import java.io.InputStream;

public class Entity {
    GamePanel gamePanel;

    // Images
    public Image up0, up1, down0, down1, left0, left1, right0, right1;
    public Image attackUp0, attackUp1, attackDown0, attackDown1, attackLeft0, attackLeft1, attackRight0, attackRight1;
    public Image image, image2, image3;
    public Rectangle solidArea = new Rectangle(0, 0, 48 ,48);
    public Rectangle attackArea = new Rectangle(0, 0, 0,0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionActivated = false;
    public String[] dialogues = new String[20];

    // States
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNumber = 1;
    int dialogueIndex = 0;
    public boolean collision = false;
    public boolean invincible = false;
    boolean attacking = false;

    // Counter
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    public int spriteCounter = 0;

    // Attributes
    public int type; // 0 = Player, 1 = NPC, 2 = Monster
    public String name;
    public int speed;
    public int maxLife;
    public int life;

    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setAction() {}
    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gamePanel.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gamePanel.player.direction) {
            case "up" -> direction = "down";
            case "down" -> direction = "up";
            case "left" -> direction = "right";
            case "right" -> direction = "left";
        }
    }

    public void update() {
        setAction();

        collisionActivated = false;
        gamePanel.collisionChecker.checkTile(this);
        gamePanel.collisionChecker.checkObject(this, false);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.monster);
        boolean contactPlayer = gamePanel.collisionChecker.checkPlayer(this);

        if (this.type == 2 && contactPlayer) {
            if(!gamePanel.player.invincible){
                gamePanel.player.life--;
                gamePanel.player.invincible = true;
            }
        }

        if (!collisionActivated) {
            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left'" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }

        spriteCounter++;
        if (spriteCounter > 10) {
            spriteNumber = (spriteNumber == 0) ? 1 : 0;
            spriteCounter = 0;
        }

        // Invincibility
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void draw(GraphicsContext graphicsContext) {
        Image image = null;
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {

            switch (direction) {
                case "up" -> image = (spriteNumber == 0) ? up0 : up1;
                case "down" -> image = (spriteNumber == 0) ? down0 : down1;
                case "left" -> image = (spriteNumber == 0) ? left0 : left1;
                case "right" -> image = (spriteNumber == 0) ? right0 : right1;
            }

            if(invincible){
                graphicsContext.setGlobalAlpha(0.1);
            }
            graphicsContext.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize);
            graphicsContext.setGlobalAlpha(1);
        }
    }

    public Image setup(String imageName, int width, int height) {
        UtilityTool utilityTool = new UtilityTool();
        Image scaledImage = null;
        try {
            InputStream is = getClass().getResourceAsStream("/fr/cejuba/stardew/" + imageName + ".png");
            if (is == null) {
                throw new RuntimeException("Resource not found: " + imageName);
            }
            Image originalImage = new Image(is);
            scaledImage = utilityTool.scaleImage(originalImage, width, height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scaledImage;
    }
}
