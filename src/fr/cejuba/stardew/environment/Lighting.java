package fr.cejuba.stardew.environment;

// TODO : If there's a bug check #45 video around 13:00

import fr.cejuba.stardew.main.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

public class Lighting {

    GamePanel gamePanel;
    WritableImage darknessFilter;

    public Lighting(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setLightSource() {

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

        graphicsContext.setFill(radialGradient);
        graphicsContext.fillRect(0,0, gamePanel.screenWidth, gamePanel.screenWidth);
    }
}
