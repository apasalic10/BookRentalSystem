package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.MemberManager;
import ba.unsa.etf.rpr.exceptions.BookException;
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

/**
 * Controller for managing login screen
 * @author Almedin Pasalic
 */
public class LoginController {

    //managers
    private final MemberManager memberManager = new MemberManager();


    //components
    public TextField usernameId;
    public PasswordField passwordId;
    public Button loginButton;
    public Button signupButton;
    public Label neispravanpassId;
    public GridPane loginScreen;

    //temp
    public static String username;

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

    public void loginClick(ActionEvent actionEvent) throws BookException {
        Stage ns = (Stage) loginScreen.getScene().getWindow();

        if(usernameId.getText().isEmpty() || passwordId.getText().isEmpty()){
           neispravanpassId.setText("Username or password are not correct!");
           return;
        }

        try{
            if(passwordId.getText().equals(memberManager.getByUsername(usernameId.getText()).getPassword())){
                if(usernameId.getText().equals("apasalic1")){
                    AbstractController.switchScreen(ns,"adminScreen.fxml","Admin Screen");
                    username = usernameId.getText();
                }
                else{
                    username = usernameId.getText();
                    AbstractController.switchScreen(ns,"home.fxml","Home");

                }
            }
            else{
                neispravanpassId.setText("Username or password are not correct!");
            }
        }catch(BookException | IOException e){
            neispravanpassId.setText("Username or password are not correct!");
        }
    }

    public static String getUsername(){
        return username;
    }
}
