package fr.cejuba.stardew.main;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.object.Heart;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.text.DecimalFormat;

public class UI {

    GamePanel gamePanel;
    GraphicsContext graphicsContext;
    Font arial_40, arial_30, arial_80B, arial_32F, arial_96B;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNumber = 0;
    public int titleScreenState = 0;

    Image heart_full, heart_half, heart_blank;

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
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(GraphicsContext graphicsContext){
        this.graphicsContext = graphicsContext;

        graphicsContext.setFont(arial_40);
        graphicsContext.setFill(Color.WHITE);

        if(gamePanel.gameState == gamePanel.titleState){
            drawTitleScreen();
        }
        if(gamePanel.gameState == gamePanel.playState){
            drawPlayerLife();
        }
        if(gamePanel.gameState == gamePanel.pauseState){
            drawPlayerLife();
            drawPauseScreen();
        }
        if(gamePanel.gameState == gamePanel.dialogueState){
            drawPlayerLife();
            drawDialogueScreen();
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

    private void drawTitleScreen() {
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
            graphicsContext.drawImage(gamePanel.player.down0, x, y, gamePanel.tileSize * 2, gamePanel.tileSize * 2);

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

}
