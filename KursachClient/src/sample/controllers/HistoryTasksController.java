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

public class HistoryTasksController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Task> tasksTable;

    @FXML
    private TableColumn<Task, String> tasksTab;

    @FXML
    private Button exitButton;

    private final ObservableList<Task> listTask = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        exitButton.setOnAction(actionEvent -> {
            Windows.windows.newWindow("views/adminWindow.fxml", exitButton);
        });

        try {
            initTasks(showAllTasks());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initTasks(LinkedList<Task> listDb){
        listTask.clear();
        listTask.addAll(listDb);

        tasksTab.setCellValueFactory(new PropertyValueFactory<Task, String>("title"));

        tasksTable.setItems(listTask);
    }

    private LinkedList<Task> showAllTasks() throws IOException {
        Client.connectionToServer.sendMsg("showHistoryTasks");
        return Client.connectionToServer.showHistoryTasks();
    }
}
