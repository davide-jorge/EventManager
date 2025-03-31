package dk.easv.eventmanager.gui.controllers;

import dk.easv.eventmanager.bll.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private TextField usernameTextfield, passwordTextfield;

    @FXML
    private Button togglePasswordButton;

    // Method to toggle password visibility
    @FXML
    private void togglePasswordVisibility() {
        // Get the parent AnchorPane
        AnchorPane parent = (AnchorPane) passwordTextfield.getParent();

        // Check if the password field is currently a PasswordField or TextField
        if (passwordTextfield instanceof PasswordField) {
            // Convert to TextField to reveal the password
            TextField textField = new TextField(passwordTextfield.getText());
            textField.setLayoutX(passwordTextfield.getLayoutX());
            textField.setLayoutY(passwordTextfield.getLayoutY());
            textField.setPrefWidth(passwordTextfield.getPrefWidth());

            // Remove the PasswordField and add the TextField
            parent.getChildren().remove(passwordTextfield);
            parent.getChildren().add(textField);

            // Change button text to "Hide"
            togglePasswordButton.setText("Hide");

            // Update the reference to the new TextField
            passwordTextfield = textField;
        } else {
            // Convert back to PasswordField to hide the password
            PasswordField passwordField = new PasswordField();
            passwordField.setText(passwordTextfield.getText());
            passwordField.setLayoutX(passwordTextfield.getLayoutX());
            passwordField.setLayoutY(passwordTextfield.getLayoutY());
            passwordField.setPrefWidth(passwordTextfield.getPrefWidth());

            // Remove the TextField and add the PasswordField
            parent.getChildren().remove(passwordTextfield);
            parent.getChildren().add(passwordField);

            // Change button text to "Show"
            togglePasswordButton.setText("Show");

            // Update the reference to the new PasswordField
            passwordTextfield = passwordField;
        }
    }

    private UserManager userManager;

    public LoginController() {
        userManager = new UserManager();
    }

    public void login(ActionEvent actionEvent) {
        String username = usernameTextfield.getText();
        String password = passwordTextfield.getText();

        // Validate Admin Login
        if (userManager.validateAdmin(username, password)) {
            try {
                openAdmin();
                closeLoginWindow(actionEvent);  // Close the login window after successful login
            } catch (Exception e) {
                showErrorDialog("Error","An error occurred while opening the admin window");
            }
        }
        // Validate Coordinator Login
        else if (userManager.validateCoordinator(username, password)) {
            try {
                openCoordinator();
                closeLoginWindow(actionEvent);  // Close the login window after successful login
            } catch (Exception e) {
                showErrorDialog("Error","An error occurred while opening the admin window");
            }
        }
        // Invalid Credentials
        else {
            showInvalidLoginAlert();
        }
    }

    // Method to show invalid login alert
    private void showInvalidLoginAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Credentials");
        alert.setHeaderText("Login Failed");
        alert.setContentText("Please check your Username and Password");
        alert.showAndWait();
    }

    // Method to open Admin dashboard
    private void openAdmin() throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/eventmanager/admin-view.fxml"));
            // Cast to TabPane instead of AnchorPane
            TabPane tabPane = (TabPane) loader.load();
            // Open the Admin Panel in a new window
            Stage newStage = new Stage();
            newStage.setTitle("Admin Panel");
            newStage.setScene(new Scene(tabPane));
            newStage.show();
        } catch (Exception e) {
            showErrorDialog("Error","Failed to open the Admin window");
        }
    }

    // Method to open Coordinator dashboard
    private void openCoordinator() throws Exception {
        // Load the Coordinator UI (FXML or other logic)
        System.out.println("Opening Coordinator UI...");
    }

    private void closeLoginWindow(ActionEvent actionEvent){
        Stage stage = (Stage) usernameTextfield.getScene().getWindow();
        stage.close();
    }

    /**
     * A helper method for error handling
     * Displays a user-friendly dialog when an error occurs (FXML loading error, invalid credentials, etc.)
     * */
    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
