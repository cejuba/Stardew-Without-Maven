package fr.cejuba.stardew.entity;

import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.main.GameState;
import fr.cejuba.stardew.main.KeyHandler;
import fr.cejuba.stardew.main.Type;
import fr.cejuba.stardew.object.boots.Boots;
import fr.cejuba.stardew.object.consumable.Consumable;
import fr.cejuba.stardew.object.lighting.Lantern;
import fr.cejuba.stardew.object.boots.LeatherBoots;
import fr.cejuba.stardew.object.activable.Key;
import fr.cejuba.stardew.object.consumable.RedPotion;
import fr.cejuba.stardew.object.lighting.LightingItem;
import fr.cejuba.stardew.object.projectile.Fireball;
import fr.cejuba.stardew.object.shield.RustyShield;
import fr.cejuba.stardew.object.shield.Shield;
import fr.cejuba.stardew.object.weapon.Axe;
import fr.cejuba.stardew.object.weapon.Weapon;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class Player extends Entity {

    private KeyHandler keyHandler;

    private final int screenX;
    private final int screenY;
    private boolean attackCanceled = false;
    private boolean lightUpdated = false;


    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        super(gamePanel); // Call the constructor of the parent class

        this.keyHandler = keyHandler;

        screenX = gamePanel.getScreenWidth() / 2 - (gamePanel.getTileSize() / 2);
        screenY = gamePanel.getScreenHeight() / 2 - (gamePanel.getTileSize() / 2);

        solidArea = new Rectangle(8, 16, 32, 32);
        solidAreaDefaultX = (int) solidArea.getX();
        solidAreaDefaultY = (int) solidArea.getY();
/*
        attackArea.setWidth(36);
        attackArea.setHeight(36);
 */
        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }

    public void setKeyHandler(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
    }

    public void setDefaultValues() {

        setDefaultPosition();

        // Player status

        level = 1;
        maxLife = 6;
        maxMana = 4;
        restoreLifeAndMana();
        ammo = 10;
        strength = 1;
        dexterity = 1;
        agility = 2;
        experience = 0;
        nextLevelExperience = 5;
        gold = 500;
        currentWeapon = new Axe(gamePanel);
        currentShield = new RustyShield(gamePanel);
        currentBoots = new LeatherBoots(gamePanel);
        projectile = new Fireball(gamePanel);
        // projectile = new Rock(gamePanel); TODO : Change to have a system with ammo
        attack = getAttack();
        defense = getDefense();
        defaultSpeed = getSpeed();
        speed = defaultSpeed;
    }

    public void setDefaultPosition() {
        worldX = gamePanel.getTileSize() * 23;
        worldY = gamePanel.getTileSize() * 21;
        //worldX = gamePanel.tileSize * 12;
        //worldY = gamePanel.tileSize * 11;
        direction = "down";

    }

    public void restoreLifeAndMana(){
        life = maxLife;
        mana = maxMana;
        invincible = false;
    }

    public void setItems() {
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(currentBoots);
        inventory.add(new Lantern(gamePanel));
        inventory.add(new Key(gamePanel));
        inventory.add(new RedPotion(gamePanel));
    }

    public int getAttack() {
        attackArea = currentWeapon.attackArea;
        return strength * currentWeapon.attackValue;
    }

    public int getDefense() {
        return dexterity * currentShield.defenseValue;
    }

    public int getSpeed(){
        return agility * currentBoots.speedValue;
    }

    public void getPlayerImage() {
        try {
            System.out.println("Loading player images");

            up1 = setup("player/walking/player_up_1", gamePanel.getTileSize(), gamePanel.getTileSize());
            up2 = setup("player/walking/player_up_2", gamePanel.getTileSize(), gamePanel.getTileSize());
            down1 = setup("player/walking/player_down_1", gamePanel.getTileSize(), gamePanel.getTileSize());
            down2 = setup("player/walking/player_down_2", gamePanel.getTileSize(), gamePanel.getTileSize());
            right1 = setup("player/walking/player_right_1", gamePanel.getTileSize(), gamePanel.getTileSize());
            right2 = setup("player/walking/player_right_2", gamePanel.getTileSize(), gamePanel.getTileSize());
            left1 = setup("player/walking/player_right_1", gamePanel.getTileSize(), gamePanel.getTileSize());
            left2 = setup("player/walking/player_right_2", gamePanel.getTileSize(), gamePanel.getTileSize());

            System.out.println("Player images loaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getSleepingImage(Image image){
        up1 = image;
        up2 = image;
        down1 = image;
        down2 = image;
        right1 = image;
        right2 = image;
        left1 = image;
        left2 = image;
    }

    public void getPlayerAttackImage() {
        try {
            System.out.println("Loading player attack images");
            switch(currentWeapon.getType()) {
                case Type.SWORD -> {
                    attackUp0 = setup("player/attacking/boy_attack_up_1", gamePanel.getTileSize(), gamePanel.getTileSize() * 2);
                    attackUp1 = setup("player/attacking/boy_attack_up_2", gamePanel.getTileSize(), gamePanel.getTileSize() * 2);
                    attackDown0 = setup("player/attacking/boy_attack_down_1", gamePanel.getTileSize(), gamePanel.getTileSize() * 2);
                    attackDown1 = setup("player/attacking/boy_attack_down_2", gamePanel.getTileSize(), gamePanel.getTileSize() * 2);
                    attackRight0 = setup("player/attacking/boy_attack_right_1", gamePanel.getTileSize() * 2, gamePanel.getTileSize());
                    attackRight1 = setup("player/attacking/boy_attack_right_2", gamePanel.getTileSize() * 2, gamePanel.getTileSize());
                    attackLeft0 = setup("player/attacking/boy_attack_left_1", gamePanel.getTileSize() * 2, gamePanel.getTileSize());
                    attackLeft1 = setup("player/attacking/boy_attack_left_2", gamePanel.getTileSize() * 2, gamePanel.getTileSize());
                }
                case Type.AXE -> {
                    attackUp0 = setup("player/attacking/boy_axe_up_1", gamePanel.getTileSize(), gamePanel.getTileSize() * 2);
                    attackUp1 = setup("player/attacking/boy_axe_up_2", gamePanel.getTileSize(), gamePanel.getTileSize() * 2);
                    attackDown0 = setup("player/attacking/boy_axe_down_1", gamePanel.getTileSize(), gamePanel.getTileSize() * 2);
                    attackDown1 = setup("player/attacking/boy_axe_down_2", gamePanel.getTileSize(), gamePanel.getTileSize() * 2);
                    attackRight0 = setup("player/attacking/boy_axe_right_1", gamePanel.getTileSize() * 2, gamePanel.getTileSize());
                    attackRight1 = setup("player/attacking/boy_axe_right_2", gamePanel.getTileSize() * 2, gamePanel.getTileSize());
                    attackLeft0 = setup("player/attacking/boy_axe_left_1", gamePanel.getTileSize() * 2, gamePanel.getTileSize());
                    attackLeft1 = setup("player/attacking/boy_axe_left_2", gamePanel.getTileSize() * 2, gamePanel.getTileSize());
                }
            }
            System.out.println("Player attack images loaded successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {

        if(attacking){
            attacking();
        }
        else if (keyHandler.isUpPressed() || keyHandler.isDownPressed() || keyHandler.isLeftPressed() || keyHandler.isRightPressed() || keyHandler.isEnterPressed()) {
            if (keyHandler.isUpPressed()) {
                direction = "up";
            } else if (keyHandler.isDownPressed()) {
                direction = "down";
            } else if (keyHandler.isLeftPressed()) {
                direction = "left";
            } else if (keyHandler.isRightPressed()) {
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

            // InteractiveTile Collision
            int interactiveTileIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.interactiveTile);



            // Event checker
            gamePanel.eventHandler.checkEvent();

            if (!collisionActivated && !keyHandler.isEnterPressed()) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            if(keyHandler.isEnterPressed() && !attackCanceled){
                gamePanel.playSoundEffect(7);
                attacking = true;
                spriteCounter = 0;
            }

            attackCanceled = false;
            gamePanel.keyHandler.setEnterPressed(false);

            spriteCounter++;
            if (spriteCounter > 10) {
                spriteNumber = (spriteNumber == 0) ? 1 : 0;
                spriteCounter = 0;
            }
        }


        if(gamePanel.keyHandler.isShotKeyPressed() && !projectile.alive && shotAvailableCounter == 30 && projectile.haveRessource(this)){
            // Set Default Coordinates
            projectile.set(worldX, worldY, direction, true, this);

            // Subtract Resources
            projectile.subtractResource(this);

            // Check Vacancy
            for(int i = 0; i < gamePanel.projectile[1].length; i++){
                if(gamePanel.projectile[gamePanel.getCurrentMap()][i] == null){
                    gamePanel.projectile[gamePanel.getCurrentMap()][i] = projectile;
                    break;
                }
            }
            shotAvailableCounter = 0;
            gamePanel.playSoundEffect(10);
        }
        // Invincibility
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if(shotAvailableCounter < 30){
            shotAvailableCounter++;
        }
        if(life > maxLife){
            life = maxLife;
        }
        if(mana > maxMana){
            mana = maxMana;
        }
        if(life <= 0){
            gamePanel.setGameState(GameState.GAMEOVER);
            gamePanel.ui.setCommandNumber(-1); // Prevent retry by accident when smashing ENTER key
            gamePanel.stopMusic();
            gamePanel.playSoundEffect(12);
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
            damageMonster(monsterIndex, attack, currentWeapon.knockBackPower);

            int interactiveTileIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.interactiveTile);
            damageInteractiveTile(interactiveTileIndex);

            int projectileIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.projectile);
            damageProjectile(projectileIndex);

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

            // PickUpOnly Objects
            if(gamePanel.object[gamePanel.getCurrentMap()][index].getType() == Type.PICKUPONLY){
                gamePanel.object[gamePanel.getCurrentMap()][index].use(this);
                gamePanel.object[gamePanel.getCurrentMap()][index] = null;
            }
            // Obstacle
            else if(gamePanel.object[gamePanel.getCurrentMap()][index].getType() == Type.OBSTACLE){
                if(keyHandler.isEnterPressed()){
                    gamePanel.object[gamePanel.getCurrentMap()][index].interact();
                }
            }
            // Inventory
            else{
                String text;

                if(canObtainItem(gamePanel.object[gamePanel.getCurrentMap()][index])){
                    gamePanel.playSoundEffect(1);
                    text = "Got a " + gamePanel.object[gamePanel.getCurrentMap()][index].name + "!";
                }
                else {
                    text = "You cannot carry any more items.";
                }
                gamePanel.ui.addMessage(text);
                gamePanel.object[gamePanel.getCurrentMap()][index] = null;
            }
        }
    }

    public void interactNPC(int index) {
        if(gamePanel.keyHandler.isEnterPressed()){
            if (index != 999) {
                attackCanceled = true;
                gamePanel.setGameState(GameState.DIALOGUE);
                gamePanel.npc[gamePanel.getCurrentMap()][index].speak();
            }
        }
    }

    public void contactMonster(int index) {
        if (index != 999) {
            if(!invincible && !gamePanel.monster[gamePanel.getCurrentMap()][index].dying){
                gamePanel.playSoundEffect(6);
                int damage = gamePanel.monster[gamePanel.getCurrentMap()][index].attack - defense;
                if(damage < 0){
                    damage = 0;
                }
                life -= damage;
                invincible = true;
            }
        }
    }

    public void damageMonster(int index, int attack, int knockBackPower) {
        if (index != 999) {
            System.out.println("Monster hit");
            if(!gamePanel.monster[gamePanel.getCurrentMap()][index].invincible){
                gamePanel.playSoundEffect(5);

                if(knockBackPower > 0){
                    knockBack(gamePanel.monster[gamePanel.getCurrentMap()][index], knockBackPower);
                }
                int damage = attack - gamePanel.monster[gamePanel.getCurrentMap()][index].defense;
                if(damage < 0){
                    damage = 0;
                }

                gamePanel.monster[gamePanel.getCurrentMap()][index].life -= damage;
                gamePanel.ui.addMessage(damage + " damage to " + gamePanel.monster[gamePanel.getCurrentMap()][index].name + ".");

                gamePanel.monster[gamePanel.getCurrentMap()][index].invincible = true;
                gamePanel.monster[gamePanel.getCurrentMap()][index].damageReaction();

                if(gamePanel.monster[gamePanel.getCurrentMap()][index].life <= 0){
                    gamePanel.monster[gamePanel.getCurrentMap()][index].dying = true;
                    gamePanel.ui.addMessage("You defeated " + gamePanel.monster[gamePanel.getCurrentMap()][index].name + "!");
                    gamePanel.ui.addMessage("Experience + " + gamePanel.monster[gamePanel.getCurrentMap()][index].name);
                    experience += gamePanel.monster[gamePanel.getCurrentMap()][index].experience;
                    checkLevelUp();
                }
            }
        }
    }

    public void knockBack(Entity entity, int knockBackPower) {
        entity.direction = direction;
        entity.speed += knockBackPower;
        entity.knockBack = true;
    }

    public void damageInteractiveTile(int i){

        if(i != 999 && gamePanel.interactiveTile[gamePanel.getCurrentMap()][i].isDestructible() && gamePanel.interactiveTile[gamePanel.getCurrentMap()][i].isCorrectItem(this) && !gamePanel.interactiveTile[gamePanel.getCurrentMap()][i].invincible){
            gamePanel.interactiveTile[gamePanel.getCurrentMap()][i].playSoundEnvironment();
            gamePanel.interactiveTile[gamePanel.getCurrentMap()][i].life --;
            gamePanel.interactiveTile[gamePanel.getCurrentMap()][i].invincible = true;

            generateParticle(gamePanel.interactiveTile[gamePanel.getCurrentMap()][i], gamePanel.interactiveTile[gamePanel.getCurrentMap()][i]);

            if(gamePanel.interactiveTile[gamePanel.getCurrentMap()][i].life <= 0){
                gamePanel.interactiveTile[gamePanel.getCurrentMap()][i] = gamePanel.interactiveTile[gamePanel.getCurrentMap()][i].getDestroyedForm();
            }
        }
    }

    public void damageProjectile(int i){
        if(i != 999){
            Entity projectile = gamePanel.projectile[gamePanel.getCurrentMap()][i];
            projectile.alive = false;
            generateParticle(projectile, projectile);
        }
    }

    public void checkLevelUp() {
        if (experience >= nextLevelExperience) {
            level++;
            experience -= nextLevelExperience;
            nextLevelExperience *= 2;
            maxLife += 2;
            life = maxLife;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();

            gamePanel.playSoundEffect(8);
            gamePanel.setGameState(GameState.DIALOGUE);
            gamePanel.ui.setCurrentDialogue("You are level " + level + " now!\n" + "Max life +2, Strength +1, Dexterity +1");

        }
    }

    public void selectItem(){
        int itemIndex = gamePanel.ui.getItemIndexInInventory(gamePanel.ui.getPlayerSlotColumn(), gamePanel.ui.getPlayerSlotRow());

        if(itemIndex < inventory.size()){
            Entity selectedItem = inventory.get(itemIndex);

            if (selectedItem instanceof Weapon) {
                currentWeapon = (Weapon) selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            } else if (selectedItem instanceof Shield) {
                currentShield = (Shield) selectedItem;
                defense = getDefense();
            } else if (selectedItem instanceof Boots) {
                currentBoots = (Boots) selectedItem;
                speed = getSpeed();
            } else if (selectedItem instanceof LightingItem) {
                if(currentLight == selectedItem) {
                    currentLight = null;
                } else {
                    currentLight = (LightingItem) selectedItem;
                }
                lightUpdated = true;
            } else if (selectedItem instanceof Consumable) {
                System.out.println(selectedItem.name + " used");
                if(selectedItem.use(this)){
                    if(selectedItem.amount > 1){
                        selectedItem.amount--;
                    } else {
                        inventory.remove(itemIndex);
                    }
                }
            }
        }
    }

    public int searchItemInInventory(String itemName){
        int itemIndex = 999;
        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i).name.equals(itemName)){
                itemIndex = i;
                break;
            }
        }
        return itemIndex;
    }

    public boolean canObtainItem(Entity item){
        boolean canObtain = false;

        // Check if stackable
        if(item.stackable){
            int index = searchItemInInventory(item.name);

            if(index != 999){
                inventory.get(index).amount++;
                canObtain = true;
            }
            else{ // New item so need to check vacancy
                if(inventory.size() < maxInventorySize){
                    inventory.add(item);
                    canObtain = true;
                }
            }
        }
        else{ // Not stackable so check vacancy
            if(inventory.size() < maxInventorySize){
                inventory.add(item);
                canObtain = true;
            }
        }
        return canObtain;
    }

    public void draw(GraphicsContext graphicsContext) {
        Image image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up" :
                if(!attacking){
                    image = (spriteNumber == 0) ? up1 : up2;
                }
                else{
                    tempScreenY -= gamePanel.getTileSize();
                    image = (spriteNumber == 0) ? attackUp0 : attackUp1;
                }
                break;
            case "down" :
                if(!attacking){
                    image = (spriteNumber == 0) ? down1 : down2;
                }
                else{
                    image = (spriteNumber == 0) ? attackDown0 : attackDown1;
                }
                break;
            case "left" :
                if(!attacking){
                    image = (spriteNumber == 0) ? left1 : left2;
                }
                else{
                    tempScreenX -= gamePanel.getTileSize();
                    image = (spriteNumber == 0) ? attackLeft0 : attackLeft1;
                }
                break;
            case "right" :
                if(!attacking){
                    image = (spriteNumber == 0) ? right1 : right2;
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

    // Getters and Setters
    public int getScreenX() {
        return screenX;
    }
    public int getScreenY() {
        return screenY;
    }
    public boolean isLightUpdated() {
        return lightUpdated;
    }
    public void setLightUpdated(boolean lightUpdated) {
        this.lightUpdated = lightUpdated;
    }
    public KeyHandler getKeyHandler() {
        return keyHandler;
    }
    public boolean isAttackCanceled() {
        return attackCanceled;
    }
    public void setAttackCanceled(boolean attackCanceled) {
        this.attackCanceled = attackCanceled;
    }

}
