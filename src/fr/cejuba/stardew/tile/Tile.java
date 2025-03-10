package fr.cejuba.stardew.tile;

import javafx.scene.image.Image;

public class Tile {
    public Image image;
    private boolean collision = false;

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

}
