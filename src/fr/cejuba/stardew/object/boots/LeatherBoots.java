package fr.cejuba.stardew.object.boots;

import fr.cejuba.stardew.main.GamePanel;

public class LeatherBoots extends Boots {

    public LeatherBoots(GamePanel gamePanel) {
        super(gamePanel);

        name = "Leather Boots";
        down2 = setup("/object/boots", gamePanel.getTileSize(), gamePanel.getTileSize());
        speedValue = 2;

        description = "[" + name + "]\n" + "It's a pair of boots.";

        price = 100;
    }
}
