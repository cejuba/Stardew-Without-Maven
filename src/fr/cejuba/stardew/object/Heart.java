package fr.cejuba.stardew.object;

import fr.cejuba.stardew.main.GamePanel;
import javafx.scene.image.Image;

import java.util.Objects;

public class Heart extends SuperObject {

    GamePanel gamePanel;

    public Heart(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        name = "Heart";
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            image = new Image(Objects.requireNonNull(classLoader.getResourceAsStream("fr/cejuba/stardew/object/heart_full.png")));
            image2 = new Image(Objects.requireNonNull(classLoader.getResourceAsStream("fr/cejuba/stardew/object/heart_half.png")));
            image3 = new Image(Objects.requireNonNull(classLoader.getResourceAsStream("fr/cejuba/stardew/object/heart_blank.png")));

            image = utilityTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
            image2 = utilityTool.scaleImage(image2, gamePanel.tileSize, gamePanel.tileSize);
            image3 = utilityTool.scaleImage(image3, gamePanel.tileSize, gamePanel.tileSize);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        collision = true;
    }
}
