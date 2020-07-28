package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.databage.DatabaseHandler;
import sample.model.Task;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;


public class CellController extends ListCell<Task> {

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private ImageView iconImageView;

    @FXML
    private Label taskLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private ImageView deleteButton;

    @FXML
    private ImageView listUpdateButton;

    private FXMLLoader fxmlLoader;

    private DatabaseHandler databaseHandler;

    @FXML
    void initialize() throws SQLException{

    }

    @Override
    protected void updateItem(Task task, boolean empty) {

        databaseHandler = new DatabaseHandler();
        super.updateItem(task, empty);

        if (empty || task == null) {
            setText(null);
            setGraphic(null);
        }else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/cell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            taskLabel.setText(task.getTask());
            dateLabel.setText(task.getDateCreated().toString());
            descriptionLabel.setText(task.getDescription());

            int taskId = task.getTaskId();

            listUpdateButton.setOnMouseClicked(mouseEvent -> {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/view/updateTaskForm.fxml"));


                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));

                UpdateTaskController updateTaskController = loader.getController();
                updateTaskController.setTaskField(task.getTask());
                updateTaskController.setUpdateDescriptionField(task.getDescription());

                updateTaskController.updateTaskButton.setOnAction(actionEvent -> {

//                    updateTaskController.updateTaskButton.getScene().getWindow().hide();

                    Calendar calendar = Calendar.getInstance();
                    java.sql.Timestamp timestamp =
                            new Timestamp(calendar.getTimeInMillis());

                    try {

                        System.out.println("taskid " + task.getTaskId());

                        databaseHandler.updateTask(timestamp, updateTaskController.getDescription(),
                                updateTaskController.getTask(), task.getTaskId());

                        //update our listController
                        //updateTaskController.refreshList();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                });

                stage.show();


            });


            //it will remove current delete clicked item
            deleteButton.setOnMouseClicked(event -> {
                databaseHandler = new DatabaseHandler();
                try {
                    databaseHandler.deleteTask(AddItemController.userId, taskId);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                getListView().getItems().remove(getItem());
            });

            setText(null);
            setGraphic(rootAnchorPane);
        }
    }
}
