package sample.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.Client;
import sample.Windows;

public class RegistrationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button regBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    void initialize() {

        cancelBtn.setOnAction(actionEvent -> {
            Windows.windows.newWindow("views/sample.fxml", cancelBtn);
        });

        regBtn.setOnAction(actionEvent -> {
            String email = emailField.getText();
            String password = passwordField.getText();
            String surname =  surnameField.getText();
            String name = nameField.getText();

            if(!email.isEmpty() && !password.isEmpty()) {
                Client.connectionToServer.sendMsg("registration");
                Client.connectionToServer.sendMsg(email + " " +
                        password+ " " +name+" "+ surname);

                Windows.windows.newWindow("views/sample.fxml",regBtn);
                showMessage("Account is created !");
            }
            else{
                showMessage("Account is not created !");
            }
        });
    }

    private void showMessage(String massage){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success ...");
        alert.setHeaderText(null);
        alert.setContentText(massage);
        alert.showAndWait();
    }
}
