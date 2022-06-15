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
import sample.model.User;

public class EditAccountController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button editProfileButton;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button exitButton;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField nameField;

    @FXML
    void initialize() {
        initField();
        exitButton.setOnAction(actionEvent -> {
            Windows.windows.newWindow("views/clientWindow.fxml", exitButton);
        });

        editProfileButton.setOnAction(actionEvent -> {
            sendData();
        });
    }

    private void initField()  {
        emailField.setText(User.currentUser.getEmail());
        nameField.setText(User.currentUser.getName());
        surnameField.setText(User.currentUser.getSurname());
        passwordField.setText(User.currentUser.getPassword());

    }

    private void sendData(){
        if(valid()) {
            Client.connectionToServer.sendMsg("updateUserData");
            Client.connectionToServer.sendMsg(User.currentUser.getId() + " " + nameField.getText() + " " + surnameField.getText() + " " + emailField.getText() + " " + passwordField.getText());
            showMessage("Аккаунт изменен !");
            Windows.windows.newWindow("views/clientWindow.fxml", editProfileButton);

        }else {
            showMessage("Аккаунт не  изменен !");
        }
    }

    private boolean valid () {
        if (nameField.getText().equals("") || surnameField.getText().equals("") || emailField.getText().equals("") || passwordField.getText().equals("")) {
            return false;
        }
        return true;
    }

    private void showMessage(String massage){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success ...");
        alert.setHeaderText(null);
        alert.setContentText(massage);
        alert.showAndWait();
    }
}
