package fr.cejuba.stardew.entity;

import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.main.Type;
import fr.cejuba.stardew.main.UtilityTool;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.InputStream;
import java.util.ArrayList;

public class Entity {
    GamePanel gamePanel;

    // Images
    public Image up1, up2, down1, down2, left1, left2, right1, right2;
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
    public boolean onPath = false;
    public boolean knockBack = false; // TODO : Bug : knockBack is knock enemy in places where they shouldn't be (inside a tree, in the ocean, ...)

    // Counter
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    public int spriteCounter = 0;
    public int shotAvailableCounter = 0;
    int dyingCounter = 0;
    int hpBarCounter = 0;
    int knockBackCounter = 0;

    // Attributes
    public String name;
    public int defaultSpeed;
    public int speed;
    public int maxLife;
    public int life;
    public int maxMana;
    public int mana;
    public int ammo;
    public int level;
    public int strength;
    public int dexterity;
    public int agility;
    public int attack;
    public int defense;
    public int experience;
    public int nextLevelExperience;
    public int gold;
    public Entity currentWeapon;
    public Entity currentShield;
    public Entity currentBoots;
    public Entity currentLight;
    public Projectile projectile;

    // Item attributes
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;
    public int value;
    public int attackValue;
    public int defenseValue;
    public int speedValue;
    public String description = "";
    public int useCost;
    public int price;
    public int knockBackPower = 0;
    public boolean stackable = false;
    public int amount = 1;
    public int lightRadius;

    // Type
    private Type type;

    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setAction() {}

    public void damageReaction() {}

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

