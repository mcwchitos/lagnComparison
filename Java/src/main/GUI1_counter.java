package main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class GUI1_counter extends Application {

    public void start(Stage stage) {
        TextField count = new TextField("0");
        count.setEditable(false);
        count.setPrefWidth(50);
        Button countUp = new Button("Count up");
        Button countDown = new Button("Count down");

        countUp.setOnAction(e ->
                count.setText(Integer.parseInt(count.getText()) + 1 + ""));
        countDown.setOnAction(e ->
                count.setText(Integer.parseInt(count.getText()) - 1 + ""));

        HBox root = new HBox(20, countDown, count, countUp);
        root.setPadding(new Insets(25));

        stage.setScene(new Scene(root));
        stage.setTitle("Counter");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
