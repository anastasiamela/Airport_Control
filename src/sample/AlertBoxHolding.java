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

public class AlertBoxHolding {

    public static void display(String title, ArrayList<Flight> flights){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);

        TableView<Flight> table = new TableView<Flight>();
        table.setEditable(false);

        ObservableList<Flight> data = FXCollections.observableArrayList();

        for(Flight fl: flights){
            if(fl.getSituation_flight()=="HOLDING") data.add(fl);
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

        TableColumn firstTimeCol = new TableColumn("First Contact Time");
        firstTimeCol.setMinWidth(150);
        firstTimeCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("first_time"));

        table.setItems(data);
        table.getColumns().addAll(flightIdCol,flightTypeCol,planeTypeCol,firstTimeCol);

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
