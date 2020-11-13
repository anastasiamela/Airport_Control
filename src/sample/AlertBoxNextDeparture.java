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

public class AlertBoxNextDeparture {
    public static void display(String title, ArrayList<Flight> flights, int current_minutes) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(500);

        TableView<Flight> table = new TableView<Flight>();
        table.setEditable(false);

        ObservableList<Flight> data = FXCollections.observableArrayList();

        for (Flight fl : flights) {
            int time_should_leave = fl.getParking_time() + fl.getDeparting_time();
            if(time_should_leave < current_minutes) {
                if(time_should_leave - current_minutes <= 10) data.add(fl);
            }
            data.add(fl);
        }

        TableColumn flightIdCol = new TableColumn("Flight ID");
        flightIdCol.setMinWidth(100);
        flightIdCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("flight_id"));

        TableColumn flightTypeCol = new TableColumn("Flight Type");
        flightTypeCol.setMinWidth(100);
        flightTypeCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("type_flight_string"));

        TableColumn planeTypeCol = new TableColumn("Airplane Type");
        planeTypeCol.setMinWidth(100);
        planeTypeCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("type_airplane_string"));

        table.setItems(data);
        table.getColumns().addAll(flightIdCol,flightTypeCol,planeTypeCol);

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
