package fr.cejuba.stardew.object;

import fr.cejuba.stardew.main.GamePanel;
import javafx.scene.image.Image;

import java.util.Objects;

public class Key extends SuperObject {

    GamePanel gamePanel;

    public Key(GamePanel gamePanel) {
        name = "Key";
        try {
            ClassLoader classLoader = getClass().getClassLoader();

            image = new Image(Objects.requireNonNull(classLoader.getResourceAsStream("fr/cejuba/stardew/objects/key.png")));
            utilityTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        collision = true;
    }

}
