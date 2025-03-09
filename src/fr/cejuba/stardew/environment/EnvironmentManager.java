package fr.cejuba.stardew.environment;

import fr.cejuba.stardew.main.GamePanel;
import javafx.scene.canvas.GraphicsContext;

// TODO : Add rain, fog, lighting, ...
public class EnvironmentManager {

    GamePanel gamePanel;
    Lighting lighting;

    public EnvironmentManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setup(){
        lighting = new Lighting(gamePanel, 200);
    }

    public void draw(GraphicsContext graphicsContext) {
        lighting.draw(graphicsContext);
    }
}
