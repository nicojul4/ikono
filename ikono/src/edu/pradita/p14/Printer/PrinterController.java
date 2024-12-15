package edu.pradita.p14;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.*;

public class PrinterController {

    @FXML private TextField hostField;
    @FXML private TextField dbNameField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button connectButton;
    @FXML private Button printButton;
    @FXML private Label statusLabel;

    private Connection connection;

    @FXML
    private void handleConnect() {
        String host = hostField.getText();
        String dbName = dbNameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String url = "jdbc:mysql://" + host + "/" + dbName;

        try {
            connection = DriverManager.getConnection(url, username, password);
            statusLabel.setText("Status: Connected to Database");
            statusLabel.setStyle("-fx-text-fill: #27ae60;");
            printButton.setDisable(false);
        } catch (SQLException e) {
            statusLabel.setText("Status: Connection Failed!");
            statusLabel.setStyle("-fx-text-fill: #e74c3c;");
            e.printStackTrace();
        }
    }

    @FXML
    private void handlePrint() {
        if (connection == null) {
            statusLabel.setText("Status: No Database Connection");
            statusLabel.setStyle("-fx-text-fill: #e74c3c;");
            return;
        }

        String query = "SELECT * FROM transactions";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("=== PRINTING DATABASE TRANSACTIONS ===");
            while (rs.next()) {
                System.out.println("Transaction ID: " + rs.getInt("transaction_id"));
                System.out.println("User ID: " + rs.getInt("user_id"));
                System.out.println("Product ID: " + rs.getInt("product_id"));
                System.out.println("Quantity: " + rs.getInt("quantity"));
                System.out.println("Total Price: " + rs.getDouble("total_price"));
                System.out.println("--------------------------------------");
            }
            statusLabel.setText("Status: Print Successful!");
            statusLabel.setStyle("-fx-text-fill: #27ae60;");
        } catch (SQLException e) {
            statusLabel.setText("Status: Print Failed!");
            statusLabel.setStyle("-fx-text-fill: #e74c3c;");
            e.printStackTrace();
        }
    }
}
