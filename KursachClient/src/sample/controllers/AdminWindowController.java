package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Client;
import sample.Windows;
import sample.model.Task;
import sample.model.User;

public class AdminWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button historyTasksBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private Button historyUsersBtn;

    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, Integer> idUserTab;

    @FXML
    private TableColumn<User, String> emailTab;

    @FXML
    private TableColumn<User, String> passwordTab;

    @FXML
    private TableColumn<User, Integer> rollTab;

    @FXML
    private TableView<Task> tasksTable;

    @FXML
    private TableColumn<Task, Integer> idTaskTab;

    @FXML
    private TableColumn<Task, String> emailTab1;

    @FXML
    private TableColumn<Task, String> taskTab;
    @FXML
    private TextField idField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField rollField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button chooseButton;

    @FXML
    private Button redactionButton;

    @FXML
    private Button deleteAccountButton;

    @FXML
    private Button newTaskBtn;

    @FXML
    private TextField emailTaskField;

    @FXML
    private TextField newTaskField;

    @FXML
    private TextField idTaskField;

    @FXML
    private TextField taskField;

    @FXML
    private Button chooseTaskButton;

    @FXML
    private Button deleteTaskButton;

    @FXML
    private TextField emailNewTaskField;

    private final ObservableList<User> listUsers = FXCollections.observableArrayList();
    private final ObservableList<Task> listTask = FXCollections.observableArrayList();

    LinkedList<Task> list = new LinkedList<>(listTask);




    @FXML
    void initialize() {

        historyTasksBtn.setOnAction(actionEvent -> {
            for (Task t : list) {
                System.out.println(list.toString());

            }
            Windows.windows.newWindow("views/historyTasks.fxml", historyTasksBtn);
        });

        exitBtn.setOnAction(actionEvent -> {
            Windows.windows.newWindow("views/sample.fxml", exitBtn);
        });

        try {
            initUsers(showAllUsers());
            initTasks(showAllTasks());
        } catch (IOException e) {
            e.printStackTrace();
        }

        chooseButton.setOnAction(event -> {
            initFieldToRedaction();
        });

        redactionButton.setOnAction(event -> {
            Client.connectionToServer.sendMsg("redactionUser");
            Client.connectionToServer.sendMsg(idField.getText()+" "+emailField.getText()+" "+passwordField.getText()+" "+rollField.getText());
            try {
                initUsers(showAllUsers());
                idField.setText("");
                emailField.setText("");
                passwordField.setText("");
                rollField.setText("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        deleteAccountButton.setOnAction(actionEvent -> {
            if(usersTable.getSelectionModel().getSelectedItem() != null) {
                if(!User.currentUser.getEmail().equals(emailField.getText())){
                    Client.connectionToServer.sendMsg("deleteUser");
                    Client.connectionToServer.sendMsg(idField.getText());
                    try {
                        initUsers(showAllUsers());
                        idField.setText("");
                        emailField.setText("");
                        passwordField.setText("");
                        rollField.setText("");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    showMessage("Вы не можете удалить свой аккаунт !");
                    idField.setText("");
                    emailField.setText("");
                    passwordField.setText("");
                    rollField.setText("");
                }
            }
            else{
                showMessage("Выберите строку !");
            }
        });

        historyUsersBtn.setOnAction(actionEvent -> {
            Windows.windows.newWindow("views/historyUsers.fxml", historyUsersBtn);
        });

        newTaskBtn.setOnAction(actionEvent -> {
            LinkedList<Task> w = null;
            try {
                w = showAllTasks();
            } catch (IOException e) {
                e.printStackTrace();
            }
            LinkedList<String> z = new LinkedList<>();
            for (int i = 0; i < w.size(); i++) {
                z.add(w.get(i).getTitle());

            }
            if (!emailNewTaskField.getText().equals("") && !newTaskField.getText().equals("") && !z.contains(newTaskField.getText())){
                addNewTask();
            }
            else{
                showMessage("Заполните пустые поля !");
                return;
            }
            initField();
        });

        chooseTaskButton.setOnAction(event -> {
            initTaskFieldToRedaction();
        });

        deleteTaskButton.setOnAction(actionEvent -> {
            if(tasksTable.getSelectionModel().getSelectedItem() != null) {
                Client.connectionToServer.sendMsg("deleteTask");
                Client.connectionToServer.sendMsg(idTaskField.getText());
                try {
                    initTasks(showAllTasks());
                    idTaskField.setText("");
                    emailTaskField.setText("");
                    taskField.setText("");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                showMessage("Вы не можете удалить свой аккаунт !");
                idTaskField.setText("");
                emailTaskField.setText("");
                taskField.setText("");
            }
        });

    }

    private LinkedList<User> showAllUsers() throws IOException {
        Client.connectionToServer.sendMsg("showAllUser");
        return Client.connectionToServer.showAllUsers();
    }

    private void initUsers(LinkedList<User> listDb){
        listUsers.clear();
        listUsers.addAll(listDb);
        idUserTab.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        emailTab.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        passwordTab.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        rollTab.setCellValueFactory(new PropertyValueFactory<User, Integer>("roll"));
        usersTable.setItems(listUsers);
    }

    private void initFieldToRedaction(){
        LinkedList<User> listUsersDb = new LinkedList<>();
        listUsersDb.addAll(listUsers);
        if(usersTable.getSelectionModel().getSelectedItem() != null) {
            int count = usersTable.getSelectionModel().getSelectedCells().get(0).getRow();
            idField.setEditable(false);
            idField.setText(String.valueOf(listUsers.get(count).getId()));
            emailField.setText(listUsers.get(count).getEmail());
            passwordField.setText(listUsers.get(count).getPassword());
            rollField.setText(String.valueOf(listUsers.get(count).getRoll()));
        }
    }

    private void addNewTask(){

        Client.connectionToServer.sendMsg("addTask");
        Client.connectionToServer.sendMsg(emailNewTaskField.getText() + " " + newTaskField.getText());
        if(Client.connectionToServer.checkAddTaskInDb()){
            showMessage("Задание было добавлено !");
        }
        else {
            showMessage("Что то пошло не так !");
        }
    }

    private void initTaskFieldToRedaction(){
        LinkedList<Task> listTasksDb = new LinkedList<>();
        listTasksDb.addAll(listTask);
        if(tasksTable.getSelectionModel().getSelectedItem() != null) {
            int count = tasksTable.getSelectionModel().getSelectedCells().get(0).getRow();
            idField.setEditable(false);
            idTaskField.setText(String.valueOf(listTask.get(count).getId()));
            emailTaskField.setText(listTask.get(count).getEmail());
            taskField.setText(listTask.get(count).getTitle());
        }
    }

    private void initTasks(LinkedList<Task> listDb){
        listTask.clear();
        listTask.addAll(listDb);

        idTaskTab.setCellValueFactory(new PropertyValueFactory<Task, Integer>("id"));
        emailTab1.setCellValueFactory(new PropertyValueFactory<Task, String>("email"));
        taskTab.setCellValueFactory(new PropertyValueFactory<Task, String>("title"));

        tasksTable.setItems(listTask);
    }

    private LinkedList<Task> showAllTasks() throws IOException {
        Client.connectionToServer.sendMsg("showAllTasks");
        return Client.connectionToServer.showAllTasks();
    }

    private LinkedList<String> qqq() throws IOException {
            LinkedList<Task> w = showAllTasks();
            LinkedList<String> z = new LinkedList<>();
        for (int i = 0; i < w.size(); i++) {
            z.add(w.get(i).getTitle());

        }
        return z;
    }

    private void showMessage(String massage){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success ...");
        alert.setHeaderText(null);
        alert.setContentText(massage);
        alert.showAndWait();
    }

    private void initField(){
        emailNewTaskField.setText("");
        newTaskField.setText("");
    }
}


