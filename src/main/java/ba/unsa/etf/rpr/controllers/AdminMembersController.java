package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.MemberManager;
import ba.unsa.etf.rpr.business.RentalManager;
import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Library;
import ba.unsa.etf.rpr.domain.Member;
import ba.unsa.etf.rpr.domain.Rental;
import ba.unsa.etf.rpr.exceptions.BookException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AdminMembersController {
    //managers
    private final RentalManager rentalManager = new RentalManager();
    private final MemberManager memberManager = new MemberManager();
    //components
    public TableView<Member> memberList;
    public TableColumn<Member,Integer> idColumn;
    public TableColumn<Member, String> firstNameColumn;
    public TableColumn<Member, String> lastNameColumn;
    public TableColumn<Member, String> usernameColumn;
    public TableColumn<Member, String> emailColumn;
    public TableColumn<Member, String> phoneNumberColumn;

    public VBox vboxId;

    @FXML
    public void initialize() throws IOException {
        idColumn.setCellValueFactory(new PropertyValueFactory<Member,Integer>("Id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Member,String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Member,String>("lastName"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<Member,String>("username"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Member,String>("email"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<Member,String>("phoneNumber"));

        refreshMembers();


        /*memberList.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection) -> {
            if(newSelection != null){
                Member member = (Member) newSelection;
                firstNameId.setText(member.getFirstName());
                lastNameId.setText(member.getLastName());
                usernameId.setText(member.getUsername());
                emailId.setText(member.getEmail());
                phoneNumberId.setText(member.getPhoneNumber());
            }
        });*/

    }

    private void refreshMembers() {
        try {
            memberList.setItems(FXCollections.observableList(memberManager.getAll()));
            memberList.refresh();
        } catch (BookException e) {
            new Alert(Alert.AlertType.NONE, e.getMessage(), ButtonType.OK).show();
        }
    }

    public void deleteClick(ActionEvent actionEvent) throws BookException {

        Member member = (Member) memberList.getSelectionModel().getSelectedItem();

        if(member != null){
            for(Rental r : rentalManager.searchByMember(member)){
                rentalManager.delete(r.getId());
            }
            memberManager.delete(member.getId());
        }

        refreshMembers();
    }

    public void backClick(ActionEvent actionEvent) throws IOException {
        Stage ns = (Stage) vboxId.getScene().getWindow();
        AbstractController.switchScene(ns,"adminScreen.fxml","Admin Screen");
    }


}
