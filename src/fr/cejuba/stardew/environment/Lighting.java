package fr.cejuba.stardew.environment;

import fr.cejuba.stardew.main.GamePanel;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

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

        // Get the top left x and y of the light circle
        double x = centerX - (double) circleRadius / 2;
        double y = centerY - (double) circleRadius / 2;

        graphicsContext.setFill(Color.TRANSPARENT);
        graphicsContext.fillOval(x, y, circleRadius, circleRadius);

        // Draw a larger oval with a transparent fill and a stroke
        double strokeWidth = 10000; // Adjust the stroke width as needed
        graphicsContext.setStroke(Color.rgb(0, 0, 0, 0.95));
        graphicsContext.setLineWidth(strokeWidth);
        graphicsContext.setFill(Color.TRANSPARENT);
        graphicsContext.strokeOval(x - strokeWidth / 2, y - strokeWidth / 2, circleRadius + strokeWidth, circleRadius + strokeWidth);
    }
}