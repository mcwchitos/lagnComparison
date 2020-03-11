package main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerExpression;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static main.Utils.*;

public class GUIs extends Application {

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

        HBox root1 = new HBox(20, countDown, count, countUp);
        root1.setPadding(new Insets(25));

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

        HBox root2 = new HBox(10, celNum, new Label("Celsius = "), farNum, new Label("Fahrenheit"));
        root2.setPadding(new Insets(10));

        ComboBox<String> flightType = new ComboBox<>();
        flightType.getItems().addAll("one-way flight", "return flight");
        flightType.setValue("one-way flight");
        TextField startDate = new TextField(dateToString(LocalDate.now()));
        TextField returnDate = new TextField(dateToString(LocalDate.now()));
        Button book = new Button("Book");

        returnDate.disableProperty().bind(flightType.valueProperty().isEqualTo("one-way flight"));
        startDate.styleProperty().bind(Bindings.createStringBinding(() ->
                        isDateString(startDate.getText()) ? "" : "-fx-background-color: lightcoral"
                , startDate.textProperty()));
        returnDate.styleProperty().bind(Bindings.createStringBinding(() ->
                        isDateString(returnDate.getText()) ? "" : "-fx-background-color: lightcoral"
                , returnDate.textProperty()));
        book.disableProperty().bind(Bindings.createBooleanBinding(() -> {
            if (flightType.getValue().equals("one-way flight")) {
                return !isDateString(startDate.getText());
            } else {
                return !isDateString(startDate.getText()) ||
                        !isDateString(returnDate.getText()) ||
                        stringToDate(startDate.getText()).compareTo(stringToDate(returnDate.getText())) > 0;
            }
        }, flightType.valueProperty(), startDate.textProperty(), returnDate.textProperty()));

        VBox root3 = new VBox(10, flightType, startDate, returnDate, book);
        root3.setPadding(new Insets(10));

        ProgressBar progress = new ProgressBar();
        Label numericProgress = new Label();
        Slider slider = new Slider(1, 400, 200);
        Button reset = new Button("Reset");

        SimpleDoubleProperty elapsed = new SimpleDoubleProperty(0);
        progress.progressProperty().bind(elapsed.divide(slider.valueProperty()));
        numericProgress.textProperty().bind(Bindings.createStringBinding(() ->
                formatElapsed(elapsed.intValue()), elapsed));
        reset.setOnAction(event -> elapsed.set(0));

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            if (elapsed.get() < slider.valueProperty().get()) elapsed.set(elapsed.get() + 1);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        VBox root4 = new VBox(10, new HBox(10, new Label("Elapsed Time: "), progress),
                numericProgress,
                new HBox(10, new Label("Duration: "), slider),
                reset);
        root4.setPadding(new Insets(10));

        TextField prefix = new TextField();
        prefix.setPrefWidth(60);
        TextField name = new TextField();
        name.setPrefWidth(100);
        TextField surname = new TextField();
        surname.setPrefWidth(100);
        Button create = new Button("Create");
        Button update = new Button("Update");
        Button delete = new Button("Delete");
        update.setDisable(true);
        delete.setDisable(true);
        ListView<String> entries = new ListView<>();
        entries.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        List<String> externDb = new ArrayList<>();
        externDb.add("Emil, Hans");
        externDb.add("Musterman, Max");
        externDb.add("Tisch, Roman");
        ObservableList<String> db = FXCollections.observableArrayList(externDb);
        db.addListener((ListChangeListener<String>) c -> {
            while (c.next()) {
                if (c.wasReplaced()) externDb.set(c.getFrom(), c.getAddedSubList().get(0));
                else {
                    if (c.wasAdded()) externDb.add(c.getAddedSubList().get(0));
                    if (c.wasRemoved()) externDb.remove(c.getFrom());
                }
            }
        });
        FilteredList<String> dbView = db.filtered(item -> true);
        entries.setItems(dbView);

        StringExpression fullname = surname.textProperty().concat(", ").concat(name.textProperty());
        IntegerExpression selectedIndex = entries.getSelectionModel().selectedIndexProperty();
        prefix.textProperty().addListener((v, o, n) -> dbView.setPredicate(item -> item.startsWith(n)));
        create.setOnAction(e -> db.add(fullname.get()));
        delete.setOnAction(e -> db.remove(dbView.getSourceIndex(selectedIndex.get())));
        update.setOnAction(e -> db.set(dbView.getSourceIndex(selectedIndex.get()), fullname.get()));
        delete.disableProperty().bind(selectedIndex.isEqualTo(-1));
        update.disableProperty().bind(selectedIndex.isEqualTo(-1));

        BorderPane root5 = new BorderPane();
        root5.setPrefSize(400, 400);
        root5.setPadding(new Insets(10));
        HBox top5 = new HBox(10, new Label("Filter prefix: "), prefix);
        top5.setPadding(new Insets(0, 0, 10, 0));
        top5.setAlignment(Pos.BASELINE_LEFT);
        root5.setTop(top5);
        root5.setCenter(entries);
        GridPane right = new GridPane();
        right.setHgap(10);
        right.setVgap(10);
        right.setPadding(new Insets(0, 0, 0, 10));
        right.addRow(0, new Label("Name: "), name);
        right.addRow(1, new Label("Surname: "), surname);
        root5.setRight(right);
        HBox bottom = new HBox(10, create, update, delete);
        bottom.setPadding(new Insets(10, 0, 0, 0));
        root5.setBottom(bottom);

        Button undo = new Button("Undo");
        Button redo = new Button("Redo");
        CircleDrawerCanvas canvas = new CircleDrawerCanvas();

        undo.setOnAction(e -> canvas.undo());
        redo.setOnAction(e -> canvas.redo());

        BorderPane root6 = new BorderPane();
        root6.setPadding(new Insets(10));
        HBox top6 = new HBox(10, undo, redo);
        top6.setPadding(new Insets(0, 0, 10, 0));
        root6.setTop(top6);
        root6.setCenter(canvas);

        SpreadSheet root7 = new SpreadSheet(100, 26);


        FlowPane root = new FlowPane();
        root.getChildren().addAll(root1, root2, root3, root4, root5, root6, root7);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("GUIs");
        stage.show();
    }

    private static String formatElapsed(int elapsed) {
        int seconds = (int) Math.floor(elapsed / 10.0);
        int dezipart = elapsed % 10;
        return seconds + "." + dezipart + "s";
    }

    public static void main(String[] args) {
        launch(args);
    }
}
