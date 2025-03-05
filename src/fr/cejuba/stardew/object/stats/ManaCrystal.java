package fr.cejuba.stardew.object.stats;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;

public class ManaCrystal extends Entity {

    GamePanel gamePanel;

    public ManaCrystal(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = "Mana Crystal";
        image = setup("object/manacrystal_full", gamePanel.tileSize, gamePanel.tileSize);
        image2 = setup("object/manacrystal_blank", gamePanel.tileSize, gamePanel.tileSize);
    }
}
