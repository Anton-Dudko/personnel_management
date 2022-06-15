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

public class ClientWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exitBtn;

    @FXML
    private Button editAccountBtn;

    @FXML
    private TableView<Task> tasksTable;

    @FXML
    private TableColumn<Task, String> taskTab;

    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, String> emailTab;

    @FXML
    private TableColumn<User, String> nameTab;

    @FXML
    private TableColumn<User, String> surnameTab;

    private final ObservableList<User> listUsers = FXCollections.observableArrayList();
    private final ObservableList<Task> listTasks = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        exitBtn.setOnAction(actionEvent -> {
            Windows.windows.newWindow("views/sample.fxml", exitBtn);
        });

        try {
            initTasks(showAllTasks());
        } catch (IOException e) {
            e.printStackTrace();
        }

        editAccountBtn.setOnAction(actionEvent -> {
            Windows.windows.newWindow("views/editAccount.fxml", editAccountBtn);
        });

        try {
            initUsers(showAllUsers());
            initField();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private LinkedList<User> showAllUsers() throws IOException {
        Client.connectionToServer.sendMsg("showAllUserForClient");
        return Client.connectionToServer.showAllUsersForClient();
    }

    private void initUsers(LinkedList<User> listDb){
        listUsers.clear();
        listUsers.addAll(listDb);
        emailTab.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        nameTab.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        surnameTab.setCellValueFactory(new PropertyValueFactory<User, String>("surname"));
        usersTable.setItems(listUsers);
    }

    private void initField() throws IOException {

        Client.connectionToServer.sendMsg("currentUserData");
        Client.connectionToServer.sendMsg(String.valueOf(User.currentUser.getId()));
        Client.connectionToServer.initUserFullParams();
    }

    private void initTasks(LinkedList<Task> listDb){
        listTasks.clear();
        listTasks.addAll(listDb);

        taskTab.setCellValueFactory(new PropertyValueFactory<Task, String>("title"));

        tasksTable.setItems(listTasks);
    }


    private LinkedList<Task> showAllTasks() throws IOException {
        Client.connectionToServer.sendMsg("showAllTasksForClient");
        Client.connectionToServer.sendMsg(String.valueOf(User.currentUser.getEmail()));

        return Client.connectionToServer.showAllTasksForClient();
    }
}
