package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/login.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("view/list.fxml"));
        primaryStage.setTitle("ToDo");
        primaryStage.setScene(new Scene(root, 1200, 700));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
