package fr.cejuba.stardew.main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.text.DecimalFormat;

public class UI {

    GamePanel gamePanel;
    GraphicsContext graphicsContext;
    Font arial_40, arial_30, arial_80B, arial_32F;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";


    double playTime;
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        arial_40 = Font.font("Arial", 40);
        arial_30 = Font.font("Arial", 30);
        arial_80B = Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 80);
        arial_32F = Font.font("Arial", javafx.scene.text.FontWeight.NORMAL, 32);
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(GraphicsContext graphicsContext){
        this.graphicsContext = graphicsContext;

        graphicsContext.setFont(arial_40);
        graphicsContext.setFill(Color.WHITE);

        if(gamePanel.gameState == gamePanel.playState){
            // TBD
        }
        if(gamePanel.gameState == gamePanel.pauseState){
            drawPauseScreen();
        }
        if(gamePanel.gameState == gamePanel.dialogueState){
            drawDialogueScreen();
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

    public void drawDialogueScreen(){
        int x = gamePanel.tileSize*2;
        int y = gamePanel.tileSize/2;
        int width = gamePanel.screenWidth - (gamePanel.tileSize*4);
        int height = gamePanel.tileSize*4;
        drawSubWindows(x, y, width, height);

        graphicsContext.setFont(arial_32F);
        graphicsContext.setFill(Color.WHITE);
        x += gamePanel.tileSize;
        y += gamePanel.tileSize;

        for(String line : currentDialogue.split("\n")){
            graphicsContext.fillText(line, x, y);
            y += 40;
        }
    }

    public void drawSubWindows(int x, int y, int width, int height){

        graphicsContext.setFill(Color.rgb(0,0,0, 0.8));
        graphicsContext.fillRoundRect(x, y, width, height, 35, 35);

        graphicsContext.setStroke(Color.rgb(255,255,255));
        graphicsContext.setLineWidth(5);
        graphicsContext.strokeRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }

    public int getXCenteredText(String text, GraphicsContext graphicsContext) {
        Text tempText = new Text(text);
        tempText.setFont(arial_40);
        double width = tempText.getBoundsInLocal().getWidth();
        return (int)(((double) gamePanel.screenWidth/2) - (width/2));
    }

}
