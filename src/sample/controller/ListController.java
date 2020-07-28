package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import sample.databage.DatabaseHandler;
import sample.model.Task;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.ResourceBundle;

public class ListController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView listRefreshButton;

    @FXML
    private JFXTextField listTaskField;

    @FXML
    private JFXTextArea listDescriptionField;

    @FXML
    private JFXButton listSaveTaskButton;

    @FXML
    private JFXListView<Task> listTask;

    private ObservableList<Task> tasks;
    private ObservableList<Task> refreshedTasks;

    private DatabaseHandler databaseHandler;

    @FXML
    void initialize() throws SQLException {

        tasks = FXCollections.observableArrayList();

        databaseHandler = new DatabaseHandler();
        ResultSet resultSet = databaseHandler.getTasksByUser(AddItemController.userId);

        while (resultSet.next()) {
            Task task = new Task();
            task.setTaskId(resultSet.getInt("taskid"));
            task.setTask(resultSet.getString("task"));
            task.setDateCreated(resultSet.getTimestamp("datecreated"));
            task.setDescription(resultSet.getString("description"));

            tasks.addAll(task);

        }

        listTask.setItems(tasks);
        listTask.setCellFactory(CellController -> new CellController());

        listRefreshButton.setOnMouseClicked(event -> {
            try {
                refreshList();
            } catch (SQLException throwAbles) {
                throwAbles.printStackTrace();
            }
        });
        listSaveTaskButton.setOnAction(event -> {
            addNewTask();
        });
    }

    public void refreshList() throws SQLException {

        refreshedTasks = FXCollections.observableArrayList();

        DatabaseHandler databaseHandler = new DatabaseHandler();
        ResultSet resultSet = databaseHandler.getTasksByUser(AddItemController.userId);

        while (resultSet.next()) {
            Task task = new Task();
            task.setTaskId(resultSet.getInt("taskid"));
            task.setTask(resultSet.getString("task"));
            task.setDateCreated(resultSet.getTimestamp("datecreated"));
            task.setDescription(resultSet.getString("description"));

            refreshedTasks.addAll(task);
        }

        listTask.setItems(refreshedTasks);
        listTask.setCellFactory(CellController -> new CellController());
    }

    public void addNewTask() {
        if (!listTaskField.getText().equals("") || !listDescriptionField.getText().equals("")) {
            Task myNewTask = new Task();

//            System.out.println("list controller: button Clicked");
            Calendar calendar = Calendar.getInstance();
            java.sql.Timestamp timestamp = new java.sql.Timestamp(calendar.getTimeInMillis());

            myNewTask.setUserId(AddItemController.userId);
            myNewTask.setDateCreated(timestamp);
            myNewTask.setDescription(listDescriptionField.getText().trim());
            myNewTask.setTask(listTaskField.getText().trim());

            databaseHandler.insertTask(myNewTask);

            listTaskField.setText("");
            listDescriptionField.setText("");

            try {
                initialize();
            } catch (SQLException throwAbles) {
                throwAbles.printStackTrace();
            }

        }
    }
}