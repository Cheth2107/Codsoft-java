import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Random;

public class NumberGuessingGame extends Application {

    private int secretNumber;
    private int attempts = 0;
    private final int maxAttempts = 10;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        secretNumber = new Random().nextInt(100) + 1;

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        Label promptLabel = new Label("Guess a number between 1 and 100:");
        TextField guessField = new TextField();
        guessField.setMaxWidth(200); // Adjusts the width of the text field
        Button submitButton = new Button("Submit");
        Label feedbackLabel = new Label();
        Label attemptsLabel = new Label("Attempts: 0"); // Label to display the number of attempts

        submitButton.setOnAction(e -> {
            try {
                int guess = Integer.parseInt(guessField.getText());
                attempts++;
                attemptsLabel.setText("Attempts: " + attempts); // Update the attempts label
                if (guess == secretNumber) {
                    feedbackLabel.setText("Congratulations! You guessed the number in " + attempts + " attempts.");
                } else if (attempts >= maxAttempts) {
                    feedbackLabel.setText("Sorry, you didn't guess the number. It was " + secretNumber + ".");
                } else if (guess < secretNumber) {
                    feedbackLabel.setText("Too low. Try again.");
                } else {
                    feedbackLabel.setText("Too high. Try again.");
                }
                guessField.clear();
            } catch (NumberFormatException ex) {
                feedbackLabel.setText("Please enter a valid number.");
            }
        });

        vbox.getChildren().addAll(promptLabel, guessField, submitButton, feedbackLabel, attemptsLabel);

        Scene scene = new Scene(vbox, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Number Guessing Game");
        primaryStage.show();
    }
}
