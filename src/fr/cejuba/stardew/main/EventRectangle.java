package fr.cejuba.stardew.main;

import javafx.scene.shape.Rectangle;

public class EventRectangle extends Rectangle {
    private int eventRectangleDefaultX, eventRectangleDefaultY;
    private boolean eventDone = false;

    public void initialize(int x, int y, int width, int height) {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        eventRectangleDefaultX = x;
        eventRectangleDefaultY = y;
    }

    // Getters and Setters
    public int getEventRectangleDefaultX() {
        return eventRectangleDefaultX;
    }
    public int getEventRectangleDefaultY() {
        return eventRectangleDefaultY;
    }
    public boolean isEventDone() {
        return eventDone;
    }
    public void setEventDone(boolean eventDone) {
        this.eventDone = eventDone;
    }

}
