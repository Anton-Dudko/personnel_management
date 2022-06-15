package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Client;
import sample.Windows;
import sample.model.Task;
import sample.model.User;

public class HistoryUsersController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, String> nameTab;

    @FXML
    private TableColumn<User, String> surnameTable;

    @FXML
    private TableColumn<User, String> emailTab;

    @FXML
    private Button exitButton;

    private final ObservableList<User> listUser = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        exitButton.setOnAction(actionEvent -> {
            Windows.windows.newWindow("views/adminWindow.fxml", exitButton);
        });

        try {
            initTasks(showAllUsers());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initTasks(LinkedList<User> listDb){
        listUser.clear();
        listUser.addAll(listDb);

        nameTab.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        surnameTable.setCellValueFactory(new PropertyValueFactory<User, String>("surname"));
        emailTab.setCellValueFactory(new PropertyValueFactory<User, String>("email"));

        usersTable.setItems(listUser);
    }


    private LinkedList<User> showAllUsers() throws IOException {
        Client.connectionToServer.sendMsg("showHistoryUsers");
        return Client.connectionToServer.showAllUsersForClient();
    }

    private void initUsers(LinkedList<User> listDb){
        listUser.clear();
        listUser.addAll(listDb);
        emailTab.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        nameTab.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        surnameTable.setCellValueFactory(new PropertyValueFactory<User, String>("surname"));
        usersTable.setItems(listUser);
    }
}
