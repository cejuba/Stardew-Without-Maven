package fr.cejuba.stardew.entity;

import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.main.KeyHandler;
import fr.cejuba.stardew.object.Boots;
import fr.cejuba.stardew.object.Key;
import fr.cejuba.stardew.object.projectile.Fireball;
import fr.cejuba.stardew.object.shield.RustyShield;
import fr.cejuba.stardew.object.weapon.Axe;
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
        currentBoots = new Boots(gamePanel);
        projectile = new Fireball(gamePanel);
        // projectile = new Rock(gamePanel); TODO : Change to have a system with ammo
        attack = getAttack();
        defense = getDefense();
        defaultSpeed = getSpeed();
        speed = defaultSpeed;
    }

    public void setDefaultPosition() {
        worldX = gamePanel.tileSize * 23;
        worldY = gamePanel.tileSize * 21;
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
        inventory.add(new Key(gamePanel));
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

            up1 = setup("player/walking/player_up_1", gamePanel.tileSize, gamePanel.tileSize);
            up2 = setup("player/walking/player_up_2", gamePanel.tileSize, gamePanel.tileSize);
            down1 = setup("player/walking/player_down_1", gamePanel.tileSize, gamePanel.tileSize);
            down2 = setup("player/walking/player_down_2", gamePanel.tileSize, gamePanel.tileSize);
            right1 = setup("player/walking/player_right_1", gamePanel.tileSize, gamePanel.tileSize);
            right2 = setup("player/walking/player_right_2", gamePanel.tileSize, gamePanel.tileSize);
            left1 = setup("player/walking/player_right_1", gamePanel.tileSize, gamePanel.tileSize);
            left2 = setup("player/walking/player_right_2", gamePanel.tileSize, gamePanel.tileSize);

            System.out.println("Player images loaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getPlayerAttackImage() {
        try {
            System.out.println("Loading player attack images");
            if(currentWeapon.type == type_sword){
                attackUp0 = setup("player/attacking/boy_attack_up_1", gamePanel.tileSize, gamePanel.tileSize*2);
                attackUp1 = setup("player/attacking/boy_attack_up_2", gamePanel.tileSize, gamePanel.tileSize*2);
                attackDown0 = setup("player/attacking/boy_attack_down_1", gamePanel.tileSize, gamePanel.tileSize*2);
                attackDown1 = setup("player/attacking/boy_attack_down_2", gamePanel.tileSize, gamePanel.tileSize*2);
                attackRight0 = setup("player/attacking/boy_attack_right_1", gamePanel.tileSize*2, gamePanel.tileSize);
                attackRight1 = setup("player/attacking/boy_attack_right_2", gamePanel.tileSize*2, gamePanel.tileSize);
                attackLeft0 = setup("player/attacking/boy_attack_left_1", gamePanel.tileSize*2, gamePanel.tileSize);
                attackLeft1 = setup("player/attacking/boy_attack_left_2", gamePanel.tileSize*2, gamePanel.tileSize);
            }
            if(currentWeapon.type == type_axe){
                attackUp0 = setup("player/attacking/boy_axe_up_1", gamePanel.tileSize, gamePanel.tileSize*2);
                attackUp1 = setup("player/attacking/boy_axe_up_2", gamePanel.tileSize, gamePanel.tileSize*2);
                attackDown0 = setup("player/attacking/boy_axe_down_1", gamePanel.tileSize, gamePanel.tileSize*2);
                attackDown1 = setup("player/attacking/boy_axe_down_2", gamePanel.tileSize, gamePanel.tileSize*2);
                attackRight0 = setup("player/attacking/boy_axe_right_1", gamePanel.tileSize*2, gamePanel.tileSize);
                attackRight1 = setup("player/attacking/boy_axe_right_2", gamePanel.tileSize*2, gamePanel.tileSize);
                attackLeft0 = setup("player/attacking/boy_axe_left_1", gamePanel.tileSize*2, gamePanel.tileSize);
                attackLeft1 = setup("player/attacking/boy_axe_left_2", gamePanel.tileSize*2, gamePanel.tileSize);
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

            // InteractiveTile Collision
            int interactiveTileIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.interactiveTile);



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


        if(gamePanel.keyHandler.shotKeyPressed && !projectile.alive && shotAvailableCounter == 30 && projectile.haveRessource(this)){
            // Set Default Coordinates
            projectile.set(worldX, worldY, direction, true, this);

            // Subtract Resources
            projectile.subtractResource(this);

            // Check Vacancy
            for(int i = 0; i < gamePanel.projectile[1].length; i++){
                if(gamePanel.projectile[gamePanel.currentMap][i] == null){
                    gamePanel.projectile[gamePanel.currentMap][i] = projectile;
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
            gamePanel.gameState = gamePanel.gameOverState;
            gamePanel.ui.commandNumber = -1; // Prevent retry by accident when smashing ENTER key
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
            if(gamePanel.object[gamePanel.currentMap][index].type == type_pickUpOnly){
                gamePanel.object[gamePanel.currentMap][index].use(this);
                gamePanel.object[gamePanel.currentMap][index] = null;
            }
            // Inventory
            else{
                String text;

                if(inventory.size() != maxInventorySize){
                    inventory.add(gamePanel.object[gamePanel.currentMap][index]);
                    gamePanel.playSoundEffect(1);
                    text = "Got a " + gamePanel.object[gamePanel.currentMap][index].name + "!";
                }
                else {
                    text = "You cannot carry any more items.";
                }
                gamePanel.ui.addMessage(text);
                gamePanel.object[gamePanel.currentMap][index] = null;
            }
        }
    }

    public void interactNPC(int index) {
        if(gamePanel.keyHandler.enterPressed){
            if (index != 999) {
                attackCanceled = true;
                gamePanel.gameState = gamePanel.dialogueState;
                gamePanel.npc[gamePanel.currentMap][index].speak();
            }
        }
    }

    public void contactMonster(int index) {
        if (index != 999) {
            if(!invincible && !gamePanel.monster[gamePanel.currentMap][index].dying){
                gamePanel.playSoundEffect(6);
                int damage = gamePanel.monster[gamePanel.currentMap][index].attack - defense;
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
            if(!gamePanel.monster[gamePanel.currentMap][index].invincible){
                gamePanel.playSoundEffect(5);

                if(knockBackPower > 0){
                    knockBack(gamePanel.monster[gamePanel.currentMap][index], knockBackPower);
                }
                int damage = attack - gamePanel.monster[gamePanel.currentMap][index].defense;
                if(damage < 0){
                    damage = 0;
                }

                gamePanel.monster[gamePanel.currentMap][index].life -= damage;
                gamePanel.ui.addMessage(damage + " damage to " + gamePanel.monster[gamePanel.currentMap][index].name + ".");

                gamePanel.monster[gamePanel.currentMap][index].invincible = true;
                gamePanel.monster[gamePanel.currentMap][index].damageReaction();

                if(gamePanel.monster[gamePanel.currentMap][index].life <= 0){
                    gamePanel.monster[gamePanel.currentMap][index].dying = true;
                    gamePanel.ui.addMessage("You defeated " + gamePanel.monster[gamePanel.currentMap][index].name + "!");
                    gamePanel.ui.addMessage("Experience + " + gamePanel.monster[gamePanel.currentMap][index].name);
                    experience += gamePanel.monster[gamePanel.currentMap][index].experience;
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

        if(i != 999 && gamePanel.interactiveTile[gamePanel.currentMap][i].destructible && gamePanel.interactiveTile[gamePanel.currentMap][i].isCorrectItem(this) && !gamePanel.interactiveTile[gamePanel.currentMap][i].invincible){
            gamePanel.interactiveTile[gamePanel.currentMap][i].playSoundEnvironment();
            gamePanel.interactiveTile[gamePanel.currentMap][i].life --;
            gamePanel.interactiveTile[gamePanel.currentMap][i].invincible = true;

            generateParticle(gamePanel.interactiveTile[gamePanel.currentMap][i], gamePanel.interactiveTile[gamePanel.currentMap][i]);

            if(gamePanel.interactiveTile[gamePanel.currentMap][i].life <= 0){
                gamePanel.interactiveTile[gamePanel.currentMap][i] = gamePanel.interactiveTile[gamePanel.currentMap][i].getDestroyedForm();
            }
        }
    }

    public void damageProjectile(int i){
        if(i != 999){
            Entity projectile = gamePanel.projectile[gamePanel.currentMap][i];
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
            gamePanel.gameState = gamePanel.dialogueState;
            gamePanel.ui.currentDialogue = "You are level " + level + " now!\n" + "Max life +2, Strength +1, Dexterity +1";

        }
    }

    public void selectItem(){
        int itemIndex = gamePanel.ui.getItemIndexInInventory(gamePanel.ui.playerSlotColumn, gamePanel.ui.playerSlotRow);

        if(itemIndex < inventory.size()){
            Entity selectedItem = inventory.get(itemIndex);

            if(selectedItem.type == type_sword || selectedItem.type == type_axe){
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
            if(selectedItem.type == type_shield){
                currentShield = selectedItem;
                defense = getDefense();
            }
            if(selectedItem.type == type_boots){
                currentBoots = selectedItem;
                speed = getSpeed();
            }
            if(selectedItem.type == type_consumable){
                System.out.println("Potion used");
                selectedItem.use(this);
                inventory.remove(itemIndex);
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
                    image = (spriteNumber == 0) ? up1 : up2;
                }
                else{
                    tempScreenY -= gamePanel.tileSize;
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
                    tempScreenX -= gamePanel.tileSize;
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
}
