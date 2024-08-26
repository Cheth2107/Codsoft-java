import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class StudentGradeCalculator extends Application {

    private TextField[] marksFields;
    private Label resultLabel;
    private Button calculateButton;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Student Grade Calculator");

        // Create UI elements
        Label subjectLabel = new Label("Enter marks for each subject (out of 100):");
        TextField subjectCountField = new TextField();
        Button generateFieldsButton = new Button("Generate Fields");
        calculateButton = new Button("Calculate");
        resultLabel = new Label();

        // Create a grid pane for layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(10);
        grid.setHgap(10);

        // Add elements to the grid
        grid.add(subjectLabel, 0, 0);
        grid.add(subjectCountField, 0, 1);
        grid.add(generateFieldsButton, 1, 1);
        grid.add(calculateButton, 0, 2);
        grid.add(resultLabel, 0, 3, 2, 1);

        // Event handler for generating input fields
        generateFieldsButton.setOnAction(e -> {
            int subjectCount = Integer.parseInt(subjectCountField.getText());
            createMarksFields(grid, subjectCount);
        });

        // Event handler for calculating results
        calculateButton.setOnAction(e -> calculateGrades());

        // Set up the scene and show the stage
        Scene scene = new Scene(grid, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createMarksFields(GridPane grid, int subjectCount) {
        // Clear previous fields if any
        grid.getChildren().removeIf(node -> GridPane.getRowIndex(node) > 1);

        marksFields = new TextField[subjectCount];

        for (int i = 0; i < subjectCount; i++) {
            Label label = new Label("Subject " + (i + 1) + ":");
            TextField marksField = new TextField();
            marksFields[i] = marksField;
            grid.add(label, 0, i + 2);
            grid.add(marksField, 1, i + 2);
        }

        // Reposition the calculate button and result label below the generated fields
        grid.add(calculateButton, 0, subjectCount + 2);
        grid.add(resultLabel, 0, subjectCount + 3, 2, 1);
    }

    private void calculateGrades() {
        if (marksFields == null) return;

        int totalMarks = 0;
        int subjectCount = marksFields.length;

        for (TextField marksField : marksFields) {
            try {
                int marks = Integer.parseInt(marksField.getText());
                if (marks < 0 || marks > 100) {
                    resultLabel.setText("Please enter valid marks (0-100).");
                    return;
                }
                totalMarks += marks;
            } catch (NumberFormatException e) {
                resultLabel.setText("Please enter valid numeric values.");
                return;
            }
        }

        double averagePercentage = (double) totalMarks / subjectCount;
        String grade = calculateGrade(averagePercentage);

        resultLabel.setText(String.format("Total Marks: %d\nAverage Percentage: %.2f%%\nGrade: %s",
                totalMarks, averagePercentage, grade));
    }

    private String calculateGrade(double percentage) {
        if (percentage >= 90) return "A+";
        else if (percentage >= 80) return "A";
        else if (percentage >= 70) return "B+";
        else if (percentage >= 60) return "B";
        else if (percentage >= 50) return "C";
        else if (percentage >= 40) return "D";
        else return "F";
    }

    public static void main(String[] args) {
        launch(args);
    }
}
