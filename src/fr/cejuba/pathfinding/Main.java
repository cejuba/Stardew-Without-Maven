package fr.cejuba.pathfinding;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        DemoPanel demoPanel = new DemoPanel();
        StackPane root = new StackPane();
        root.getChildren().add(demoPanel);

        Scene scene = new Scene(root, DemoPanel.screenWidth, DemoPanel.screenHeight);

        primaryStage.setTitle("Pathfinding");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}