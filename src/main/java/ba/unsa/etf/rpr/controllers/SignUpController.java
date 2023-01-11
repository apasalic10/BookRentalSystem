package ba.unsa.etf.rpr.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController {
    public GridPane signUpScreen;
    public TextField firstnameId;
    public TextField sign_usernameId;
    public TextField sign_emailId;
    public TextField lastnameId;
    public PasswordField sign_passwordId;
    public Button signUpButtonId;
    public Button loginButtonId;
    public Label neispravanpassId;
    public TextField phonenumberId;

    @FXML
    public void initialize(){
        sign_passwordId.textProperty().addListener((obs,oldValue,newValue)->{
            if(newValue.trim().length() >= 5){
                neispravanpassId.setText("");
            }
            else{
                neispravanpassId.setText("Use at least 5 charachters!");
            }
        });
    }

    public void loginClick(ActionEvent actionEvent) throws IOException {
        Stage ns = (Stage) signUpScreen.getScene().getWindow();
        AbstractController.switchScreen(ns,"login.fxml","Login");
    }

    public void signUpClick(ActionEvent actionEvent) {
        if(firstnameId.getText().isEmpty() || lastnameId.getText().isEmpty() || sign_usernameId.getText().isEmpty() || sign_passwordId.getText().isEmpty() || sign_emailId.getText().isEmpty() || phonenumberId.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Not all fields have been entered!");
            alert.setHeaderText("Error");
            alert.show();
        }
    }
}
