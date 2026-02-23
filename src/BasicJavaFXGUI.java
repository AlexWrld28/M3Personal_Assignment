import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class BasicJavaFXGUI extends Application {

    @Override
    public void start(Stage primaryStage) {

        // Labels
        Label rateLabel = new Label("Annual Interest Rate:");
        Label yearsLabel = new Label("Number of Years:");
        Label loanAmountLabel = new Label("Loan Amount:");
        Label monthlyPaymentLabel = new Label("Monthly Payment:");
        Label totalPaymentLabel = new Label("Total Payment:");

        // TextFields (inputs)
        TextField rateField = new TextField();
        TextField yearsField = new TextField();
        TextField loanAmountField = new TextField();

        // TextFields (outputs)
        TextField monthlyPaymentField = new TextField();
        monthlyPaymentField.setEditable(false);

        TextField totalPaymentField = new TextField();
        totalPaymentField.setEditable(false);

        // Right-align text inside all fields
        rateField.setAlignment(Pos.CENTER_RIGHT);
        yearsField.setAlignment(Pos.CENTER_RIGHT);
        loanAmountField.setAlignment(Pos.CENTER_RIGHT);
        monthlyPaymentField.setAlignment(Pos.CENTER_RIGHT);
        totalPaymentField.setAlignment(Pos.CENTER_RIGHT);

        // Let fields stretch to the right edge
        rateField.setMaxWidth(Double.MAX_VALUE);
        yearsField.setMaxWidth(Double.MAX_VALUE);
        loanAmountField.setMaxWidth(Double.MAX_VALUE);
        monthlyPaymentField.setMaxWidth(Double.MAX_VALUE);
        totalPaymentField.setMaxWidth(Double.MAX_VALUE);

        // Button
        Button calculateButton = new Button("Calculate");

        // Layout
        GridPane root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(15));

        // Column constraints: label column fixed, field column grows
        ColumnConstraints col0 = new ColumnConstraints();
        col0.setHgrow(Priority.NEVER);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS);

        root.getColumnConstraints().addAll(col0, col1);

        // Add rows (label left, field right)
        root.add(rateLabel, 0, 0);
        root.add(rateField, 1, 0);

        root.add(yearsLabel, 0, 1);
        root.add(yearsField, 1, 1);

        root.add(loanAmountLabel, 0, 2);
        root.add(loanAmountField, 1, 2);

        root.add(monthlyPaymentLabel, 0, 3);
        root.add(monthlyPaymentField, 1, 3);

        root.add(totalPaymentLabel, 0, 4);
        root.add(totalPaymentField, 1, 4);

        // Button directly below last field, right-aligned
        root.add(calculateButton, 1, 5);
        GridPane.setHalignment(calculateButton, HPos.RIGHT);

        // Event-driven: calculate on button click
        calculateButton.setOnAction(e -> {
            try {
                double annualRate = Double.parseDouble(rateField.getText().trim());
                int years = Integer.parseInt(yearsField.getText().trim());
                double loanAmount = Double.parseDouble(loanAmountField.getText().trim());

                if (annualRate < 0 || years <= 0 || loanAmount <= 0) {
                    throw new IllegalArgumentException("Values must be positive (years must be > 0).");
                }

                // Backend logic in separate file
                LoanCalculator calculator = new LoanCalculator(annualRate, years, loanAmount);

                double monthlyPayment = calculator.calculateMonthlyPayment();
                double totalPayment = calculator.calculateTotalPayment();

                monthlyPaymentField.setText(String.format("$%,.2f", monthlyPayment));
                totalPaymentField.setText(String.format("$%,.2f", totalPayment));

            } catch (NumberFormatException ex) {
                showError("Please enter valid numeric values.\n\nExample:\nRate: 6.5\nYears: 30\nLoan Amount: 250000");
            } catch (IllegalArgumentException ex) {
                showError(ex.getMessage());
            } catch (Exception ex) {
                showError("Something went wrong: " + ex.getMessage());
            }
        });

        // Scene + Stage
        Scene scene = new Scene(root, 480, 320);
        primaryStage.setTitle("Loan Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText("Could not calculate");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}