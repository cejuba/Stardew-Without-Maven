package fr.cejuba.stardew.entity;

import fr.cejuba.stardew.main.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Particle extends Entity {

    private final Entity generator;
    private Color color;
    private int speed, size, xd, yd;

    public Particle(GamePanel gamePanel, Entity generator, Color color, int size, int speed, int maxLife, int xd, int yd) {
        super(gamePanel);

        this.generator = generator;
        this.size = size;
        this.color = color;
        this.speed = speed;
        this.xd = xd;
        this.yd = yd;

        life = maxLife;
        int offset = (gamePanel.getTileSize() / 2) - (size / 2);
        worldX = generator.worldX + offset;
        worldY = generator.worldY + offset;
    }

    public void update() {
        life--;

        if (life < maxLife/3) { // TODO : patch because gravity isn't applied to particles
            yd++;
        }
        worldX += xd * speed;
        worldY += yd * speed;

        if (life <= 0) {
            alive = false;
        }
    }

    public void draw(GraphicsContext graphicsContext) {
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.getScreenX();
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.getScreenY();

        graphicsContext.setFill(color);
        graphicsContext.fillRect(screenX, screenY, size, size);
    }

}
