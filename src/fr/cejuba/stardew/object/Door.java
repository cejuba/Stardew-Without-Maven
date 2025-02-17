package fr.cejuba.stardew.object;

import fr.cejuba.stardew.main.GamePanel;
import javafx.scene.image.Image;

import java.util.Objects;

public class Door extends SuperObject {

    GamePanel gamePanel;

    public Door(GamePanel gamePanel) {
        name = "Door";
        try {
            ClassLoader classLoader = getClass().getClassLoader();

            image = new Image(Objects.requireNonNull(classLoader.getResourceAsStream("fr/cejuba/stardew/objects/door.png")));
            utilityTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        collision = true;
    }

}
