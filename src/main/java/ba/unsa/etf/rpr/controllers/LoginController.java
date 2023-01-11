package ba.unsa.etf.rpr.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class LoginController {

    //components
    public TextField usernameId;
    public PasswordField passwordId;
    public Button loginButton;
    public Button signupButton;
    public Label neispravanpassId;
    public GridPane loginScreen;


    @FXML
    public void initialize(){
        passwordId.textProperty().addListener((obs,oldValue,newValue)->{
            if(newValue.trim().length() >= 5){
                neispravanpassId.setText("");
            }
            else{
                neispravanpassId.setText("Password must have at least five characters!");
            }
        });
    }

    public void signUpClick(ActionEvent actionEvent) throws IOException {
        Stage ns = (Stage) loginScreen.getScene().getWindow();
        AbstractController.switchScreen(ns,"signUp.fxml","Sign Up");
    }

    public void loginClick(ActionEvent actionEvent) {
        if(usernameId.getText().isEmpty() || passwordId.getText().isEmpty()){
           neispravanpassId.setText("Username or password are not correct!");
        }

    }
}
