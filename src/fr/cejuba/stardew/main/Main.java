package fr.cejuba.stardew.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        KeyHandler keyHandler = new KeyHandler();
        // System.out.println("KeyHandler instance during GamePanel creation: " + keyHandler);

        GamePanel gamePanel = new GamePanel(keyHandler);

        // System.out.println("KeyHandler instance during GamePanel creation: " + keyHandler);

        StackPane root = new StackPane();
        root.getChildren().add(gamePanel);
        Scene scene = new Scene(root, gamePanel.getWidth(), gamePanel.getHeight());

        keyHandler.addKeyHandlers(scene);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

/*
        gamePanel.setKeyHandler(keyHandler);
*/
        // System.out.println("KeyHandler instance in Main: " + keyHandler);


        primaryStage.setTitle("Stardew 2D");
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.setScene(scene);
        primaryStage.show();


        gamePanel.setupGame();
        gamePanel.startGameThread();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
