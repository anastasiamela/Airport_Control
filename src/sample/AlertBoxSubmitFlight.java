package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBoxSubmitFlight {

    public static void display(String title, boolean submitted) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(400);

        Label label1 = new Label("1");
        Button button = new Button();
        if (submitted){
            label1.setText("The flight is successfully submitted.");
            button.setText("Close");
        }
        else {
            label1.setText("you have to complete all the fields.");
            button.setText("Try Again");
        }
        button.setOnAction(e -> window.close());

        VBox layout = new VBox();
        layout.setSpacing(5);
        layout.setPadding(new Insets(10,10,10,10));
        layout.getChildren().addAll(label1, button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
