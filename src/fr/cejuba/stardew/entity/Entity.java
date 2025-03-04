package fr.cejuba.stardew.entity;

import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.main.UtilityTool;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
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
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;

    // Counter
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    public int spriteCounter = 0;
    int dyingCounter = 0;
    int hpBarCounter = 0;

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
                gamePanel.playSoundEffect(6);
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

            // Monster HP bar
            if(type == 2 && hpBarOn){

                double oneScale = (double)gamePanel.tileSize / maxLife;
                double hpBarValue = oneScale * life;

                graphicsContext.setFill(Color.BLACK);
                graphicsContext.fillRect(screenX-1, screenY - 16, gamePanel.tileSize+2, 12);
                graphicsContext.setFill(Color.RED);
                graphicsContext.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);

                hpBarCounter++;

                if(hpBarCounter > 600){
                    hpBarOn = false;
                    hpBarCounter = 0;
                }
            }

            if(invincible){
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(graphicsContext, 0.1F);
            }
            if(dying){
                dyingAnimation(graphicsContext);
            }
            graphicsContext.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize);
            changeAlpha(graphicsContext, 1);
        }
    }


    public void dyingAnimation(GraphicsContext graphicsContext) {
        dyingCounter++;

        int i = 5;
        int totalPhases = 8;

        // Determine the phase (1-based index)
        int phase = (dyingCounter - 1) / i + 1;

        // Toggle alpha based on the phase being odd/even
        if (phase <= totalPhases) {
        changeAlpha(graphicsContext, (phase % 2 == 1) ? 0 : 1);
        }
        else {
            // End of animation
            dying = false;
            alive = false;
        }
    }

    public void changeAlpha(GraphicsContext graphicsContext, float alphaValue) {
        graphicsContext.setGlobalAlpha(alphaValue);
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
