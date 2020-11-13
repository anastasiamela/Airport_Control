package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AlertBoxFlights {
    public static void display(String title, ArrayList<Flight> flights){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        TableView<Flight> table = new TableView<Flight>();
        table.setEditable(false);

        ObservableList<Flight> data = FXCollections.observableArrayList();

        for(Flight fl: flights){
            data.add(fl);
        }

        TableColumn flightIdCol = new TableColumn("Flight ID");
        flightIdCol.setMinWidth(100);
        flightIdCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("flight_id"));

        TableColumn cityOfDepartureCol = new TableColumn("Departure City");
        cityOfDepartureCol.setMinWidth(100);
        cityOfDepartureCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("city"));

        TableColumn flightTypeCol = new TableColumn("Flight Type");
        flightTypeCol.setMinWidth(100);
        flightTypeCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("type_flight_string"));

        TableColumn planeTypeCol = new TableColumn("Airplane Type");
        planeTypeCol.setMinWidth(100);
        planeTypeCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("type_airplane_string"));

        TableColumn parkedWhereCol = new TableColumn("Parking Spot");
        parkedWhereCol.setMinWidth(100);
        parkedWhereCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("parked_pos"));

        TableColumn currentStateCol = new TableColumn("Current Situation");
        currentStateCol.setMinWidth(100);
        currentStateCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("situation_flight"));

        TableColumn departureTimeCol = new TableColumn("Departure Time");
        departureTimeCol.setMinWidth(100);
        departureTimeCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("departing_time"));

        table.setItems(data);
        table.getColumns().addAll(flightIdCol, cityOfDepartureCol, flightTypeCol, planeTypeCol, parkedWhereCol, currentStateCol, departureTimeCol);

        VBox layout = new VBox();
        layout.setSpacing(5);
        layout.setPadding(new Insets(10,0,0,10));
        layout.getChildren().addAll(table);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();


    }
}
