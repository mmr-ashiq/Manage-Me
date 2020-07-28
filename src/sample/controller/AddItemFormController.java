package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.databage.DatabaseHandler;
import sample.model.Task;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ResourceBundle;

public class AddItemFormController {

    private int userId;

    private DatabaseHandler databaseHandler;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton toDosButton;

    @FXML
    private Label successLabel;

    @FXML
    private JFXTextArea descriptionField;

    @FXML
    private JFXTextField taskField;

    @FXML
    private JFXButton saveTaskButton;

    @FXML
    void initialize() {

        databaseHandler = new DatabaseHandler();
        Task task = new Task();

        saveTaskButton.setOnAction((ActionEvent event) -> {

            /*
                java.util.Date that allows the JDBC API to identify this as an SQL TIMESTAMP value
                resource: https://docs.oracle.com/javase/8/docs/api/java/sql/Timestamp.html
             */

            Calendar calendar = Calendar.getInstance();

            java.sql.Timestamp timestamp = new java.sql.Timestamp(calendar.getTimeInMillis());

            String taskName = taskField.getText().trim();
            String taskDescription = descriptionField.getText().trim();

            if (!taskName.equals("") || !taskDescription.equals("")) {

                System.out.println("User Id: " + AddItemController.userId);
                task.setUserId(AddItemController.userId);
                task.setDateCreated(timestamp);
                task.setDescription(taskDescription);
                task.setTask(taskName);

                databaseHandler.insertTask(task);

                successLabel.setVisible(true); //this label will visible only when user press save task button
                toDosButton.setVisible(true);
                int taskNumber = 0;
                try {
                    taskNumber = databaseHandler.getAllTasks(AddItemController.userId);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                toDosButton.setText("My ToDo's: " + "(" + taskNumber + ")");

                /* if user write a task and press save button it will remove everything
                   which is written before.
                 */
                taskField.setText("");
                descriptionField.setText("");

                toDosButton.setOnAction(actionEvent -> {
                    // it will send user to the list screen
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/sample/view/list.fxml"));

                    try {
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.showAndWait();

                });

            }
        });

    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
        System.out.println("AddItemFromController: " + this.userId);
    }

}
