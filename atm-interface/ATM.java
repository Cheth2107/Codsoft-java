import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ATM extends Application {

    private BankAccount account;
    private Label balanceLabel;
    private TextField amountField;
    private Label messageLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        account = new BankAccount(1000.00); // Initialize account with a balance of 1000

        primaryStage.setTitle("ATM Interface");

        // Create UI elements
        balanceLabel = new Label("Current Balance: $" + account.getBalance());
        Label amountLabel = new Label("Enter Amount:");
        amountField = new TextField();
        Button withdrawButton = new Button("Withdraw");
        Button depositButton = new Button("Deposit");
        Button checkBalanceButton = new Button("Check Balance");
        messageLabel = new Label();

        // Event handlers
        withdrawButton.setOnAction(e -> withdraw());
        depositButton.setOnAction(e -> deposit());
        checkBalanceButton.setOnAction(e -> checkBalance());

        // Layout setup
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(10);
        grid.setHgap(10);

        grid.add(balanceLabel, 0, 0, 2, 1);
        grid.add(amountLabel, 0, 1);
        grid.add(amountField, 1, 1);
        grid.add(withdrawButton, 0, 2);
        grid.add(depositButton, 1, 2);
        grid.add(checkBalanceButton, 0, 3, 2, 1);
        grid.add(messageLabel, 0, 4, 2, 1);

        Scene scene = new Scene(grid, 350, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void withdraw() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (account.withdraw(amount)) {
                messageLabel.setText("Withdraw successful! New balance: $" + account.getBalance());
            } else {
                messageLabel.setText("Insufficient balance or invalid amount.");
            }
        } catch (NumberFormatException e) {
            messageLabel.setText("Please enter a valid number.");
        }
        amountField.clear();
        updateBalance();
    }

    private void deposit() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount > 0) {
                account.deposit(amount);
                messageLabel.setText("Deposit successful! New balance: $" + account.getBalance());
            } else {
                messageLabel.setText("Please enter a positive amount.");
            }
        } catch (NumberFormatException e) {
            messageLabel.setText("Please enter a valid number.");
        }
        amountField.clear();
        updateBalance();
    }

    private void checkBalance() {
        updateBalance();
        messageLabel.setText("Checked balance successfully.");
    }

    private void updateBalance() {
        balanceLabel.setText("Current Balance: $" + account.getBalance());
    }
}



