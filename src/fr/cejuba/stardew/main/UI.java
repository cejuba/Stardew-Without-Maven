package fr.cejuba.stardew.main;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.object.BronzeCoin;
import fr.cejuba.stardew.object.stats.Heart;
import fr.cejuba.stardew.object.stats.ManaCrystal;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


import java.text.DecimalFormat;
import java.util.ArrayList;

public class UI {

    GamePanel gamePanel;
    GraphicsContext graphicsContext;
    Font arial_40, arial_30, arial_80B, arial_32F, arial_96B;
    public boolean messageOn = false;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNumber = 0;
    public int titleScreenState = 0;
    public int playerSlotColumn = 0;
    public int playerSlotRow = 0;
    public int npcSlotColumn = 0;
    public int npcSlotRow = 0;
    int subState = 0;
    double counter = 0;
    public Entity npc;

    Image heart_full, heart_half, heart_blank, crystal_full, crystal_blank, gold;

    double playTime;
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        arial_40 = Font.font("Arial", 40);
        arial_30 = Font.font("Arial", 30);
        arial_80B = Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 80);
        arial_32F = Font.font("Arial", javafx.scene.text.FontWeight.NORMAL, 32);
        arial_96B = Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 96);

        Entity heart = new Heart(gamePanel);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
        Entity crystal = new ManaCrystal(gamePanel);
        crystal_full = crystal.image;
        crystal_blank = crystal.image2;
        Entity bronzeCoin = new BronzeCoin(gamePanel);
        gold = bronzeCoin.down2;
    }

    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }

    public void draw(GraphicsContext graphicsContext){
        this.graphicsContext = graphicsContext;

        graphicsContext.setFont(arial_40);
        graphicsContext.setFill(Color.WHITE);

        if(gamePanel.gameState == gamePanel.titleState){
            drawTitleScreen();
        } else if(gamePanel.gameState == gamePanel.playState){
            drawPlayerLife();
            drawPlayerMana();
            drawMessage();
        } else if(gamePanel.gameState == gamePanel.pauseState){
            drawPlayerLife();
            drawPlayerMana();
            drawPauseScreen();
        } else if(gamePanel.gameState == gamePanel.dialogueState){
            drawPlayerLife();
            drawPlayerMana();
            drawDialogueScreen();
        } else if(gamePanel.gameState == gamePanel.characterState){
            drawCharacterScreen();
            drawInventory(gamePanel.player, true);
        } else if(gamePanel.gameState == gamePanel.optionState){
            drawOptionScreen();
        } else if(gamePanel.gameState == gamePanel.gameOverState){
            drawGameOverScreen();
        } else if(gamePanel.gameState == gamePanel.transitionState){
            drawTransition();
        } else if(gamePanel.gameState == gamePanel.tradeState){
            drawTradeScreen();
        }
    }

    public void drawMessage(){
        int messageX = gamePanel.tileSize;
        int messageY = gamePanel.tileSize * 4;
        graphicsContext.setFont(new Font("Arial", 32F));

        for(int i = 0; i < message.size(); i++){
            if(message.get(i) != null){

                graphicsContext.setFill(Color.BLACK);
                graphicsContext.fillText(message.get(i), messageX + 2, messageY + 2);
                graphicsContext.setFill(Color.WHITE);
                graphicsContext.fillText(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 50;

                if(messageCounter.get(i) > 180){
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }

    }

    public void drawPlayerLife() {
        int x = gamePanel.tileSize / 2;
        int y = gamePanel.tileSize / 2;
        int i = 0;

        while (i < gamePanel.player.maxLife / 2) {
            graphicsContext.drawImage(heart_blank, x, y);
            i++;
            x += gamePanel.tileSize;
        }

        x = gamePanel.tileSize / 2;
        y = gamePanel.tileSize / 2;
        i = 0;

        while (i < gamePanel.player.life) {
            graphicsContext.drawImage(heart_half, x, y);
            i++;
            if (i < gamePanel.player.life) {
                graphicsContext.drawImage(heart_full, x, y);
            }
            i++;
            x += gamePanel.tileSize;
        }
    }

    public void drawPlayerMana() {
        int x = gamePanel.tileSize / 2;
        int y = gamePanel.tileSize * 2;
        int i = 0;

        while (i < gamePanel.player.maxMana) {
            graphicsContext.drawImage(crystal_blank, x, y);
            i++;
            x += 35;
        }

        x = gamePanel.tileSize / 2;
        y = gamePanel.tileSize * 2;
        i = 0;

        while (i < gamePanel.player.mana) {
            graphicsContext.drawImage(crystal_full, x, y);
            i++;
            x += 35;
        }
    }

    private void drawTitleScreen() {
        graphicsContext.setFill(Color.rgb(0, 0, 0));
        graphicsContext.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        if (titleScreenState == 0) {
            graphicsContext.setFill(Color.rgb(0, 0, 0));
            graphicsContext.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

            graphicsContext.setFont(arial_96B);
            String text = "STARDEW VALLEY";
            int x = getXCenteredText(text, graphicsContext);
            int y = gamePanel.tileSize * 3;

            graphicsContext.setFill(Color.GRAY);
            graphicsContext.fillText(text, x + 5, y + 5);

            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillText(text, x, y);

            x = gamePanel.screenWidth / 2 - gamePanel.tileSize;
            y += gamePanel.tileSize * 2;
            graphicsContext.drawImage(gamePanel.player.down1, x, y, gamePanel.tileSize * 2, gamePanel.tileSize * 2);

            graphicsContext.setFont(arial_40);

            text = "New Game";
            x = getXCenteredText(text, graphicsContext);
            y += gamePanel.tileSize * 4;
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillText(text, x, y);
            if (commandNumber == 0) {
                graphicsContext.setFill(Color.RED);
                graphicsContext.fillText(text, x, y);
                graphicsContext.fillText(">", x - gamePanel.tileSize, y);
            }

            text = "Load Game";
            x = getXCenteredText(text, graphicsContext);
            y += gamePanel.tileSize;
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillText(text, x, y);
            if (commandNumber == 1) {
                graphicsContext.setFill(Color.RED);
                graphicsContext.fillText(text, x, y);
                graphicsContext.fillText(">", x - gamePanel.tileSize, y);
            }

            text = "Quit";
            x = getXCenteredText(text, graphicsContext);
            y += gamePanel.tileSize;
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillText(text, x, y);
            if (commandNumber == 2) {
                graphicsContext.setFill(Color.RED);
                graphicsContext.fillText(text, x, y);
                graphicsContext.fillText(">", x - gamePanel.tileSize, y);
            }
        } else if (titleScreenState == 1) {

            graphicsContext.setFill(Color.WHITE);
            graphicsContext.setFont(Font.font("Arial", 42));
            String text = "Select your class!";
            int x = getXCenteredText(text, graphicsContext);
            int y = gamePanel.tileSize * 2;
            graphicsContext.fillText(text, x, y);

            text = "Fighter";
            x = getXCenteredText(text, graphicsContext);
            y += gamePanel.tileSize * 2;
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillText(text, x, y);
            if (commandNumber == 0) {
                graphicsContext.setFill(Color.RED);
                graphicsContext.fillText(text, x, y);
                graphicsContext.fillText(">", x - gamePanel.tileSize, y);
            }

            text = "Thief";
            y += gamePanel.tileSize * 2;
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillText(text, x, y);
            if (commandNumber == 1) {
                graphicsContext.setFill(Color.RED);
                graphicsContext.fillText(text, x, y);
                graphicsContext.fillText(">", x - gamePanel.tileSize, y);
            }
            text = "Sorcerer";
            y += gamePanel.tileSize * 2;
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillText(text, x, y);
            if (commandNumber == 2) {
                graphicsContext.setFill(Color.RED);
                graphicsContext.fillText(text, x, y);
                graphicsContext.fillText(">", x - gamePanel.tileSize, y);
            }

            text = "Back";
            x = getXCenteredText(text, graphicsContext);
            y += gamePanel.tileSize * 3;
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillText(text, x, y);
            if (commandNumber == 3) {
                graphicsContext.setFill(Color.RED);
                graphicsContext.fillText(text, x, y);
                graphicsContext.fillText(">", x - gamePanel.tileSize, y);
            }
        }
    }

    public void drawPauseScreen() {
        graphicsContext.setFont(arial_80B);
        String text = "PAUSED";
        int x = getXCenteredText(text, graphicsContext);
        int y = gamePanel.screenHeight / 2;

        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setFont(arial_40);
        graphicsContext.fillText(text, x, y);
    }

    public void drawDialogueScreen() {
        int x = gamePanel.tileSize * 2;
        int y = gamePanel.tileSize / 2;
        int width = gamePanel.screenWidth - (gamePanel.tileSize * 4);
        int height = gamePanel.tileSize * 4;
        drawSubWindows(x, y, width, height);

        graphicsContext.setFont(arial_32F);
        graphicsContext.setFill(Color.WHITE);
        x += gamePanel.tileSize;
        y += gamePanel.tileSize;

        for (String line : currentDialogue.split("\n")) {
            graphicsContext.fillText(line, x, y);
            y += 40;
        }
    }

    public void drawCharacterScreen(){
        final int frameX = gamePanel.tileSize;
        final int frameY = gamePanel.tileSize;
        final int frameWidth = gamePanel.tileSize * 5;
        final int frameHeight = gamePanel.tileSize * 12;
        drawSubWindows(frameX, frameY, frameWidth, frameHeight);

        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setFont(new Font("Arial", 32));

        int textX = frameX + 20;
        int textY = frameY + gamePanel.tileSize;
        final int lineHeight = 32; // Need to be the same as the font size

        // Names
        graphicsContext.fillText("Level", textX, textY);
        textY += lineHeight;
        graphicsContext.fillText("Life", textX, textY);
        textY += lineHeight;
        graphicsContext.fillText("Mana", textX, textY);
        textY += lineHeight;
        graphicsContext.fillText("Strength", textX, textY);
        textY += lineHeight;
        graphicsContext.fillText("Dexterity", textX, textY);
        textY += lineHeight;
        graphicsContext.fillText("Agility", textX, textY);
        textY += lineHeight;
        graphicsContext.fillText("Attack", textX, textY);
        textY += lineHeight;
        graphicsContext.fillText("Defense", textX, textY);
        textY += lineHeight;
        graphicsContext.fillText("Speed", textX, textY);
        textY += lineHeight;
        graphicsContext.fillText("Experience", textX, textY);
        textY += lineHeight;
        graphicsContext.fillText("Next Level", textX, textY);
        textY += lineHeight;
        graphicsContext.fillText("Gold", textX, textY);
        textY += lineHeight + 10;
        graphicsContext.fillText("Weapon", textX, textY);
        textY += lineHeight + 15;
        graphicsContext.fillText("Shield", textX, textY);
        textY += lineHeight + 15;
        graphicsContext.fillText("Boots", textX, textY);

        // Reset
        textY = frameY + gamePanel.tileSize;

        // Values
        int tailX = frameX + frameWidth - 30;
        String value = String.valueOf(gamePanel.player.level);
        textX = getXAlignedToRightText(value, tailX);
        graphicsContext.fillText(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gamePanel.player.life + "/" + gamePanel.player.maxLife);
        textX = getXAlignedToRightText(value, tailX);
        graphicsContext.fillText(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gamePanel.player.mana + "/" + gamePanel.player.maxMana);
        textX = getXAlignedToRightText(value, tailX);
        graphicsContext.fillText(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gamePanel.player.strength);
        textX = getXAlignedToRightText(value, tailX);
        graphicsContext.fillText(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gamePanel.player.dexterity);
        textX = getXAlignedToRightText(value, tailX);
        graphicsContext.fillText(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gamePanel.player.agility);
        textX = getXAlignedToRightText(value, tailX);
        graphicsContext.fillText(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gamePanel.player.attack);
        textX = getXAlignedToRightText(value, tailX);
        graphicsContext.fillText(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gamePanel.player.defense);
        textX = getXAlignedToRightText(value, tailX);
        graphicsContext.fillText(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gamePanel.player.speed);
        textX = getXAlignedToRightText(value, tailX);
        graphicsContext.fillText(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gamePanel.player.experience);
        textX = getXAlignedToRightText(value, tailX);
        graphicsContext.fillText(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gamePanel.player.nextLevelExperience);
        textX = getXAlignedToRightText(value, tailX);
        graphicsContext.fillText(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gamePanel.player.gold);
        textX = getXAlignedToRightText(value, tailX);
        graphicsContext.fillText(value, textX, textY);


        textY += lineHeight;
        graphicsContext.drawImage(gamePanel.player.currentWeapon.down2, tailX - gamePanel.tileSize, textY-24);

        textY += gamePanel.tileSize;
        graphicsContext.drawImage(gamePanel.player.currentShield.down2, tailX - gamePanel.tileSize, textY-24);

        textY += gamePanel.tileSize;
        graphicsContext.drawImage(gamePanel.player.currentBoots.down2, tailX - gamePanel.tileSize, textY-24);
    }

    public void drawGameOverScreen(){
        graphicsContext.setFill(new Color(0,0,0,0.4));
        graphicsContext.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        int x, y;
        String text;
        graphicsContext.setFont(arial_80B);
        text = "GAME OVER";

        // Shadow
        graphicsContext.setFill(Color.BLACK);
        x = getXCenteredText(text, graphicsContext);
        y = gamePanel.tileSize*4;
        graphicsContext.fillText(text, x, y);

        // Main
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText(text, x-4, y-4);

        // Retry
        graphicsContext.setFont(arial_40);
        text = "Retry";
        x = getXCenteredText(text, graphicsContext);
        y = gamePanel.screenHeight - gamePanel.tileSize*4;
        graphicsContext.fillText(text, x, y);
        if(commandNumber == 0){
            graphicsContext.fillText(">", x - gamePanel.tileSize, y);
        }

        // Back to Title
        text = "Back to Title";
        x = getXCenteredText(text, graphicsContext);
        y += gamePanel.tileSize*2;
        graphicsContext.fillText(text, x, y);
        if(commandNumber == 1){
            graphicsContext.fillText(">", x - gamePanel.tileSize, y);
        }
    }

    public void drawInventory(Entity entity, boolean cursorShown){

        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotColumn = 0;
        int slotRow = 0;
        if(entity == gamePanel.player){
            frameWidth = gamePanel.tileSize * 6;
            frameHeight = gamePanel.tileSize * 5;
            frameX = gamePanel.screenWidth - frameWidth - gamePanel.tileSize ;
            frameY = gamePanel.tileSize;
            slotColumn = playerSlotColumn;
            slotRow = playerSlotRow;
        }
        else{
            frameWidth = gamePanel.tileSize * 6;
            frameHeight = gamePanel.tileSize * 5;
            frameX = gamePanel.tileSize * 2;
            frameY = gamePanel.tileSize;
            slotColumn = npcSlotColumn;
            slotRow = npcSlotRow;
        }

        // Frame
        drawSubWindows(frameX, frameY, frameWidth, frameHeight);

        // Slots
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = gamePanel.tileSize+3;

        // Draw Player's inventory
        for(int i = 0; i < entity.inventory.size(); i++){
            if(entity.inventory.get(i) == entity.currentWeapon || entity.inventory.get(i) == entity.currentShield || entity.inventory.get(i) == entity.currentBoots){
                graphicsContext.setFill(Color.GOLD);
                graphicsContext.fillRoundRect(slotX, slotY, gamePanel.tileSize, gamePanel.tileSize, 10, 10);
            }
            graphicsContext.drawImage(entity.inventory.get(i).down2, slotX, slotY);
            slotX += slotSize;
            if(i % 5 == 4){
                slotX = slotXStart;
                slotY += slotSize;
            }
        }

        // Cursor
        if(cursorShown){
            int cursorX = slotXStart + (slotColumn * slotSize);
            int cursorY = slotYStart + (slotRow * slotSize);
            int cursorWidth = gamePanel.tileSize;
            int cursorHeight = gamePanel.tileSize;

            // Draw cursor
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.setLineWidth(3);
            graphicsContext.strokeRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            // Description Frame
            final int descriptionFrameWidth = gamePanel.tileSize * 6;
            final int descriptionFrameHeight = gamePanel.tileSize * 3;
            final int descriptionFrameX = frameX;
            final int descriptionFrameY = frameHeight + gamePanel.tileSize;

            // Description
            int textX = descriptionFrameX + 20;
            int textY = descriptionFrameY + gamePanel.tileSize;
            graphicsContext.setFont(new Font("Arial", 28F));

            int itemIndex = getItemIndexInInventory(slotColumn, slotRow);

            if(itemIndex< entity.inventory.size()){
                drawSubWindows(descriptionFrameX, descriptionFrameY, descriptionFrameWidth, descriptionFrameHeight);

                for(String line : entity.inventory.get(itemIndex).description.split("\n")){
                    graphicsContext.setFill(Color.WHITE);
                    graphicsContext.fillText(line, textX, textY);
                    textY += 32;
                }
            }
        }
    }

    public void drawOptionScreen(){
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setFont(new Font("Arial", 32F));

        // Sub Window

        int frameWidth = gamePanel.screenWidth / 2;
        int frameHeight = gamePanel.screenHeight - gamePanel.tileSize * 2;
        int frameX =  frameWidth - (gamePanel.screenWidth - frameWidth) / 2;
        int frameY = gamePanel.tileSize;
        drawSubWindows(frameX, frameY, frameWidth, frameHeight);

        switch(subState){
            case 0 -> options_top(frameX, frameY);
            case 1 -> options_fullScreenNotification(frameX, frameY);
            case 2 -> options_control(frameX, frameY);
            case 3 -> options_endGameConfirmation(frameX, frameY);
        }

        gamePanel.keyHandler.enterPressed = false;
    }

    public void drawTransition(){
        counter += 0.02;
        graphicsContext.setFill(Color.rgb(0,0,0,counter));
        graphicsContext.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        if(counter >= 0.9){
            counter = 0;
            gamePanel.gameState = gamePanel.playState;
            gamePanel.currentMap = gamePanel.eventHandler.tempMap;
            gamePanel.player.worldX = gamePanel.eventHandler.tempCol * gamePanel.tileSize;
            gamePanel.player.worldY = gamePanel.eventHandler.tempRow * gamePanel.tileSize;
            gamePanel.eventHandler.previousEventX = gamePanel.player.worldX;
            gamePanel.eventHandler.previousEventY = gamePanel.player.worldY;
        }
    }

    public void drawTradeScreen(){
        switch(subState){
            case 0 -> trade_select();
            case 1 -> trade_buy();
            case 2 -> trade_sell();
        }
        gamePanel.keyHandler.enterPressed = false;
    }

    public void options_top(int frameX, int frameY){

        int textX;
        int textY;

        // Title
        String text = "Options";
        textX = getXCenteredText(text, graphicsContext);
        textY = frameY + gamePanel.tileSize;
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText(text, textX, textY);

        // Full Screen On/Off
        textX = frameX + gamePanel.tileSize;
        textY += gamePanel.tileSize * 2;
        text = "Full Screen";
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText(text, textX, textY);
        if(commandNumber == 0){
            graphicsContext.fillText(">", textX - 25, textY);
            if(gamePanel.keyHandler.enterPressed){
                gamePanel.fullScreenOn = !gamePanel.fullScreenOn;
                subState = 1;
            }
        }

        // Music
        textY += gamePanel.tileSize;
        text = "Music";
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText(text, textX, textY);
        if(commandNumber == 1){
            graphicsContext.fillText(">", textX - 25, textY);
        }

        // Sound Effect
        textY += gamePanel.tileSize;
        text = "Sound Effect";
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText(text, textX, textY);
        if(commandNumber == 2){
            graphicsContext.fillText(">", textX - 25, textY);
        }

        // Control
        textY += gamePanel.tileSize;
        text = "Control";
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText(text, textX, textY);
        if(commandNumber == 3){
            graphicsContext.fillText(">", textX - 25, textY);
            if(gamePanel.keyHandler.enterPressed){
                subState = 2;
                commandNumber = 0;
            }
        }

        // End Game
        textY += gamePanel.tileSize;
        text = "End Game";
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText(text, textX, textY);
        if(commandNumber == 4){
            graphicsContext.fillText(">", textX - 25, textY);
            if(gamePanel.keyHandler.enterPressed){
                subState = 3;
                commandNumber = 0;
            }
        }

        // Back
        textY += gamePanel.tileSize * 2;
        text = "Back";
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText(text, textX, textY);
        if(commandNumber == 5){
            graphicsContext.fillText(">", textX - 25, textY);
            if(gamePanel.keyHandler.enterPressed){
                gamePanel.gameState = gamePanel.playState;
                commandNumber = 0;
            }
        }

        // Full Screen Check Box
        textX = frameX + gamePanel.tileSize * 10;
        textY = (int) (frameY + gamePanel.tileSize * 2.5);
        graphicsContext.setLineWidth(3);
        graphicsContext.strokeRect(textX, textY, 24, 24);
        if(gamePanel.fullScreenOn){
            graphicsContext.fillRect(textX, textY, 24, 24);
        }

        // Music Volume
        textY += gamePanel.tileSize;
        graphicsContext.strokeRect(textX, textY, 120, 24);
        int volumeWidth = 24 * gamePanel.music.volumeScale;
        graphicsContext.fillRect(textX, textY, volumeWidth, 24);

        // Sound Effect Volume
        textY += gamePanel.tileSize;
        graphicsContext.strokeRect(textX, textY, 120, 24);
        volumeWidth = 24 * gamePanel.soundEffect.volumeScale;
        graphicsContext.fillRect(textX, textY, volumeWidth, 24);

        gamePanel.config.saveConfig();
    }

    private void options_fullScreenNotification(int frameX, int frameY) {
        int textX = frameX + gamePanel.tileSize;
        int textY = frameY + gamePanel.tileSize * 2;

        currentDialogue = "The change will take effect after \n restarting the game.";
        for (String line : currentDialogue.split("\n")){
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillText(line, textX, textY);
            textY += 32;
        }

        // Back

        textY += gamePanel.tileSize * 2;
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText("Back", textX, textY);
        if(commandNumber == 0){
            graphicsContext.fillText(">", textX - 25, textY);
            if(gamePanel.keyHandler.enterPressed){
                subState = 0;
                commandNumber = 0;
            }
        }
    }

    public void options_control(int frameX, int frameY) {
        int textX, textY;

        // Title
        String text = "Control";
        textX = getXCenteredText(text, graphicsContext);
        textY = frameY + gamePanel.tileSize;
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText(text, textX, textY);

        textX = frameX + gamePanel.tileSize;
        textY += gamePanel.tileSize;
        graphicsContext.fillText("Move", textX, textY);
        textY += gamePanel.tileSize;
        graphicsContext.fillText("Confirm/Attack", textX, textY);
        textY += gamePanel.tileSize;
        graphicsContext.fillText("Shoot/Cast", textX, textY);
        textY += gamePanel.tileSize;
        graphicsContext.fillText("Character Screen", textX, textY);
        textY += gamePanel.tileSize;
        graphicsContext.fillText("Pause", textX, textY);
        textY += gamePanel.tileSize;
        graphicsContext.fillText("Options", textX, textY);

        textX = frameX + gamePanel.tileSize * 7;
        textY = frameY + gamePanel.tileSize * 2;
        graphicsContext.fillText("Arrow Keys/ZQSD", textX, textY);
        textY += gamePanel.tileSize;
        graphicsContext.fillText("Enter", textX, textY);
        textY += gamePanel.tileSize;
        graphicsContext.fillText("F", textX, textY);
        textY += gamePanel.tileSize;
        graphicsContext.fillText("C", textX, textY);
        textY += gamePanel.tileSize;
        graphicsContext.fillText("P", textX, textY);
        textY += gamePanel.tileSize;
        graphicsContext.fillText("ESCAPE", textX, textY);

        // Back

        textX = frameX + gamePanel.tileSize;
        textY = frameY + gamePanel.tileSize * 9;
        graphicsContext.fillText("Back", textX, textY);
        if(commandNumber == 0){
            graphicsContext.fillText(">", textX - 25, textY);
            if(gamePanel.keyHandler.enterPressed){
                subState = 0;
                commandNumber = 3;
            }
        }
    }

    public void options_endGameConfirmation(int frameX, int frameY){
        int textX = frameX + gamePanel.tileSize;
        int textY = frameY + gamePanel.tileSize * 3;

        currentDialogue = "Quit the game and \nreturn to title screen?";

        for(String line : currentDialogue.split("\n")){
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillText(line, textX, textY);
            textY += 32;
        }

        // Yes
        String text = "Yes";
        textX = getXCenteredText(text, graphicsContext);
        textY += gamePanel.tileSize * 3;
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText(text, textX, textY);
        if(commandNumber == 0){
            graphicsContext.fillText(">", textX - 25, textY);
            if(gamePanel.keyHandler.enterPressed){
                subState = 0;
                gamePanel.ui.titleScreenState = 0;
                gamePanel.gameState = gamePanel.titleState;
                gamePanel.stopMusic();
            }
        }

        // No
        text = "No";
        textX = getXCenteredText(text, graphicsContext);
        textY += gamePanel.tileSize * 3;
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText(text, textX, textY);
        if(commandNumber == 1){
            graphicsContext.fillText(">", textX - 25, textY);
            if(gamePanel.keyHandler.enterPressed){
                subState = 0;
                commandNumber = 4;
            }
        }

    }

    public void trade_select(){
        drawDialogueScreen();

        // Draw Options
        int width = (int) (gamePanel.tileSize * 3.5);
        int height = (int) (gamePanel.tileSize * 3.5);
        int x = gamePanel.screenWidth - width - gamePanel.tileSize;
        int y = gamePanel.tileSize * 4;
        drawSubWindows(x, y, width, height);

        // Draw Text
        x += gamePanel.tileSize;
        y += gamePanel.tileSize;
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText("Buy", x, y);
        if(commandNumber == 0){
            graphicsContext.fillText(">", x - 24, y);
            if(gamePanel.keyHandler.enterPressed){
                subState = 1;
            }
        }
        y += gamePanel.tileSize;
        graphicsContext.fillText("Sell", x, y);
        if(commandNumber == 1){
            graphicsContext.fillText(">", x - 24, y);
            if(gamePanel.keyHandler.enterPressed){
                subState = 2;
            }
        }
        y += gamePanel.tileSize;
        graphicsContext.fillText("Leave", x, y);
        if(commandNumber == 2){
            graphicsContext.fillText(">", x - 24, y);
            if(gamePanel.keyHandler.enterPressed){
                commandNumber = 0;
                gamePanel.gameState = gamePanel.dialogueState;
                currentDialogue = "Come again !";
            }
        }


    }

    public void trade_buy(){
        // Draw Player Inventory
        drawInventory(gamePanel.player, false);
        // Draw NPC Inventory
        drawInventory(npc, true);

        // Draw Hint Window
        int width = gamePanel.tileSize * 6;
        int height = gamePanel.tileSize * 2;
        int x = gamePanel.tileSize * 2;
        int y = gamePanel.tileSize * 9;
        drawSubWindows(x, y, width, height);
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText("[ESC] Back", x + 24, y + 60);

        // Draw Coin Window
        x = gamePanel.screenWidth - width - gamePanel.tileSize;
        drawSubWindows(x, y, width, height);
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText("Your Gold : " + gamePanel.player.gold, x + 24, y + 60);

        // Draw Price Window
        int itemIndex = getItemIndexInInventory(npcSlotColumn, npcSlotRow);
        if(itemIndex < npc.inventory.size()){
            x = (int) (gamePanel.tileSize * 5.5);
            y = (int) (gamePanel.tileSize * 5.5);
            width = (int) (gamePanel.tileSize * 2.5);
            height = gamePanel.tileSize;
            drawSubWindows(x, y, width, height);
            graphicsContext.drawImage(gold, x+10, y+8, 32, 32);

            int price = npc.inventory.get(itemIndex).price;
            String text = "" + price;
            x = getXAlignedToRightText(text, gamePanel.tileSize * 8-20);
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillText(text, x, y + 32);

            // Buy an item
            if(gamePanel.keyHandler.enterPressed){
                if(npc.inventory.get(itemIndex).price > gamePanel.player.gold){
                    subState = 0;
                    gamePanel.gameState = gamePanel.dialogueState;
                    currentDialogue = "You need more coin to buy that!";
                    drawDialogueScreen();
                }
                else if(gamePanel.player.inventory.size() >= gamePanel.player.maxInventorySize){
                    subState = 0;
                    gamePanel.gameState = gamePanel.dialogueState;
                    currentDialogue = "Your inventory is full!";
                    drawDialogueScreen();
                }
                else{
                    gamePanel.player.gold -= npc.inventory.get(itemIndex).price;
                    gamePanel.player.inventory.add(npc.inventory.get(itemIndex));
                }
            }
        }
    }

    public void trade_sell(){
        drawInventory(gamePanel.player, true);

        int x, y, width, height;

        // Draw Hint Window
        width = gamePanel.tileSize * 6;
        height = gamePanel.tileSize * 2;
        x = gamePanel.tileSize * 2;
        y = gamePanel.tileSize * 9;
        drawSubWindows(x, y, width, height);
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText("[ESC] Back", x + 24, y + 60);

        // Draw Coin Window
        x = gamePanel.screenWidth - width - gamePanel.tileSize;
        drawSubWindows(x, y, width, height);
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText("Your Gold : " + gamePanel.player.gold, x + 24, y + 60);

        // Draw Price Window
        int itemIndex = getItemIndexInInventory(playerSlotColumn, playerSlotRow);
        if(itemIndex < gamePanel.player.inventory.size()){
            y = (int) (gamePanel.tileSize * 5.5);
            width = (int) (gamePanel.tileSize * 2.5);
            height = gamePanel.tileSize;
            x = gamePanel.screenWidth - width - gamePanel.tileSize;
            drawSubWindows(x, y, width, height);
            graphicsContext.drawImage(gold, x+10, y+8, 32, 32);

            int price = (int) Math.round(gamePanel.player.inventory.get(itemIndex).price * 0.8);
            String text = "" + price;
            x = getXAlignedToRightText(text, x + width - 20);
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillText(text, x, y + 32);

            // Sell an item
            if(gamePanel.keyHandler.enterPressed){
                if(gamePanel.player.inventory.get(itemIndex) == gamePanel.player.currentWeapon || gamePanel.player.inventory.get(itemIndex) == gamePanel.player.currentShield || gamePanel.player.inventory.get(itemIndex) == gamePanel.player.currentBoots){
                    commandNumber = 0;
                    subState = 0;
                    gamePanel.gameState = gamePanel.dialogueState;
                    currentDialogue = "You can't sell equipped items!";
                    drawDialogueScreen();
                }
                else{
                    gamePanel.player.gold += price;
                    gamePanel.player.inventory.remove(itemIndex);
                }
            }
        }


    }

    public int getItemIndexInInventory(int slotColum, int slotRow){
        return slotColum + slotRow * 5;
    }

    public void drawSubWindows(int x, int y, int width, int height) {
        graphicsContext.setFill(Color.rgb(0, 0, 0, 0.8));
        graphicsContext.fillRoundRect(x, y, width, height, 35, 35);

        graphicsContext.setStroke(Color.rgb(255,255,255));
        graphicsContext.setLineWidth(5);
        graphicsContext.strokeRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }

    public int getXCenteredText(String text, GraphicsContext graphicsContext){
        Text tempText = new Text(text);
        tempText.setFont(graphicsContext.getFont());
        double width = tempText.getLayoutBounds().getWidth();
        return (int)(((double) gamePanel.screenWidth/2) - (width/2));
    }

    public int getXAlignedToRightText(String text, int tailX){
        Text tempText = new Text(text);
        tempText.setFont(graphicsContext.getFont());
        double width = tempText.getLayoutBounds().getWidth();
        return (int)(((double) tailX - width));
    }

}
