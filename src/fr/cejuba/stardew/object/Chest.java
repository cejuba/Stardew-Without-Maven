package fr.cejuba.stardew.object;

import fr.cejuba.stardew.main.GamePanel;
import javafx.scene.image.Image;

import java.util.Objects;

public class Chest extends SuperObject {

    GamePanel gamePanel;

    public Chest(GamePanel gamePanel) {
        name = "Chest";
        try {
            ClassLoader classLoader = getClass().getClassLoader();

            image = new Image(Objects.requireNonNull(classLoader.getResourceAsStream("fr/cejuba/stardew/objects/chest.png")));
            utilityTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
