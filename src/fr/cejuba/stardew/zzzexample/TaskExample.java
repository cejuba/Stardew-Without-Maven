package fr.cejuba.stardew.zzzexample;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TaskExample extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Label statusLabel = new Label("Status: Ready");
        ProgressBar progressBar = new ProgressBar(0);

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                final int max = 100;

                for (int i = 1; i <= max; i++) {
                    // Simulate some work
                    Thread.sleep(50);

                    // Update progress and status message
                    updateProgress(i, max);
                    updateMessage("Processing " + i + " out of " + max);
                }

                return null;
            }
        };

        // Bind progress and status message to UI components
        progressBar.progressProperty().bind(task.progressProperty());
        statusLabel.textProperty().bind(task.messageProperty());

        // Start the task on a new thread
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();

        VBox root = new VBox(10, statusLabel, progressBar);
        Scene scene = new Scene(root, 300, 200);

        primaryStage.setTitle("Task Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
