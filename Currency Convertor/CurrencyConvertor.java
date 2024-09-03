import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CurrencyConverter extends Application {

    private ComboBox<String> baseCurrencyComboBox;
    private ComboBox<String> targetCurrencyComboBox;
    private TextField amountTextField;
    private Label resultLabel;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Currency Converter");

        // GridPane Layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(15, 15, 15, 15));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        // Base Currency Selection
        Label baseCurrencyLabel = new Label("Base Currency:");
        baseCurrencyComboBox = new ComboBox<>();
        baseCurrencyComboBox.getItems().addAll("USD", "EUR", "GBP", "INR", "AUD", "CAD", "JPY");
        baseCurrencyComboBox.setValue("USD");

        // Target Currency Selection
        Label targetCurrencyLabel = new Label("Target Currency:");
        targetCurrencyComboBox = new ComboBox<>();
        targetCurrencyComboBox.getItems().addAll("USD", "EUR", "GBP", "INR", "AUD", "CAD", "JPY");
        targetCurrencyComboBox.setValue("EUR");

        // Amount Input
        Label amountLabel = new Label("Amount:");
        amountTextField = new TextField();

        // Convert Button
        Button convertButton = new Button("Convert");
        convertButton.setOnAction(e -> convertCurrency());

        // Result Display
        resultLabel = new Label();

        // Adding elements to GridPane
        gridPane.add(baseCurrencyLabel, 0, 0);
        gridPane.add(baseCurrencyComboBox, 1, 0);
        gridPane.add(targetCurrencyLabel, 0, 1);
        gridPane.add(targetCurrencyComboBox, 1, 1);
        gridPane.add(amountLabel, 0, 2);
        gridPane.add(amountTextField, 1, 2);
        gridPane.add(convertButton, 1, 3);
        gridPane.add(resultLabel, 1, 4);

        // Setting Scene and Stage
        Scene scene = new Scene(gridPane, 350, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void convertCurrency() {
        String baseCurrency = baseCurrencyComboBox.getValue();
        String targetCurrency = targetCurrencyComboBox.getValue();
        String amountText = amountTextField.getText();
        if (amountText.isEmpty() || !amountText.matches("\\d+(\\.\\d{1,2})?")) {
            resultLabel.setText("Please enter a valid amount.");
            return;
        }

        double amount = Double.parseDouble(amountText);

        try {
            double exchangeRate = fetchExchangeRate(baseCurrency, targetCurrency);
            double convertedAmount = amount * exchangeRate;
            resultLabel.setText(String.format("%.2f %s", convertedAmount, targetCurrency));
        } catch (Exception e) {
            resultLabel.setText("Error fetching exchange rate.");
        }
    }

    private double fetchExchangeRate(String baseCurrency, String targetCurrency) throws Exception {
        String apiKey = "YOUR API KEY";  // Replace with your actual API key
        String apiUrl = String.format("https://api.exchangerate-api.com/v4/latest/%s", baseCurrency);

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            Scanner scanner = new Scanner(reader);
            StringBuilder response = new StringBuilder();

            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
            scanner.close();
            reader.close();

            JSONObject jsonObject = new JSONObject(response.toString());
            return jsonObject.getJSONObject("rates").getDouble(targetCurrency);
        } else {
            throw new Exception("Failed to fetch exchange rate.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
