package fr.cejuba.stardew.object.shield;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.main.Type;

public class RustyShield extends Entity {
    public RustyShield(GamePanel gamePanel) {
        super(gamePanel);

        setType(Type.SHIELD);
        name = "Rusty Shield";
        down2 = setup("/object/rustyShield", gamePanel.getTileSize(), gamePanel.getTileSize());
        defenseValue = 1;

        description = "[" + name + "]\n" + "A rusty shield.";
        price = 35;

    }
}
