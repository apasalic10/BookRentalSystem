package ba.unsa.etf.rpr.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    public TextField usernameId;
    public PasswordField passwordId;
    public Button loginButton;
    public Button signupButton;
    public Label neispravanpassId;


    @FXML
    public void initialize(){
        passwordId.textProperty().addListener((obs,oldValue,newValue)->{
            if(newValue.length() >= 5){
                neispravanpassId.setText("");
            }
            else{
                neispravanpassId.setText("Password must have at least five characters!");
            }
        });
    }
}
