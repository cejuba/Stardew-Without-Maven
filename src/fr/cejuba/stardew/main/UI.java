package fr.cejuba.stardew.main;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.environment.DayStates;
import fr.cejuba.stardew.environment.Lighting;
import fr.cejuba.stardew.object.money.BronzeCoin;
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

    private final GamePanel gamePanel;
    private GraphicsContext graphicsContext;
    private final Font arial_40, arial_80B, arial_32F, arial_96B;
    private boolean messageOn = false;
    private final ArrayList<String> message = new ArrayList<>();
    private final ArrayList<Integer> messageCounter = new ArrayList<>();
    private boolean gameFinished = false;
    private String currentDialogue = "";
    private int commandNumber = 0;
    private int titleScreenState = 0;
    private int playerSlotColumn = 0;
    private int playerSlotRow = 0;
    private int npcSlotColumn = 0;
    private int npcSlotRow = 0;
    private int subState = 0;
    private double counter = 0;
    private Entity npc;

    private final Image heart_full, heart_half, heart_blank, crystal_full, crystal_blank, gold;

    private double playTime;
    private final DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        arial_40 = Font.font("Arial", 40);
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

        switch(gamePanel.getGameState()){
            case GameState.TITLE -> drawTitleScreen();
            case GameState.PLAY -> {
                drawPlayerLife();
                drawPlayerMana();
                drawMessage();
            }
            case GameState.PAUSE -> {
                drawPlayerLife();
                drawPlayerMana();
                drawPauseScreen();
            }
            case GameState.DIALOGUE -> {
                drawPlayerLife();
                drawPlayerMana();
                drawDialogueScreen();
            }
            case GameState.INVENTORY -> {
                drawCharacterScreen();
                drawInventory(gamePanel.player, true);
            }
            case GameState.OPTION -> drawOptionScreen();
            case GameState.GAMEOVER -> drawGameOverScreen();
            case GameState.TRANSITION -> drawTransition();
            case GameState.TRADE -> drawTradeScreen();
            case GameState.SLEEP -> drawSleepScreen();
        }
    }

    public void drawMessage(){
        int messageX = gamePanel.getTileSize();
        int messageY = gamePanel.getTileSize() * 4;
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
        int x = gamePanel.getTileSize() / 2;
        int y = gamePanel.getTileSize() / 2;
        int i = 0;

        while (i < gamePanel.player.maxLife / 2) {
            graphicsContext.drawImage(heart_blank, x, y);
            i++;
            x += gamePanel.getTileSize();
        }

        x = gamePanel.getTileSize() / 2;
        y = gamePanel.getTileSize() / 2;
        i = 0;

        while (i < gamePanel.player.life) {
            graphicsContext.drawImage(heart_half, x, y);
            i++;
            if (i < gamePanel.player.life) {
                graphicsContext.drawImage(heart_full, x, y);
            }
            i++;
            x += gamePanel.getTileSize();
        }
    }

    public void drawPlayerMana() {
        int x = gamePanel.getTileSize() / 2;
        int y = gamePanel.getTileSize() * 2;
        int i = 0;

        while (i < gamePanel.player.maxMana) {
            graphicsContext.drawImage(crystal_blank, x, y);
            i++;
            x += 35;
        }

        x = gamePanel.getTileSize() / 2;
        y = gamePanel.getTileSize() * 2;
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
            int y = gamePanel.getTileSize() * 3;

            graphicsContext.setFill(Color.GRAY);
            graphicsContext.fillText(text, x + 5, y + 5);

            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillText(text, x, y);

            x = gamePanel.screenWidth / 2 - gamePanel.getTileSize();
            y += gamePanel.getTileSize() * 2;
            graphicsContext.drawImage(gamePanel.player.down1, x, y, gamePanel.getTileSize() * 2, gamePanel.getTileSize() * 2);

            graphicsContext.setFont(arial_40);

            text = "New Game";
            x = getXCenteredText(text, graphicsContext);
            y += gamePanel.getTileSize() * 4;
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillText(text, x, y);
            if (commandNumber == 0) {
                graphicsContext.setFill(Color.RED);
                graphicsContext.fillText(text, x, y);
                graphicsContext.fillText(">", x - gamePanel.getTileSize(), y);
            }

            text = "Load Game";
            x = getXCenteredText(text, graphicsContext);
            y += gamePanel.getTileSize();
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillText(text, x, y);
            if (commandNumber == 1) {
                graphicsContext.setFill(Color.RED);
                graphicsContext.fillText(text, x, y);
                graphicsContext.fillText(">", x - gamePanel.getTileSize(), y);
            }

            text = "Quit";
            x = getXCenteredText(text, graphicsContext);
            y += gamePanel.getTileSize();
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillText(text, x, y);
            if (commandNumber == 2) {
                graphicsContext.setFill(Color.RED);
                graphicsContext.fillText(text, x, y);
                graphicsContext.fillText(">", x - gamePanel.getTileSize(), y);
            }
        } else if (titleScreenState == 1) {

            graphicsContext.setFill(Color.WHITE);
            graphicsContext.setFont(Font.font("Arial", 42));
            String text = "Select your class!";
            int x = getXCenteredText(text, graphicsContext);
            int y = gamePanel.getTileSize() * 2;
            graphicsContext.fillText(text, x, y);

            text = "Fighter";
            x = getXCenteredText(text, graphicsContext);
            y += gamePanel.getTileSize() * 2;
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillText(text, x, y);
            if (commandNumber == 0) {
                graphicsContext.setFill(Color.RED);
                graphicsContext.fillText(text, x, y);
                graphicsContext.fillText(">", x - gamePanel.getTileSize(), y);
            }

            text = "Thief";
            y += gamePanel.getTileSize() * 2;
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillText(text, x, y);
            if (commandNumber == 1) {
                graphicsContext.setFill(Color.RED);
                graphicsContext.fillText(text, x, y);
                graphicsContext.fillText(">", x - gamePanel.getTileSize(), y);
            }
            text = "Sorcerer";
            y += gamePanel.getTileSize() * 2;
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillText(text, x, y);
            if (commandNumber == 2) {
                graphicsContext.setFill(Color.RED);
                graphicsContext.fillText(text, x, y);
                graphicsContext.fillText(">", x - gamePanel.getTileSize(), y);
            }

            text = "Back";
            x = getXCenteredText(text, graphicsContext);
            y += gamePanel.getTileSize() * 3;
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillText(text, x, y);
            if (commandNumber == 3) {
                graphicsContext.setFill(Color.RED);
                graphicsContext.fillText(text, x, y);
                graphicsContext.fillText(">", x - gamePanel.getTileSize(), y);
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
        int x = gamePanel.getTileSize() * 2;
        int y = gamePanel.getTileSize() / 2;
        int width = gamePanel.screenWidth - (gamePanel.getTileSize() * 4);
        int height = gamePanel.getTileSize() * 4;
        drawSubWindows(x, y, width, height);

        graphicsContext.setFont(arial_32F);
        graphicsContext.setFill(Color.WHITE);
        x += gamePanel.getTileSize();
        y += gamePanel.getTileSize();

        for (String line : currentDialogue.split("\n")) {
            graphicsContext.fillText(line, x, y);
            y += 40;
        }
    }

    public void drawCharacterScreen(){
        final int frameX = gamePanel.getTileSize();
        final int frameY = gamePanel.getTileSize();
        final int frameWidth = gamePanel.getTileSize() * 5;
        final int frameHeight = gamePanel.getTileSize() * 12;
        drawSubWindows(frameX, frameY, frameWidth, frameHeight);

        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setFont(new Font("Arial", 32));

        int textX = frameX + 20;
        int textY = frameY + gamePanel.getTileSize();
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
        textY = frameY + gamePanel.getTileSize();

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
        graphicsContext.drawImage(gamePanel.player.currentWeapon.down2, tailX - gamePanel.getTileSize(), textY-24);

        textY += gamePanel.getTileSize();
        graphicsContext.drawImage(gamePanel.player.currentShield.down2, tailX - gamePanel.getTileSize(), textY-24);

        textY += gamePanel.getTileSize();
        graphicsContext.drawImage(gamePanel.player.currentBoots.down2, tailX - gamePanel.getTileSize(), textY-24);
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
        y = gamePanel.getTileSize()*4;
        graphicsContext.fillText(text, x, y);

        // Main
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText(text, x-4, y-4);

        // Retry
        graphicsContext.setFont(arial_40);
        text = "Retry";
        x = getXCenteredText(text, graphicsContext);
        y = gamePanel.screenHeight - gamePanel.getTileSize()*4;
        graphicsContext.fillText(text, x, y);
        if(commandNumber == 0){
            graphicsContext.fillText(">", x - gamePanel.getTileSize(), y);
        }

        // Back to Title
        text = "Back to Title";
        x = getXCenteredText(text, graphicsContext);
        y += gamePanel.getTileSize()*2;
        graphicsContext.fillText(text, x, y);
        if(commandNumber == 1){
            graphicsContext.fillText(">", x - gamePanel.getTileSize(), y);
        }
    }

    public void drawInventory(Entity entity, boolean cursorShown){

        int frameX, frameY, frameWidth, frameHeight, slotColumn, slotRow;
        if(entity == gamePanel.player){
            frameWidth = gamePanel.getTileSize() * 6;
            frameHeight = gamePanel.getTileSize() * 5;
            frameX = gamePanel.screenWidth - frameWidth - gamePanel.getTileSize() ;
            frameY = gamePanel.getTileSize();
            slotColumn = playerSlotColumn;
            slotRow = playerSlotRow;
        }
        else{
            frameWidth = gamePanel.getTileSize() * 6;
            frameHeight = gamePanel.getTileSize() * 5;
            frameX = gamePanel.getTileSize() * 2;
            frameY = gamePanel.getTileSize();
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
        int slotSize = gamePanel.getTileSize()+3;

        // Draw Player's inventory
        for(int i = 0; i < entity.inventory.size(); i++){

            // Equip cursor
            if(entity.inventory.get(i) == entity.currentWeapon || entity.inventory.get(i) == entity.currentShield || entity.inventory.get(i) == entity.currentBoots || entity.inventory.get(i) == entity.currentLight){
                graphicsContext.setFill(Color.GOLD);
                if(entity.inventory.get(i).getType() == Type.LIGHT){
                    graphicsContext.setFill(Color.BLUE);
                }
                graphicsContext.fillRoundRect(slotX, slotY, gamePanel.getTileSize(), gamePanel.getTileSize(), 10, 10);
            }
            graphicsContext.drawImage(entity.inventory.get(i).down2, slotX, slotY);

            // Display amount
            if(entity.inventory.get(i).amount > 1){ // If merchant's inventory display player items' amount add this condition : && entity == gamePanel.player
                graphicsContext.setFont(arial_32F);
                int amountX;
                int amountY;

                String s = "" + entity.inventory.get(i).amount;
                amountX = getXAlignedToRightText(s, slotX + 44);
                amountY = slotY + gamePanel.getTileSize();

                // Shadow
                graphicsContext.setFill(Color.rgb(60,60,60));
                graphicsContext.fillText(s, amountX, amountY);

                // Number
                graphicsContext.setFill(Color.WHITE);
                graphicsContext.fillText(s, amountX-3, amountY-3);
            }

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
            int cursorWidth = gamePanel.getTileSize();
            int cursorHeight = gamePanel.getTileSize();

            // Draw cursor
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.setLineWidth(3);
            graphicsContext.strokeRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            // Description Frame
            final int descriptionFrameWidth = gamePanel.getTileSize() * 6;
            final int descriptionFrameHeight = gamePanel.getTileSize() * 3;
            final int descriptionFrameX = frameX;
            final int descriptionFrameY = frameHeight + gamePanel.getTileSize();

            // Description
            int textX = descriptionFrameX + 20;
            int textY = descriptionFrameY + gamePanel.getTileSize();
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
        int frameHeight = gamePanel.screenHeight - gamePanel.getTileSize() * 2;
        int frameX =  frameWidth - (gamePanel.screenWidth - frameWidth) / 2;
        int frameY = gamePanel.getTileSize();
        drawSubWindows(frameX, frameY, frameWidth, frameHeight);

        switch(subState){
            case 0 -> options_top(frameX, frameY);
            case 1 -> options_fullScreenNotification(frameX, frameY);
            case 2 -> options_control(frameX, frameY);
            case 3 -> options_endGameConfirmation(frameX, frameY);
        }

        gamePanel.keyHandler.setEnterPressed(false);
    }

    public void drawTransition(){
        counter += 0.02;
        graphicsContext.setFill(Color.rgb(0,0,0,counter));
        graphicsContext.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        if(counter >= 0.9){
            counter = 0;
            gamePanel.setGameState(GameState.PLAY);
            gamePanel.currentMap = gamePanel.eventHandler.getTempMap();
            gamePanel.player.worldX = gamePanel.eventHandler.getTempCol() * gamePanel.getTileSize();
            gamePanel.player.worldY = gamePanel.eventHandler.getTempRow() * gamePanel.getTileSize();
            gamePanel.eventHandler.setPreviousEventX(gamePanel.player.worldX);
            gamePanel.eventHandler.setPreviousEventY(gamePanel.player.worldY);
        }
    }

    public void drawTradeScreen(){
        switch(subState){
            case 0 -> trade_select();
            case 1 -> trade_buy();
            case 2 -> trade_sell();
        }
        gamePanel.keyHandler.setEnterPressed(false);
    }

    public void drawSleepScreen(){
        counter++;

        Lighting lighting = gamePanel.environmentManager.lighting;

        if(counter < 120){
            lighting.setFilterAlpha(lighting.getFilterAlpha() + 0.01f);
            if(lighting.getFilterAlpha() >= 0.98f){
                lighting.setFilterAlpha(1f);
            }
        }
        else{
            lighting.setFilterAlpha(lighting.getFilterAlpha() - 0.01f);
            if(lighting.getFilterAlpha() <= 0.02f){
                lighting.setFilterAlpha(0f);
                counter = 0;
                lighting.setDayStates(DayStates.DAY);
                lighting.setDayCounter(0);
                gamePanel.setGameState(GameState.PLAY);
                gamePanel.player.getPlayerImage();
            }
        }
    }

    public void options_top(int frameX, int frameY){

        int textX, textY;

        // Title
        String text = "Options";
        textX = getXCenteredText(text, graphicsContext);
        textY = frameY + gamePanel.getTileSize();
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText(text, textX, textY);

        // Full Screen On/Off
        textX = frameX + gamePanel.getTileSize();
        textY += gamePanel.getTileSize() * 2;
        text = "Full Screen";
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText(text, textX, textY);
        if(commandNumber == 0){
            graphicsContext.fillText(">", textX - 25, textY);
            if(gamePanel.keyHandler.isEnterPressed()){
                gamePanel.fullScreenOn = !gamePanel.fullScreenOn;
                subState = 1;
            }
        }

        // Music
        textY += gamePanel.getTileSize();
        text = "Music";
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText(text, textX, textY);
        if(commandNumber == 1){
            graphicsContext.fillText(">", textX - 25, textY);
        }

        // Sound Effect
        textY += gamePanel.getTileSize();
        text = "Sound Effect";
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText(text, textX, textY);
        if(commandNumber == 2){
            graphicsContext.fillText(">", textX - 25, textY);
        }

        // Control
        textY += gamePanel.getTileSize();
        text = "Control";
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText(text, textX, textY);
        if(commandNumber == 3){
            graphicsContext.fillText(">", textX - 25, textY);
            if(gamePanel.keyHandler.isEnterPressed()){
                subState = 2;
                commandNumber = 0;
            }
        }

        // End Game
        textY += gamePanel.getTileSize();
        text = "End Game";
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText(text, textX, textY);
        if(commandNumber == 4){
            graphicsContext.fillText(">", textX - 25, textY);
            if(gamePanel.keyHandler.isEnterPressed()){
                subState = 3;
                commandNumber = 0;
            }
        }

        // Back
        textY += gamePanel.getTileSize() * 2;
        text = "Back";
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText(text, textX, textY);
        if(commandNumber == 5){
            graphicsContext.fillText(">", textX - 25, textY);
            if(gamePanel.keyHandler.isEnterPressed()){
                gamePanel.setGameState(GameState.PLAY);
                commandNumber = 0;
            }
        }

        // Full Screen Check Box
        textX = frameX + gamePanel.getTileSize() * 10;
        textY = (int) (frameY + gamePanel.getTileSize() * 2.5);
        graphicsContext.setLineWidth(3);
        graphicsContext.strokeRect(textX, textY, 24, 24);
        if(gamePanel.fullScreenOn){
            graphicsContext.fillRect(textX, textY, 24, 24);
        }

        // Music Volume
        textY += gamePanel.getTileSize();
        graphicsContext.strokeRect(textX, textY, 120, 24);
        int volumeWidth = 24 * gamePanel.music.getVolumeScale();
        graphicsContext.fillRect(textX, textY, volumeWidth, 24);

        // Sound Effect Volume
        textY += gamePanel.getTileSize();
        graphicsContext.strokeRect(textX, textY, 120, 24);
        volumeWidth = 24 * gamePanel.soundEffect.getVolumeScale();
        graphicsContext.fillRect(textX, textY, volumeWidth, 24);

        gamePanel.config.saveConfig();
    }

    private void options_fullScreenNotification(int frameX, int frameY) {
        int textX = frameX + gamePanel.getTileSize();
        int textY = frameY + gamePanel.getTileSize() * 2;

        currentDialogue = "The change will take effect after \nrestarting the game.";
        for (String line : currentDialogue.split("\n")){
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillText(line, textX, textY);
            textY += 32;
        }

        // Back

        textY += gamePanel.getTileSize() * 2;
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText("Back", textX, textY);
        if(commandNumber == 0){
            graphicsContext.fillText(">", textX - 25, textY);
            if(gamePanel.keyHandler.isEnterPressed()){
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
        textY = frameY + gamePanel.getTileSize();
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText(text, textX, textY);

        textX = frameX + gamePanel.getTileSize();
        textY += gamePanel.getTileSize();
        graphicsContext.fillText("Move", textX, textY);
        textY += gamePanel.getTileSize();
        graphicsContext.fillText("Confirm/Attack", textX, textY);
        textY += gamePanel.getTileSize();
        graphicsContext.fillText("Shoot/Cast", textX, textY);
        textY += gamePanel.getTileSize();
        graphicsContext.fillText("Character Screen", textX, textY);
        textY += gamePanel.getTileSize();
        graphicsContext.fillText("Pause", textX, textY);
        textY += gamePanel.getTileSize();
        graphicsContext.fillText("Map", textX, textY);
        textY += gamePanel.getTileSize();
        graphicsContext.fillText("Mini-Map", textX, textY);
        textY += gamePanel.getTileSize();
        graphicsContext.fillText("Options", textX, textY);

        textX = frameX + gamePanel.getTileSize() * 7;
        textY = frameY + gamePanel.getTileSize() * 2;
        graphicsContext.fillText("Arrow Keys/ZQSD", textX, textY);
        textY += gamePanel.getTileSize();
        graphicsContext.fillText("Enter", textX, textY);
        textY += gamePanel.getTileSize();
        graphicsContext.fillText("F", textX, textY);
        textY += gamePanel.getTileSize();
        graphicsContext.fillText("C", textX, textY);
        textY += gamePanel.getTileSize();
        graphicsContext.fillText("P", textX, textY);
        textY += gamePanel.getTileSize();
        graphicsContext.fillText("M", textX, textY);
        textY += gamePanel.getTileSize();
        graphicsContext.fillText("X", textX, textY);
        textY += gamePanel.getTileSize();
        graphicsContext.fillText("ESCAPE", textX, textY);

        // Back

        textX = frameX + gamePanel.getTileSize();
        textY += gamePanel.getTileSize() * 2;
        graphicsContext.fillText("Back", textX, textY);
        if(commandNumber == 0){
            graphicsContext.fillText(">", textX - 25, textY);
            if(gamePanel.keyHandler.isEnterPressed()){
                subState = 0;
                commandNumber = 3;
            }
        }
    }

    public void options_endGameConfirmation(int frameX, int frameY){
        int textX = frameX + gamePanel.getTileSize();
        int textY = frameY + gamePanel.getTileSize() * 3;

        currentDialogue = "Quit the game and \nreturn to title screen?";

        for(String line : currentDialogue.split("\n")){
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillText(line, textX, textY);
            textY += 32;
        }

        // Yes
        String text = "Yes";
        textX = getXCenteredText(text, graphicsContext);
        textY += gamePanel.getTileSize() * 3;
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText(text, textX, textY);
        if(commandNumber == 0){
            graphicsContext.fillText(">", textX - 25, textY);
            if(gamePanel.keyHandler.isEnterPressed()){
                subState = 0;
                gamePanel.ui.titleScreenState = 0;
                gamePanel.setGameState(GameState.TITLE);
                gamePanel.stopMusic();
            }
        }

        // No
        text = "No";
        textX = getXCenteredText(text, graphicsContext);
        textY += gamePanel.getTileSize() * 3;
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText(text, textX, textY);
        if(commandNumber == 1){
            graphicsContext.fillText(">", textX - 25, textY);
            if(gamePanel.keyHandler.isEnterPressed()){
                subState = 0;
                commandNumber = 4;
            }
        }

    }

    public void trade_select(){
        drawDialogueScreen();

        // Draw Options
        int width = (int) (gamePanel.getTileSize() * 3.5);
        int height = (int) (gamePanel.getTileSize() * 3.5);
        int x = gamePanel.screenWidth - width - gamePanel.getTileSize();
        int y = gamePanel.getTileSize() * 4;
        drawSubWindows(x, y, width, height);

        // Draw Text
        x += gamePanel.getTileSize();
        y += gamePanel.getTileSize();
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText("Buy", x, y);
        if(commandNumber == 0){
            graphicsContext.fillText(">", x - 24, y);
            if(gamePanel.keyHandler.isEnterPressed()){
                subState = 1;
            }
        }
        y += gamePanel.getTileSize();
        graphicsContext.fillText("Sell", x, y);
        if(commandNumber == 1){
            graphicsContext.fillText(">", x - 24, y);
            if(gamePanel.keyHandler.isEnterPressed()){
                subState = 2;
            }
        }
        y += gamePanel.getTileSize();
        graphicsContext.fillText("Leave", x, y);
        if(commandNumber == 2){
            graphicsContext.fillText(">", x - 24, y);
            if(gamePanel.keyHandler.isEnterPressed()){
                commandNumber = 0;
                gamePanel.setGameState(GameState.DIALOGUE);
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
        int width = gamePanel.getTileSize() * 6;
        int height = gamePanel.getTileSize() * 2;
        int x = gamePanel.getTileSize() * 2;
        int y = gamePanel.getTileSize() * 9;
        drawSubWindows(x, y, width, height);
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText("[ESC] Back", x + 24, y + 60);

        // Draw Coin Window
        x = gamePanel.screenWidth - width - gamePanel.getTileSize();
        drawSubWindows(x, y, width, height);
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText("Your Gold : " + gamePanel.player.gold, x + 24, y + 60);

        // Draw Price Window
        int itemIndex = getItemIndexInInventory(npcSlotColumn, npcSlotRow);
        if(itemIndex < npc.inventory.size()){
            x = (int) (gamePanel.getTileSize() * 5.5);
            y = (int) (gamePanel.getTileSize() * 5.5);
            width = (int) (gamePanel.getTileSize() * 2.5);
            height = gamePanel.getTileSize();
            drawSubWindows(x, y, width, height);
            graphicsContext.drawImage(gold, x+10, y+8, 32, 32);

            int price = npc.inventory.get(itemIndex).price;
            String text = "" + price;
            x = getXAlignedToRightText(text, gamePanel.getTileSize() * 8-20);
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillText(text, x, y + 32);

            // Buy an item
            if(gamePanel.keyHandler.isEnterPressed()){
                if(npc.inventory.get(itemIndex).price > gamePanel.player.gold){
                    subState = 0;
                    gamePanel.setGameState(GameState.DIALOGUE);
                    currentDialogue = "You need more coin to buy that!";
                }
                else {
                    if(gamePanel.player.canObtainItem(npc.inventory.get(itemIndex))){
                        gamePanel.player.gold -= npc.inventory.get(itemIndex).price;
                    }
                    else{
                        subState = 0;
                        gamePanel.setGameState(GameState.DIALOGUE);
                        currentDialogue = "Your inventory is full!";
                    }
                }
                /*
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
                 */
            }
        }
    }

    public void trade_sell(){
        drawInventory(gamePanel.player, true);

        int x, y, width, height;

        // Draw Hint Window
        width = gamePanel.getTileSize() * 6;
        height = gamePanel.getTileSize() * 2;
        x = gamePanel.getTileSize() * 2;
        y = gamePanel.getTileSize() * 9;
        drawSubWindows(x, y, width, height);
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText("[ESC] Back", x + 24, y + 60);

        // Draw Coin Window
        x = gamePanel.screenWidth - width - gamePanel.getTileSize();
        drawSubWindows(x, y, width, height);
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText("Your Gold : " + gamePanel.player.gold, x + 24, y + 60);

        // Draw Price Window
        int itemIndex = getItemIndexInInventory(playerSlotColumn, playerSlotRow);
        if(itemIndex < gamePanel.player.inventory.size()){
            y = (int) (gamePanel.getTileSize() * 5.5);
            width = (int) (gamePanel.getTileSize() * 2.5);
            height = gamePanel.getTileSize();
            x = gamePanel.screenWidth - width - gamePanel.getTileSize();
            drawSubWindows(x, y, width, height);
            graphicsContext.drawImage(gold, x+10, y+8, 32, 32);

            int price = (int) Math.round(gamePanel.player.inventory.get(itemIndex).price * 0.8);
            String text = "" + price;
            x = getXAlignedToRightText(text, x + width - 20);
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillText(text, x, y + 32);

            // Sell an item
            if(gamePanel.keyHandler.isEnterPressed()){
                if(gamePanel.player.inventory.get(itemIndex) == gamePanel.player.currentWeapon || gamePanel.player.inventory.get(itemIndex) == gamePanel.player.currentShield || gamePanel.player.inventory.get(itemIndex) == gamePanel.player.currentBoots){
                    commandNumber = 0;
                    subState = 0;
                    gamePanel.setGameState(GameState.DIALOGUE);
                    currentDialogue = "You can't sell equipped items!";
                    drawDialogueScreen();
                }
                else{
                    if(gamePanel.player.inventory.get(itemIndex).amount > 1){
                        gamePanel.player.inventory.get(itemIndex).amount--;
                    }
                    else{
                        gamePanel.player.inventory.remove(itemIndex);
                    }
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


    // Getters and Setters
    public void setSubState(int subState) {
        this.subState = subState;
    }
    public int getSubState() {
        return subState;
    }
    public void setCommandNumber(int commandNumber) {
        this.commandNumber = commandNumber;
    }
    public int getCommandNumber() {
        return commandNumber;
    }
    public void setNpc(Entity npc) {
        this.npc = npc;
    }
    public Entity getNpc() {
        return npc;
    }
    public void setCurrentDialogue(String currentDialogue) {
        this.currentDialogue = currentDialogue;
    }
    public String getCurrentDialogue() {
        return currentDialogue;
    }
    public void setPlayerSlotColumn(int playerSlotColumn) {
        this.playerSlotColumn = playerSlotColumn;
    }
    public int getPlayerSlotColumn() {
        return playerSlotColumn;
    }
    public void setPlayerSlotRow(int playerSlotRow) {
        this.playerSlotRow = playerSlotRow;
    }
    public int getPlayerSlotRow() {
        return playerSlotRow;
    }
    public void setNpcSlotColumn(int npcSlotColumn) {
        this.npcSlotColumn = npcSlotColumn;
    }
    public int getNpcSlotColumn() {
        return npcSlotColumn;
    }
    public void setNpcSlotRow(int npcSlotRow) {
        this.npcSlotRow = npcSlotRow;
    }
    public int getNpcSlotRow() {
        return npcSlotRow;
    }
    public void setCounter(double counter) {
        this.counter = counter;
    }
    public double getCounter() {
        return counter;
    }
    public void setTitleScreenState(int titleScreenState) {
        this.titleScreenState = titleScreenState;
    }
    public int getTitleScreenState() {
        return titleScreenState;
    }
}