package fr.cejuba.pathfinding;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyHandler implements EventHandler<KeyEvent> {

    DemoPanel demoPanel;

    public KeyHandler(DemoPanel demoPanel) {
        this.demoPanel = demoPanel;
    }

    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case SPACE -> demoPanel.search();
            case ENTER -> demoPanel.autoSearch();
        }
    }
}