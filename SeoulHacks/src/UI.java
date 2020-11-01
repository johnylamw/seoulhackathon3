import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class UI extends Application{

    public void start(Stage window) {

        BorderPane layout = new BorderPane();

        // Slider Code
        VBox interactableElements = new VBox();
        Label header = new Label("Probability Simulation of Covid-19 Cases (Law of Large Numbers)");
        header.setFont(Font.font(15));

        interactableElements.getChildren().add(header);
        BorderPane trialsSliderLayout = new BorderPane();
        BorderPane probabilitySliderLayout = new BorderPane();

        Slider trialsSlider = new Slider(3, 100, 1);
        Slider probabilitySlider = new Slider(0.00, 1.00, 0.00);

        Label trialsValue = new Label(String.valueOf(1));
        Label probabilityValue = new Label(String.valueOf(probabilitySlider.getValue()));
        Label trialsText = new Label("Trials: ");
        Label probabilityText = new Label("Probability: ");

        trialsValue.setFont(Font.font(25));
        probabilityValue.setFont(Font.font(25));
        trialsText.setFont(Font.font(25));
        probabilityText.setFont(Font.font(25));

        trialsSliderLayout.setLeft(trialsText);
        probabilitySliderLayout.setLeft(probabilityText);

        trialsSliderLayout.setCenter(trialsSlider);
        probabilitySliderLayout.setCenter(probabilitySlider);

        trialsSliderLayout.setRight(trialsValue);
        probabilitySliderLayout.setRight(probabilityValue);

        interactableElements.getChildren().add(trialsSliderLayout);
        interactableElements.getChildren().add(probabilitySliderLayout);

        layout.setTop(interactableElements);

        // Layout for the Statistics:
        VBox statisticsBox = new VBox();

        Label actualProbabilityLabel = new Label();
        actualProbabilityLabel.setFont(Font.font(25));
        actualProbabilityLabel.setText("Probability is UNKNOWN");

        Label summary = new Label();
        summary.setFont(Font.font(25));
        summary.setText("Text Summary :)");

        statisticsBox.getChildren().add(actualProbabilityLabel);
        statisticsBox.getChildren().add(summary);
        layout.setBottom(statisticsBox);


        // event handler when the slider is moved
        trialsSlider.valueProperty().addListener((event) -> {
            int rounded = (int) Math.round(trialsSlider.getValue());
            trialsValue.setText(String.valueOf(rounded));
        });

        probabilitySlider.valueProperty().addListener((event) -> {
            double rounded = Math.round(probabilitySlider.getValue() * 100.0) / 100.0;
            probabilityValue.setText(String.valueOf(rounded));
        });

        // "Run Simulation" Button
        Button runButton = new Button("Run the Simulation");
        runButton.setFont(Font.font(20));
        interactableElements.getChildren().add(runButton);


        // Setting up the graph
        NumberAxis xAxis = new NumberAxis(1, 100, 1);
        NumberAxis yAxis = new NumberAxis(0.00, 1.00, 0.1);
        xAxis.setLowerBound(1);
        xAxis.setLabel("Trials");
        yAxis.setLabel("Proportion");


        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);

        runButton.setOnAction((event) -> {
            // Getting the HashMap
            int trials = (int) trialsSlider.getValue();
            double probability = probabilitySlider.getValue();

            Simulation simulation = new Simulation(trials, probability);
            simulation.run();

            actualProbabilityLabel.setText("Simulated Probability is " + String.valueOf(simulation.getActualProbability()));
            summary.setText("In " + simulation.numberOfTrues() + " out of " + simulation.getTrials() + " simulated trials, COVID transmission happened.");


            HashMap<Integer, Double> data = simulation.getAverageTrials();
            simulation.averageTrial();

            // Setting the Chart
            xAxis.setUpperBound(data.size());
            xAxis.setTickUnit(data.size()/10);
            chart.getData().clear();
            chart.getData().add(chartData(data));
        });

        chart.setTitle("Chance of Getting Covid-19");
        layout.setCenter(chart);


        Scene front = new Scene(layout, 1080, 720);
        window.setTitle("SeoulHack 3: COVID-19 Project");
        window.setScene(front);
        window.show();


    }

    public XYChart.Series chartData(HashMap<Integer, Double> averageTrial) {
        XYChart.Series data = new XYChart.Series();
        data.setName("Trial Number vs Proportion");
        for(Integer trials: averageTrial.keySet()) {
            data.getData().add(new XYChart.Data(trials, averageTrial.get(trials)));
        }
        return data;
    }




    public static void main(String[] args) {
        launch(UI.class);
    }

}
