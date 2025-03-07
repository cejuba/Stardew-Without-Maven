package fr.cejuba.stardew.object.consumable;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;

public class RedPotion extends Entity {
    GamePanel gamePanel;

    public RedPotion(GamePanel gamePanel) {
        super(gamePanel);

        value = 5;
        this.gamePanel = gamePanel;

        type = type_consumable;
        name = "RedPotion";
        down1 = setup("/object/potion_red", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\nHeals your life by " + value + ".";
        price = 25;

    }

    public void use(Entity entity) {
        System.out.println("Potion used2");
        gamePanel.gameState = gamePanel.dialogueState;
        gamePanel.ui.currentDialogue = "You drink the " + name + "!\n" + "Your life has been recovered by " + value + ".";
        entity.life += value;
        if(entity.life > entity.maxLife){
            entity.life = entity.maxLife;
        }
        gamePanel.playSoundEffect(2);

    }
}
