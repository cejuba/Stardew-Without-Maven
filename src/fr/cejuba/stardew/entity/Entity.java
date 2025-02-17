package fr.cejuba.stardew.entity;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class Entity {
    public int worldX, worldY;
    public int speed;

    public Image up0, up1, down0, down1, left0, left1, right0, right1;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNumber = 1;

    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionActivated = false;
}
