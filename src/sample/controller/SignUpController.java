package sample.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.databage.DatabaseHandler;
import sample.model.User;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane signUpCSS;

    @FXML
    private JFXTextField signUpFirstName;

    @FXML
    private JFXTextField signUpLastName;

    @FXML
    private JFXTextField signUpUserName;

    @FXML
    private JFXTextField signUpLocation;

    @FXML
    private JFXRadioButton signUpCheckboxMale;

    @FXML
    private ToggleGroup gender;

    @FXML
    private JFXRadioButton signUpCheckboxFemale;

    @FXML
    private JFXPasswordField signUpPassword;

    @FXML
    private JFXButton signUpButton;

    @FXML
    private Hyperlink signUpPageToSignInPage;

    /*@FXML
    private ComboBox<String> locationComboBox;
    ObservableList<String> locationList = FXCollections.observableArrayList("Bangladesh","USA","UAE","UK","Canada","India");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        locationComboBox.setItems(locationList);
    }

    public void comboChanged(ActionEvent event) {
        myLabel.setText(locationComboBox.getValue());
    }
*/
    @FXML
    void initialize() {

        signUpButton.setOnAction(actionEvent -> {
            CreateUser();

        });

        // it will take to use login page if he/she create a new account
        signUpPageToSignInPage.setOnAction(actionEvent -> {
            signUpPageToSignInPage.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/login.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        });

    }

    private void CreateUser() {

        DatabaseHandler databaseHandler = new DatabaseHandler();

        String fistName = signUpFirstName.getText();
        String lastName = signUpLastName.getText();
        String userName = signUpUserName.getText();
        String password = signUpPassword.getText();
        String location = signUpLocation.getText();


        String gender;
        if (signUpCheckboxFemale.isSelected()) {
            gender = "Female";
        }else gender = "Male";

        User user = new User(fistName,lastName, userName,password, location,gender);
        databaseHandler.signUpUser(user);

    }

}