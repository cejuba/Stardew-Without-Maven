package fr.cejuba.stardew.main;

import fr.cejuba.stardew.object.Key;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.text.DecimalFormat;

public class UI {

    GamePanel gamePanel;
    Font arial_40, arial_30, arial_80B;
    Image keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;

    double playTime;
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        arial_40 = Font.font("Arial", javafx.scene.text.FontWeight.NORMAL, 40);
        arial_30 = Font.font("Arial", javafx.scene.text.FontWeight.NORMAL, 30);
        arial_80B = Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 80);


        Key key = new Key(gamePanel);
        keyImage = key.image;
    }


    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(GraphicsContext graphicsContext){

        if(gameFinished){

            String text;

            text = "You found the treasure!";
            Text textNode = new Text(text);
            textNode.setFont(arial_40);
            double textLength = textNode.getBoundsInLocal().getWidth();
            double x = (double) gamePanel.screenWidth /2 - textLength/2;
            double y = (double) gamePanel.screenHeight /2 - (gamePanel.tileSize*3);
            graphicsContext.fillText(text, x, y);

            graphicsContext.setFont(arial_80B);
            graphicsContext.setFill(Color.YELLOW);
            text = "Congratulations!";
            textNode = new Text(text);

            textLength = textNode.getBoundsInLocal().getWidth();
            x = (double) gamePanel.screenWidth /2 - textLength/2;
            y = (double) gamePanel.screenHeight /2 - (gamePanel.tileSize*2);
            graphicsContext.fillText(text, x, y);

            //TBD : Center Congratulations message
            gamePanel.gameThread = null;
        }
        else{

            graphicsContext.setFont(arial_40);
            graphicsContext.setFill(Color.WHITE);

            graphicsContext.drawImage(keyImage, (double) gamePanel.tileSize /2, (double) gamePanel.tileSize /2, gamePanel.tileSize, gamePanel.tileSize);
            graphicsContext.fillText("x " + gamePanel.player.hasKey, 74, 50);
            graphicsContext.setStroke(Color.BLACK);
            graphicsContext.strokeText("x " + gamePanel.player.hasKey, 74, 50);

            playTime += (double)1/60;
            graphicsContext.fillText("Time:" + decimalFormat.format(playTime), gamePanel.tileSize*26, 65);

            if(messageOn){
                graphicsContext.setFont(arial_30);
                graphicsContext.fillText(message, (double) gamePanel.tileSize /2 , gamePanel.tileSize*5);

                messageCounter++;

                if (messageCounter == 120) {
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }
    }


}
