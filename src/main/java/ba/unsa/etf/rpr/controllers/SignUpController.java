package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.MemberManager;
import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Member;
import ba.unsa.etf.rpr.exceptions.BookException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpController {
    //managers
    private final MemberManager memberManager = new MemberManager();

    //components
    public Label neispravanUsername;
    public Label neispravanEmail;
    public Label neispravanBroj;
    List<Member> members = DaoFactory.memberDao().getAll();
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

    public SignUpController() throws BookException {
    }

    @FXML
    public void initialize(){
        sign_passwordId.textProperty().addListener((obs,oldValue,newValue)->{
            if(newValue.trim().length() >= 5){
                neispravanpassId.setText("");
            }
            else{
                neispravanpassId.setText("Use at least 5 characters!");
            }
        });
    }

    public void loginClick(ActionEvent actionEvent) throws IOException {
        Stage ns = (Stage) signUpScreen.getScene().getWindow();
        AbstractController.switchScreen(ns,"login.fxml","Login");
    }

    public void signUpClick(ActionEvent actionEvent) throws IOException, BookException {
        Stage ns = (Stage) signUpScreen.getScene().getWindow();
        boolean sameUsername = false;
        boolean sameEmail = false;
        boolean sameNumber = false;

        for(Member mem : members){
            if(mem.getUsername().equals(sign_usernameId.getText())){
                sameUsername = true;
                break;
            }
            else if (mem.getEmail().equals(sign_emailId.getText())) {
                sameEmail = true;
                break;
            }
            else if (mem.getPhoneNumber().equals(phonenumberId.getText())) {
                sameNumber = true;
                break;
            }
        }

        if(!sameUsername){
            neispravanUsername.setText("");
        }
        else if(!sameEmail){
            neispravanEmail.setText("");
        }
        else if (!sameNumber) {
            neispravanBroj.setText("");
        }

        if(firstnameId.getText().isEmpty() || lastnameId.getText().isEmpty() || sign_usernameId.getText().isEmpty() || sign_passwordId.getText().isEmpty() || sign_emailId.getText().isEmpty() || phonenumberId.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Not all fields have been entered!");
            alert.setHeaderText("Error");
            alert.show();
        }
        else if(sign_passwordId.getText().length() < 5){
            neispravanpassId.setText("Use at least 5 characters!");
        }
        else if (sameUsername) {
            neispravanUsername.setText("Username is already in use!");
        }
        else if (sameEmail) {
            neispravanEmail.setText("E-mail is already in use!");
        }
        else if(sameNumber){
            neispravanBroj.setText("Phone number is already in use!");
        }
        else{

            if(checkEmail(sign_emailId.getText())){
                neispravanEmail.setText("");

                AbstractController.switchScreen(ns,"login.fxml","Login");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("You are successfully registered!");
                alert.setHeaderText("Successful registration");
                alert.show();
                memberManager.add(createMemberObject(firstnameId.getText(),lastnameId.getText(),sign_usernameId.getText(),sign_passwordId.getText(),sign_emailId.getText(),phonenumberId.getText()));
            }
            else{
                neispravanEmail.setText("Incorrect format for e-mail!");
            }

        }
    }

    public static Member createMemberObject(String firstName,String lastName, String username, String password, String email, String phoneNumber){
        Member mem = new Member();

        mem.setFirstName(firstName);
        mem.setLastName(lastName);
        mem.setUsername(username);
        mem.setPassword(password);
        mem.setEmail(email);
        mem.setPhoneNumber(phoneNumber);

        return mem;
    }

    private boolean checkEmail(String emailField){
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(emailField);
        return matcher.matches();
    }

}
