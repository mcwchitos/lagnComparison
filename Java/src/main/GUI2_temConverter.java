package main;

import static main.Utils.*;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class GUI2_temConverter extends Application {

    public void start(Stage stage) {
        TextField celNum = new TextField("");

        TextField farNum = new TextField("");

        celNum.textProperty().bindBidirectional(farNum.textProperty(), new StringConverter<String>() {
            public String toString(String fahrenheit) {
                return isNumeric(fahrenheit) ? fToC(fahrenheit) : celNum.getText();
            }
            public String fromString(String celsius) {
                return isNumeric(celsius) ? cToF(celsius) : farNum.getText();
            }
        });

        HBox root = new HBox(10, celNum, new Label("Celsius = "), farNum, new Label("Fahrenheit"));
        root.setPadding(new Insets(10));

        stage.setScene(new Scene(root));
        stage.setTitle("Temperature Converter");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
