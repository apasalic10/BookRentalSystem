package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.MemberManager;
import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Member;
import ba.unsa.etf.rpr.exceptions.BookException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangeProfileController {
    //managers
    private final MemberManager memberManager = new MemberManager();
    //components
    public TextField firstnameId;
    public TextField sign_usernameId;
    public Label neispravanUsername;
    public TextField sign_emailId;
    public Label neispravanEmail;
    public TextField lastnameId;
    public PasswordField sign_passwordId;
    public Label neispravanpassId;
    public TextField phonenumberId;
    public Label neispravanBroj;

    List<Member> members = DaoFactory.memberDao().getAll();

    public ChangeProfileController() throws BookException {
    }

    @FXML
    public void initialize() throws BookException {
        Member mem = memberManager.getByUsername(LoginController.getUsername());

        firstnameId.setText(mem.getFirstName());
        lastnameId.setText(mem.getLastName());
        sign_usernameId.setText(mem.getUsername());
        sign_emailId.setText(mem.getEmail());
        phonenumberId.setText(mem.getPhoneNumber());

        sign_passwordId.textProperty().addListener((obs,oldValue,newValue)->{
            if(newValue.trim().length() >= 5){
                neispravanpassId.setText("");
            }
            else{
                neispravanpassId.setText("Use at least 5 characters!");
            }
        });
    }


    public void backButtonClick(ActionEvent actionEvent) throws IOException {
        Stage ns = (Stage) neispravanBroj.getScene().getWindow();
        AbstractController.switchScene(ns,"home.fxml","Home");
    }

    public void saveButtonClick(ActionEvent actionEvent) throws IOException, BookException {
        Stage ns = (Stage) neispravanBroj.getScene().getWindow();
        boolean sameUsername = false;
        boolean sameEmail = false;
        boolean sameNumber = false;

        for(Member mem : members){
            if(mem.getUsername().equals(sign_usernameId.getText()) && !mem.getUsername().equals(LoginController.getUsername())){
                sameUsername = true;
                break;
            }
            else if (mem.getEmail().equals(sign_emailId.getText()) && !mem.getEmail().equals(memberManager.getByUsername(LoginController.getUsername()).getEmail())) {
                sameEmail = true;
                break;
            }
            else if (mem.getPhoneNumber().equals(phonenumberId.getText()) && !mem.getPhoneNumber().equals(memberManager.getByUsername(LoginController.getUsername()).getPhoneNumber())) {
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

                AbstractController.switchScene(ns,"home.fxml","Home");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("You have successfully updated your profile!");
                alert.setHeaderText(null);
                alert.show();
                memberManager.update(createMemberObject(firstnameId.getText(),lastnameId.getText(),sign_usernameId.getText(),sign_passwordId.getText(),sign_emailId.getText(),phonenumberId.getText()));
            }
            else{
                neispravanEmail.setText("Incorrect format for e-mail!");
            }

        }
    }

    private Member createMemberObject(String firstName,String lastName, String username, String password, String email, String phoneNumber) throws BookException {
        Member mem = new Member();

        mem.setId(memberManager.getByUsername(LoginController.getUsername()).getId());
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
