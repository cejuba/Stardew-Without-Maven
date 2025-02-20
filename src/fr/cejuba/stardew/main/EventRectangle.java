package fr.cejuba.stardew.main;

import javafx.scene.shape.Rectangle;

public class EventRectangle extends Rectangle {
    int eventRectangleDefaultX, eventRectangleDefaultY;
    boolean eventDone = false;

    public void initialize(int x, int y, int width, int height) {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        eventRectangleDefaultX = x;
        eventRectangleDefaultY = y;
    }
}
