package sample.controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import sample.animations.Shaker;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddItemController {

    public static int userId;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label noTaskLabel;

    @FXML
    private ImageView addIconButton;

    @FXML
    void initialize() {

        addIconButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> { // event to mouse event
            Shaker buttonShaker = new Shaker(addIconButton);
            buttonShaker.shake();

            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), addIconButton);
            FadeTransition labelTransition = new FadeTransition(Duration.seconds(1), noTaskLabel);

            //remove
            System.out.println("Added Clicked!");
            addIconButton.relocate(0, 20);
            noTaskLabel.relocate(0, 45);

            addIconButton.setOpacity(0);
            noTaskLabel.setOpacity(0);

            //animation
            fadeTransition.setFromValue(1f);
            fadeTransition.setToValue(0f);
            fadeTransition.setCycleCount(1);
            fadeTransition.setAutoReverse(false);
            fadeTransition.play();

            labelTransition.setFromValue(1f);
            labelTransition.setToValue(0f);
            labelTransition.setCycleCount(1);
            labelTransition.setAutoReverse(false);
            labelTransition.play();

            try {
                AnchorPane fromPane = FXMLLoader.load(getClass().getResource("/sample/view/addItemForm.fxml"));
                AddItemController.userId = getUserId();

//                AddItemFormController addItemFormController = new AddItemFormController();
//                addItemFormController.setUserId(getUserId());

                FadeTransition rootTransition = new FadeTransition(Duration.seconds(2), fromPane);
                rootTransition.setFromValue(0f);
                rootTransition.setToValue(1f);
                rootTransition.setCycleCount(1);
                rootTransition.setAutoReverse(false);
                rootTransition.play();

                rootPane.getChildren().setAll(fromPane);

            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }

    public void setUserId(int userId) {

        this.userId = userId;
        System.out.println("AddItemController-user id: " + this.userId);
    }

    public int getUserId() {
        return this.userId;
    }

}
