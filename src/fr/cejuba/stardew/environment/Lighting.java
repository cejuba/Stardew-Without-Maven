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
    public int dayCounter;
    public float filterAlpha = 0f;

    // Day states
    private DayStates dayStates = DayStates.DAY;


    public Lighting(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void update(){
        switch(dayStates){
            case DayStates.DAY -> {
                dayCounter++;
                if(dayCounter > 600) {
                    dayStates = DayStates.DUSK;
                    dayCounter = 0;
                }
            }
            case DayStates.DUSK -> {
                filterAlpha += 0.001f;
                if(filterAlpha >= 0.98f){
                    filterAlpha = 1f;
                    dayStates = DayStates.NIGHT;
                }
            }
            case DayStates.NIGHT -> {
                dayCounter++;
                if(dayCounter > 600) {
                    dayStates = DayStates.DAWN;
                    dayCounter = 0;
                }
            }
            case DayStates.DAWN -> {
                filterAlpha -= 0.001f;
                if(filterAlpha <= 0.02f){
                    filterAlpha = 0f;
                    dayStates = DayStates.DAY;
                }
            }
        }
    }
    public void draw(GraphicsContext graphicsContext) {

        int circleRadius = 100;

        int centerX = gamePanel.player.getScreenX() + gamePanel.tileSize / 2;
        int centerY = gamePanel.player.getScreenY() + gamePanel.tileSize / 2;

        Stop[] stops = new Stop[]{};
        if(gamePanel.player.currentLight == null){
            stops = new Stop[]{
                    new Stop(0, Color.rgb(0, 0, 10, 0.0)),
                    new Stop((double) circleRadius / (2 * gamePanel.screenWidth), Color.rgb(0, 0, 10, 0.80)),
                    new Stop((double) circleRadius / gamePanel.screenWidth, Color.rgb(0, 0, 10, 0.95)),
                    new Stop(1, Color.rgb(0, 0, 10, 0.98))
            };
        }
        else{
            circleRadius = gamePanel.player.currentLight.lightRadius;
            stops = new Stop[]{
                    new Stop(0, Color.rgb(0, 0, 10, 0)),
                    new Stop((double) circleRadius / (2 * gamePanel.screenWidth), Color.rgb(0, 0, 10, 0.80)),
                    new Stop((double) circleRadius / gamePanel.screenWidth, Color.rgb(0, 0, 10, 0.95)),
                    new Stop(1, Color.rgb(0, 0, 10, 0.98))
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

        switch(dayStates){
            case DayStates.DAY -> situation = "Day";
            case DayStates.DUSK -> situation = "Dusk";
            case DayStates.NIGHT -> situation = "Night";
            case DayStates.DAWN -> situation = "Dawn";
        }
        graphicsContext.setFill(Color.WHITE);
        int textX;
        int textY = gamePanel.screenHeight - gamePanel.tileSize;

        // Values
        int tailX = gamePanel.screenWidth - gamePanel.tileSize;
        textX = gamePanel.ui.getXAlignedToRightText(situation, tailX);
        graphicsContext.fillText(situation, textX, textY);
    }

    // Getter and setter
    public DayStates getDayStates() {
        return dayStates;
    }
    public void setDayStates(DayStates dayStates) {
        this.dayStates = dayStates;
    }
}
