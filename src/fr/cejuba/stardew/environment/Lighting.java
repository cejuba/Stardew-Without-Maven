package fr.cejuba.stardew.environment;

import fr.cejuba.stardew.main.GamePanel;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.awt.*;

public class Lighting {

    GamePanel gamePanel;
    WritableImage darknessFilter;
    int circleRadius;

    public Lighting(GamePanel gamePanel, int circleRadius) {
        this.gamePanel = gamePanel;
        this.circleRadius = circleRadius;
    }

    public void draw(GraphicsContext graphicsContext) {

        int centerX = gamePanel.player.screenX + gamePanel.tileSize / 2;
        int centerY = gamePanel.player.screenY + gamePanel.tileSize / 2;

        Stop[] stops = new Stop[]{
                new Stop(0, Color.rgb(0, 0, 0, 0)),
                new Stop((double) circleRadius / (2 * gamePanel.screenWidth), Color.rgb(0, 0, 0, 0.80)),
                new Stop((double) circleRadius / gamePanel.screenWidth, Color.rgb(0, 0, 0, 0.95)),
                new Stop(1, Color.rgb(0, 0, 0, 0.98))
        };

        // Create a gradation paint settings for the light circle
        RadialGradient radialGradient = new RadialGradient(0, 0, centerX, centerY, gamePanel.screenWidth, false, CycleMethod.NO_CYCLE, stops);

        graphicsContext.setFill(radialGradient);
        graphicsContext.fillRect(0,0, gamePanel.screenWidth, gamePanel.screenWidth);
    }
}
