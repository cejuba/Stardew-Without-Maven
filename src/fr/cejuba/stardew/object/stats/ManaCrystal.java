package fr.cejuba.stardew.object.stats;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.main.Type;

public class ManaCrystal extends Entity {

    GamePanel gamePanel;

    public ManaCrystal(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        setType(Type.PICKUPONLY);
        name = "Mana Crystal";
        value = 1;
        down2 = setup("object/manacrystal_full", gamePanel.tileSize, gamePanel.tileSize);
        image = setup("object/manacrystal_full", gamePanel.tileSize, gamePanel.tileSize);
        image2 = setup("object/manacrystal_blank", gamePanel.tileSize, gamePanel.tileSize);
    }

    public boolean use(Entity entity) {
        gamePanel.playSoundEffect(2);
        gamePanel.ui.addMessage("Mana + " + value);
        entity.mana += value;
        return true;
    }
}
