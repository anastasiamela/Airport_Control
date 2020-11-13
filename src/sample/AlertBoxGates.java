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
import java.util.Hashtable;

public class AlertBoxGates {

    public static void display(String title, ArrayList<Flight> flights, Hashtable<String,Flight> hash, ArrayList<Gates> gates) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        TableView<Gates> table = new TableView<Gates>();
        table.setEditable(false);

        ObservableList<Gates> data = FXCollections.observableArrayList();
        for(Gates g: gates){
            Flight f = hash.get(g.getGateId());
            if (f != null){
                g.setState("Busy");
                g.setFlightId(f.getFlight_id());
                g.setDeparture(String.valueOf(f.getDeparting_time()));
            }
            data.add(g);
        }

        TableColumn gateIdCol = new TableColumn("Gate ID");
        gateIdCol.setMinWidth(100);
        gateIdCol.setCellValueFactory(new PropertyValueFactory<Gates, String>("gateId"));

        TableColumn stateCol = new TableColumn("State");
        stateCol.setMinWidth(100);
        stateCol.setCellValueFactory(new PropertyValueFactory<Gates, String>("state"));

        TableColumn flightIdCol = new TableColumn("Flight ID");
        flightIdCol.setMinWidth(100);
        flightIdCol.setCellValueFactory(new PropertyValueFactory<Gates, String>("flightId"));

        TableColumn departureCol = new TableColumn("Departing Time");
        departureCol.setMinWidth(100);
        departureCol.setCellValueFactory(new PropertyValueFactory<Gates, String>("departure"));

        table.setItems(data);
        table.getColumns().addAll(gateIdCol,stateCol,flightIdCol,departureCol);

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
