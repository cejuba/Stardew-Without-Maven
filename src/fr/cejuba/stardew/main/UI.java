package fr.cejuba.stardew.main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.text.DecimalFormat;

public class UI {

    GamePanel gamePanel;
    GraphicsContext graphicsContext;
    Font arial_40, arial_30, arial_80B;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;

    double playTime;
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        arial_40 = Font.font("Arial", 40);
        arial_30 = Font.font("Arial", 30);
        arial_80B = Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 80);
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

    public int getXCenteredText(String text, GraphicsContext graphicsContext) {
        Text tempText = new Text(text);
        tempText.setFont(arial_40);
        double width = tempText.getBoundsInLocal().getWidth();
        return (int)(((double) gamePanel.screenWidth/2) - (width/2));
    }

}
