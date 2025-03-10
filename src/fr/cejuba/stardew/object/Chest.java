package fr.cejuba.stardew.object;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.main.GameState;
import fr.cejuba.stardew.main.Type;

public class Chest extends Entity {

    private final GamePanel gamePanel;
    private final Entity loot;
    private boolean opened = false;

    public Chest(GamePanel gamePanel, Entity loot) {
        super(gamePanel);
        this.gamePanel = gamePanel;
        this.loot = loot;

        setType(Type.OBSTACLE);
        name = "Chest";
        image = setup("object/chest", gamePanel.getTileSize(), gamePanel.getTileSize());
        image2 = setup("object/chest_opened", gamePanel.getTileSize(), gamePanel.getTileSize());
        down2 = image;
        collision = true;

        solidArea.setX(4);
        solidArea.setY(16);
        solidArea.setWidth(40);
        solidArea.setHeight(32);
        solidAreaDefaultX = (int) solidArea.getX();
        solidAreaDefaultY = (int) solidArea.getY();
    }

    public void interact(){
        gamePanel.setGameState(GameState.DIALOGUE);

        if(!opened){
            gamePanel.playSoundEffect(3);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("You found a " + loot.name + " in this chest.");

            if(!gamePanel.player.canObtainItem(loot)){
                stringBuilder.append("\nBut you cannot carry any more.");
            } else {
                stringBuilder.append("\nYou obtained the " + loot.name + ".");
                down2 = image2;
                opened = true;
            }
            gamePanel.ui.setCurrentDialogue(stringBuilder.toString());
        }
        else{
            gamePanel.ui.setCurrentDialogue("This chest is empty.");
        }
    }
}
