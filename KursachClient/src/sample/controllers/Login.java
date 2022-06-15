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

public class Login {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button signInBtn;

    @FXML
    private Button regBtn;

    @FXML
    private PasswordField passwordInp;

    @FXML
    private TextField loginInp;

    @FXML
    void initialize() {

        regBtn.setOnAction(actionEvent ->{
            Windows.windows.newWindow("views/registration.fxml", regBtn);
        });

        signInBtn.setOnAction(actionEvent -> {
            if(validation()) {
                Client.connectionToServer.sendMsg("auth");
                Client.connectionToServer.sendMsg(loginInp.getText() + " " + passwordInp.getText());
                if (Client.connectionToServer.checkUserInDb()) {
                    Client.connectionToServer.getIdAndRoll(loginInp.getText());

                    if(User.currentUser.getRoll()==0) {
                        Windows.windows.newWindow("views/clientWindow.fxml", signInBtn);
                    }
                    if(User.currentUser.getRoll()==1){
                        Windows.windows.newWindow("views/adminWindow.fxml", signInBtn);


                    }
                } else {
                    showMessage("Аккаунт не существует !");
                }
            }else{
                showMessage("Аккаунт не существует !");
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

    private boolean validation (){
        if(!loginInp.getText().equals("") && !passwordInp.getText().equals("")){
            return true;
        }
        return false;
    }
}
