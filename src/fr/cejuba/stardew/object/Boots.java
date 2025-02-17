package fr.cejuba.stardew.object;

import fr.cejuba.stardew.main.GamePanel;
import javafx.scene.image.Image;

import java.util.Objects;

public class Boots extends SuperObject{

    GamePanel gamePanel;

    public Boots(GamePanel gamePanel) {
        name = "Boots";
        try {
            ClassLoader classLoader = getClass().getClassLoader();

            image = new Image(Objects.requireNonNull(classLoader.getResourceAsStream("fr/cejuba/stardew/objects/boots.png")));
            utilityTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