    public void checkCollision(){
        collisionActivated = false;
        gamePanel.collisionChecker.checkTile(this);
        gamePanel.collisionChecker.checkObject(this, false);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.monster);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.interactiveTile);

        boolean contactPlayer = gamePanel.collisionChecker.checkPlayer(this);

        if (this.type == Type.MONSTER && contactPlayer) {
            damagePlayer(attack);
        }
    }

    public void update() {

        if(knockBack){
            checkCollision();

            if(collision){
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
            else{
                switch(gamePanel.player.direction){
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            knockBackCounter++;
            if(knockBackCounter >= 10){
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
        }
        else{
            setAction();
            checkCollision();

            if (!collisionActivated) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
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
        if(shotAvailableCounter < 30){
            shotAvailableCounter++;
        }
    }

    public void damagePlayer(int attack){
        if(!gamePanel.player.invincible){
            gamePanel.playSoundEffect(6);

            int damage = attack - gamePanel.player.defense;
            if(damage < 0){
                damage = 0;
            }
            gamePanel.player.life -= damage;
            gamePanel.player.invincible = true;
        }
    }

    public void draw(GraphicsContext graphicsContext) {
        Image image = null;
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.getScreenX();
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.getScreenY();

        if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.getScreenX() &&
                worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.getScreenX() &&
                worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.getScreenY() &&
                worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.getScreenY()) {

            switch (direction) {
                case "up" -> image = (spriteNumber == 0) ? up1 : up2;
                case "down" -> image = (spriteNumber == 0) ? down1 : down2;
                case "left" -> image = (spriteNumber == 0) ? left1 : left2;
                case "right" -> image = (spriteNumber == 0) ? right1 : right2;
            }

            // Monster HP bar
            if(type == Type.MONSTER && hpBarOn){

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
            graphicsContext.drawImage(image, screenX, screenY);
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
            alive = false;
        }
    }

    public void changeAlpha(GraphicsContext graphicsContext, float alphaValue) {
        graphicsContext.setGlobalAlpha(alphaValue);
    }

    public void interact(){

    }
    public boolean use(Entity entity) {
        return false;
    }

    public void checkDrop(){}

    public void dropItem(Entity droppedItem){
        for(int i = 0; i < gamePanel.object[1].length; i++){
            if(gamePanel.object[gamePanel.currentMap][i] == null){
                gamePanel.object[gamePanel.currentMap][i] = droppedItem;
                gamePanel.object[gamePanel.currentMap][i].worldX = worldX;
                gamePanel.object[gamePanel.currentMap][i].worldY = worldY;
                break;
            }
        }
    }

    public Color getParticleColor() {
        return null;
    }

    public int getParticleSize() {
        return 0;
    }

    public int getParticleSpeed() {
        return 0;
    }

    public int getParticleMaxLife() {
        return 0;
    }

    public void generateParticle(Entity generator, Entity target) {
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle particle1 = new Particle(gamePanel, target, color, size, speed, maxLife, -2, -1);
        Particle particle2 = new Particle(gamePanel, target, color, size, speed, maxLife, -2, 1);
        Particle particle3 = new Particle(gamePanel, target, color, size, speed, maxLife, 2, -1);
        Particle particle4 = new Particle(gamePanel, target, color, size, speed, maxLife, 2, 1);

        gamePanel.particleList.add(particle1);
        gamePanel.particleList.add(particle2);
        gamePanel.particleList.add(particle3);
        gamePanel.particleList.add(particle4);

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

    public void searchPath(int goalCol, int goalRow){
        int startCol = (int) ((worldX + solidArea.getX())/gamePanel.tileSize);
        int startRow = (int) ((worldY + solidArea.getY())/gamePanel.tileSize);

        gamePanel.pathFinder.setNodes(startCol, startRow, goalCol, goalRow);

        if(gamePanel.pathFinder.search()){
            // Next World Coordinates
            int nextX = gamePanel.pathFinder.pathList.get(0).col * gamePanel.tileSize;
            int nextY = gamePanel.pathFinder.pathList.get(0).row * gamePanel.tileSize;

            // Entity's solidArea position
            int entityLeftX = getLeftX();
            int entityRightX = getRightX();
            int entityTopY = getTopY();
            int entityBottomY = getBottomY();

            if(entityTopY > nextY && entityLeftX >= nextX && entityRightX < nextX + gamePanel.tileSize){
                direction = "up";
            } else if (entityTopY < nextY && entityLeftX >= nextX && entityRightX < nextX + gamePanel.tileSize){
                direction = "down";
            } else if (entityTopY >= nextY && entityBottomY < nextY + gamePanel.tileSize) {
                // Left or Right
                if (entityLeftX > nextX) {
                    direction = "left";
                }
                if(entityLeftX < nextX){
                    direction = "right";
                }
            } else if(entityTopY > nextY && entityLeftX > nextX){
                // Up or Left
                direction = "up";
                checkCollision();
                if(collisionActivated){
                    direction = "left";
                }
            } else if(entityTopY > nextY && entityLeftX < nextX){
                // Up or Right
                direction = "up";
                checkCollision();
                if(collisionActivated){
                    direction = "right";
                }
            } else if(entityTopY < nextY && entityLeftX > nextX){
                // Down or Left
                direction = "down";
                checkCollision();
                if(collisionActivated){
                    direction = "left";
                }
            } else if(entityTopY < nextY && entityLeftX < nextX){
                // Down or Right
                direction = "down";
                checkCollision();
                if(collisionActivated){
                    direction = "right";
                }
            }

            // TODO : Comment in following the player
            // If the goal is reached, stop the search
            int nextCol = gamePanel.pathFinder.pathList.get(0).col;
            int nextRow = gamePanel.pathFinder.pathList.get(0).row;
            if(nextCol == goalCol && nextRow == goalRow){
                onPath = false;
            }
        }
    }

    public int getDetected(Entity user, Entity[][] target, String targetName){
        int index = 999;

        // Get surrounding objects
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();

        switch (user.direction) {
            case "up" -> nextWorldY =  user.getTopY() - 1;
            case "down" -> nextWorldY = user.getBottomY() + 1;
            case "left" -> nextWorldX = user.getLeftX() - 1;
            case "right" -> nextWorldX = user.getRightX() + 1;
        }

        int col = (int) (nextWorldX / gamePanel.tileSize);
        int row = (int) (nextWorldY / gamePanel.tileSize);

        for(int i = 0; i < target[1].length; i++){
            Entity targeted = target[gamePanel.currentMap][i];
            if(targeted != null){
                if(targeted.getColumn() == col && targeted.getRow() == row && targeted.name.equals(targetName)){
                    index = i;
                    break;
                }
            }
        }
        return index;
    }


    // Getter Setter

    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public int getLeftX(){
        return (int) (worldX + solidArea.getX());
    }
    public int getRightX(){
        return (int) (worldX + solidArea.getX() + solidArea.getWidth());
    }
    public int getTopY(){
        return (int) (worldY + solidArea.getY());
    }
    public int getBottomY(){
        return (int) (worldY + solidArea.getY() + solidArea.getHeight());
    }
    public int getColumn(){
        return (int) ((worldX + solidArea.getX())/gamePanel.tileSize);
    }
    public int getRow(){
        return (int) ((worldY + solidArea.getY())/gamePanel.tileSize);
    }
}
