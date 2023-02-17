package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.RentalManager;
import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Izvjestaj;
import ba.unsa.etf.rpr.domain.Library;
import ba.unsa.etf.rpr.exceptions.BookException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class IzvjestajController {

    private static RentalManager rentalManager = new RentalManager();
    public TableView<Izvjestaj> izvjestajList;
    public TableColumn<Izvjestaj, LocalDate> dateColumn;
    public TableColumn<Izvjestaj,Integer> rentsColumn;
    public BarChart<String,Integer> izvjestajId;
    public CategoryAxis categoryAxisId;
    public NumberAxis numberAxisId;
    public DatePicker startId;
    public DatePicker endId;


    @FXML
    public void initialize() throws BookException {
        dateColumn.setCellValueFactory(new PropertyValueFactory<Izvjestaj,LocalDate>("date"));
        rentsColumn.setCellValueFactory(new PropertyValueFactory<Izvjestaj,Integer>("rents"));
        startId.setValue(LocalDate.now().minusDays(7));
        endId.setValue(LocalDate.now());
        refreshIzvjestajList();

        refreshBarChart();
    }

    private void refreshIzvjestajList(){
        try {
            izvjestajList.setItems(FXCollections.observableList(rentalManager.getByDates(startId.getValue(),endId.getValue())));
            izvjestajList.refresh();
        } catch (BookException e) {
            new Alert(Alert.AlertType.NONE, e.getMessage(), ButtonType.OK).show();
        }
    }

    private void refreshBarChart() throws BookException {
        XYChart.Series<String, Integer> series1 = new XYChart.Series<>();
        List<Izvjestaj> list = rentalManager.getByDates(startId.getValue(),endId.getValue());

        for(Izvjestaj i : list){
            series1.setName(i.getDate().toString());
            series1.getData().add(new XYChart.Data<>(i.getDate().toString(),i.getRents()));

        }
        izvjestajId.getData().add(series1);
    }


    public void backClick(ActionEvent actionEvent) throws IOException {
        Stage ns = (Stage) izvjestajList.getScene().getWindow();
        AbstractController.switchScene(ns,"home.fxml","Home");
    }
}
