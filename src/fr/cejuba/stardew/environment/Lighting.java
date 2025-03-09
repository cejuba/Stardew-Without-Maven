package fr.cejuba.stardew.environment;

// TODO : If there's a bug check #45 video around 13:00

import fr.cejuba.stardew.main.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

public class Lighting {

    GamePanel gamePanel;
    int dayCounter;
    float filterAlpha = 0f;

    final int day = 0;
    final int dusk = 1;
    final int night = 2;
    final int dawn = 3;
    int dayState = day;


    public Lighting(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void update(){
        if(dayState == day){
            dayCounter++;
            if(dayCounter > 600) {
                dayState = dusk;
                dayCounter = 0;
            }
        } else if(dayState == dusk){
            filterAlpha += 0.001f;
            if(filterAlpha >= 0.98f){
                filterAlpha = 1f;
                dayState = night;
            }
        } else if(dayState == night){
            dayCounter++;
            if(dayCounter > 600) {
                dayState = dawn;
                dayCounter = 0;
            }
        } else if(dayState == dawn){
            filterAlpha -= 0.001f;
            if(filterAlpha <= 0.02f){
                filterAlpha = 0f;
                dayState = day;
            }
        }
    }
    public void draw(GraphicsContext graphicsContext) {

        int circleRadius = 100;

        int centerX = gamePanel.player.screenX + gamePanel.tileSize / 2;
        int centerY = gamePanel.player.screenY + gamePanel.tileSize / 2;

        Stop[] stops = new Stop[]{};
        if(gamePanel.player.currentLight == null){
            stops = new Stop[]{
                    new Stop(0, Color.rgb(0, 0, 0, 0)),
                    new Stop((double) circleRadius / (2 * gamePanel.screenWidth), Color.rgb(0, 0, 0, 0.80)),
                    new Stop((double) circleRadius / gamePanel.screenWidth, Color.rgb(0, 0, 0, 0.95)),
                    new Stop(1, Color.rgb(0, 0, 0, 0.98))
            };
        }
        else{
            circleRadius = gamePanel.player.currentLight.lightRadius;
            stops = new Stop[]{
                    new Stop(0, Color.rgb(0, 0, 0, 0)),
                    new Stop((double) circleRadius / (2 * gamePanel.screenWidth), Color.rgb(0, 0, 0, 0.80)),
                    new Stop((double) circleRadius / gamePanel.screenWidth, Color.rgb(0, 0, 0, 0.95)),
                    new Stop(1, Color.rgb(0, 0, 0, 0.98))
            };
        }

        // Create a gradation paint settings for the light circle
        RadialGradient radialGradient = new RadialGradient(0, 0, centerX, centerY, gamePanel.screenWidth, false, CycleMethod.NO_CYCLE, stops);

        graphicsContext.setGlobalAlpha(filterAlpha);
        graphicsContext.setFill(radialGradient);
        graphicsContext.fillRect(0,0, gamePanel.screenWidth, gamePanel.screenWidth);
        graphicsContext.setGlobalAlpha(1f);

        // Debug :

        String situation = "";

        switch(dayState){
            case day -> situation = "Day";
            case dusk -> situation = "Dusk";
            case night -> situation = "Night";
            case dawn -> situation = "Dawn";
        }
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText(situation, gamePanel.screenWidth - gamePanel.tileSize, gamePanel.screenHeight - gamePanel.tileSize);
    }
}
