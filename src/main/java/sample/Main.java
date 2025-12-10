package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load UserInfo.fxml
            System.out.println("Loading UserInfo.fxml...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserInfo.fxml"));
            Parent root = loader.load();

            System.out.println("FXML loaded successfully!");

            primaryStage.setTitle("University Service Request System - User Information");
            primaryStage.setScene(new Scene(root, 550, 500));
            primaryStage.setMinWidth(550);
            primaryStage.setMinHeight(500);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading FXML: " + e.getMessage());

            // Create a simple error scene
            javafx.scene.control.Label errorLabel = new javafx.scene.control.Label(
                    "Failed to load application: " + e.getMessage() +
                            "\nCheck if FXML files are in the correct location."
            );
            errorLabel.setStyle("-fx-text-fill: red; -fx-padding: 20px;");
            primaryStage.setScene(new Scene(new javafx.scene.layout.StackPane(errorLabel), 600, 400));
            primaryStage.setTitle("Error");
            primaryStage.show();
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting application...");
        launch(args);
    }
}