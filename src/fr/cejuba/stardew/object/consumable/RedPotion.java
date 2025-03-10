package fr.cejuba.stardew.object.consumable;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.main.GameState;
import fr.cejuba.stardew.main.Type;

public class RedPotion extends Entity {
    GamePanel gamePanel;

    public RedPotion(GamePanel gamePanel) {
        super(gamePanel);

        value = 5;
        this.gamePanel = gamePanel;

        type = Type.CONSUMABLE;
        name = "RedPotion";
        down2 = setup("/object/potion_red", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\nHeals your life by " + value + ".";
        price = 25;
        stackable = true;
    }

    public boolean use(Entity entity) {
        System.out.println("Potion used2");
        gamePanel.gameState = GameState.DIALOGUE;
        gamePanel.ui.currentDialogue = "You drink the " + name + "!\n" + "Your life has been recovered by " + value + ".";
        entity.life += value;
        if(entity.life > entity.maxLife){
            entity.life = entity.maxLife;
        }
        gamePanel.playSoundEffect(2);
        return true;
    }
}
