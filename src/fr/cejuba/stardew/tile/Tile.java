package fr.cejuba.stardew.tile;

import javafx.scene.image.Image;

public class Tile {
    private Image image;
    private boolean collision = false;

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }


    // Getter and setter for image
    public Image getImage() {
        return image;
    }
    public void setImage(Image image) {
        this.image = image;
    }
}
