package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class AlertBoxLoad {
    static String answer;

    public static String display(String title) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(800);

        Label label1 = new Label("Enter the SCENARIO-ID you wish in order to load the files");
        Label label2 = new Label();
        TextField txt = new TextField();
        txt.setPromptText("scenario-id");

        Button button = new Button("Load");
        button.setOnAction(ae ->{
            if (!txt.getText().toString().trim().equals("")) {
                answer = txt.getText().toString().trim();
                boolean both_files_exist = true;
                try (BufferedReader br = new BufferedReader(new FileReader("airport_"+answer+".txt"))) {
                    String line;
                } catch (FileNotFoundException e) {
                    both_files_exist = false;
                    label2.setText("No such file");
                    //e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try (BufferedReader br = new BufferedReader(new FileReader("setup_"+answer+".txt"))) {
                    String line;
                } catch (FileNotFoundException e) {
                    both_files_exist = false;
                    label2.setText("No such file");
                    //e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (both_files_exist) window.close();
            }
            else {
                label2.setText("You have to enter a scenario-id!");
            }


        });


        VBox layout = new VBox();
        layout.setSpacing(5);
        layout.setPadding(new Insets(10,10,10,10));
        layout.getChildren().addAll(label1, txt, button, label2);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        return answer;
    }
}
