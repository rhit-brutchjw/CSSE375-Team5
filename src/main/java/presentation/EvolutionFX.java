package presentation;

import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.*;
import logic.Chromosome;
import logic.EvolutionaryModel;
import logic.Population;
import logic.Settings;
import operations.RankSelection;
import operations.RouletteSelection;
import operations.TruncationSelection;

import java.io.File;


public class EvolutionFX extends Application implements Display {
    private static final int FRAME_WIDTH = 1400;
    private static final int FRAME_HEIGHT = 600;
    private FileChooser chooser = new FileChooser();
    private Stage evolutionGUI = new Stage();
    private Stage bestGUI = new Stage();
    private Stage allPopGUI = new Stage();

    private BorderPane root = new BorderPane();
    private VBox wordPanel = new VBox();
    private VBox legends = new VBox();
    private HBox options = new HBox();
    private Button start = new Button("Start");
    private CheckBox crossover = new CheckBox();
    private Label title = new Label("Fitness over Generations");
    private Label mutationText = new Label("Mutation Rate (N/pop)");
    private Label populationSizeText = new Label("Population Size");
    private Label generationsText = new Label("Generations");
    private Label genomeText = new Label("Genome Length");
    private Label selectionText = new Label("Selection");
    private Label fitnessText = new Label("Fitness Function");
    private Label elitismText = new Label("Elitism");
    private Label crossoverText = new Label("Crossover?");
    private Label highestFitnessRating = new Label("Highest Fitness Rating: ");
    private TextField mutationRate = new TextField();
    private TextField populationSize = new TextField();
    private TextField generations = new TextField();
    private TextField genomeLength = new TextField();
    private TextField elitism = new TextField();

    private ComboBox<String> selection = new ComboBox<>();
    private ComboBox<String> fitness = new ComboBox<>();
    private boolean isFinished = true;
    private Settings settings;
    private GraphicsContext gc;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        settings = new Settings();

        selection.getItems().addAll("Truncation", "Roulette", "Ranked");
        fitness.getItems().addAll("Simple", "Matching", "Consecutive");
        options.setSpacing(10);
        options.getChildren().addAll(start, new VBox(10, mutationText, mutationRate),
                new VBox(10, elitismText, elitism),
                new VBox(10, selectionText, selection),
                new VBox(10, fitnessText, fitness),
                new VBox(10, populationSizeText, populationSize),
                new VBox(10, genomeText, genomeLength),
                new VBox(10, crossoverText, crossover),
                new VBox(10, generationsText, generations));

        Canvas canvas = new Canvas(FRAME_WIDTH, FRAME_HEIGHT-100);
        gc = canvas.getGraphicsContext2D();
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(isFinished) {
                    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    isFinished = false;
                    startRun();
                }
            }
        });
        mutationRate.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                settings.setMutationFactor(Integer.parseInt(newValue));
            } catch (Exception e) {
                //do nothing
            }
        });
        elitism.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                settings.setElitism(Integer.parseInt(newValue));
            } catch (Exception e) {
                //do nothing
            }
        });
        selection.setOnAction(new SelectionHandler());
        fitness.setOnAction(new FitnessHandler());
        populationSize.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                settings.setPopulationSize(Integer.parseInt(newValue));
            } catch (Exception e) {
                //do nothing
            }
        });
        genomeLength.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                settings.setGenomeLength(Integer.parseInt(newValue));
            } catch (Exception e) {
                //do nothing
            }
        });
        crossover.setOnAction(new CrossoverHandler());
        generations.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                settings.setMaxGenerations(Integer.parseInt(newValue));
            } catch (Exception e) {
                //do nothing
            }
        });
        wordPanel.getChildren().addAll(title);

        root.setPadding(new Insets(10, 10, 10, 10));
        root.setCenter(canvas);
        root.setTop(wordPanel);
        root.setBottom(options);
        Scene scene = new Scene(root, FRAME_WIDTH, FRAME_HEIGHT);
        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                try {
                    stop();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                System.exit(0);
            }
        });
        stage.show();

    }



    private void drawShapes(GraphicsContext gc, Population population) {
        gc.setStroke(Color.BLACK);
        gc.strokeRect(100, 50, 1000, 400);
        gc.setFill(Color.GREEN);
        gc.fillRect(980, 320, 10, 10);
        gc.setFill(Color.BLACK);
        gc.fillText("Most Fit", 1000, 330);
        gc.setFill(Color.RED);
        gc.fillRect(980, 345, 10, 10);
        gc.setFill(Color.BLACK);
        gc.fillText("Least Fit", 1000, 355);
        gc.setFill(Color.YELLOW);
        gc.fillRect(980, 370, 10, 10);
        gc.setFill(Color.BLACK);
        gc.fillText("Average Fit", 1000, 380);
        gc.setFill(Color.MAGENTA);
        gc.fillRect(980, 395, 10, 10);
        gc.setFill(Color.BLACK);
        gc.fillText("Hamming Distance", 1000, 405);
        for (int i = 0; i < population.getGenomeLength() / 10 + 1; i++) {
            gc.setFill(Color.BLACK);
            gc.fillText(Integer.toString(i * 10), 80, 450 - (400 / (population.getGenomeLength() / 10)) * i);
        }
        int helper = 0;
        for (int i = 0; i < population.getCurrentGeneration() + 1; i++) {
            int a = population.getMaxGenerations() / 10;
            if (i % a == 0) {
                helper++;
                gc.setFill(Color.BLACK);
                gc.fillText(Integer.toString(i), (100 * helper), 470);
            }
        }
        gc.setLineWidth(3);
        for (int i = 0; i < population.getCurrentGeneration() - 1; i++) {
            gc.setStroke(Color.GREEN);
            gc.strokeLine(100 + (i) * (1000.0 / population.getMaxGenerations()),
                    450 - population.getBestFit().get(i) * (400.0 / (population.getGenomeLength())), 100 + (i + 1) * (1000.0 / population.getMaxGenerations()),
                    450 - population.getBestFit().get(i + 1) * (400.0 / (population.getGenomeLength())));
            gc.setStroke(Color.RED);
            gc.strokeLine(100 + (i) * (1000.0 / population.getMaxGenerations()),
                    450 - population.getWorstFit().get(i) * (400.0 / (population.getGenomeLength())), 100 + (i + 1) * (1000.0 / population.getMaxGenerations()),
                    450 - population.getWorstFit().get(i + 1) * (400.0 / (population.getGenomeLength())));
            gc.setStroke(Color.YELLOW);
            gc.strokeLine(100 + (i) * (1000.0 / population.getMaxGenerations()),
                    450 - population.getAvgFit().get(i) * (400.0 / (population.getGenomeLength())), 100 + (i + 1) * (1000.0 / population.getMaxGenerations()),
                    450 - population.getAvgFit().get(i + 1) * (400.0 / (population.getGenomeLength())));
            gc.setStroke(Color.MAGENTA);
            gc.strokeLine(100 + (i) * (1000.0 / population.getMaxGenerations()),
                    450 - population.getHammDist().get(i) * (400.0 / (population.getGenomeLength())), 100 + (i + 1) * (1000.0 / population.getMaxGenerations()),
                    450 - population.getHammDist().get(i + 1) * (400.0 / (population.getGenomeLength())));
        }

    }

    private class SelectionHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            if(selection.getSelectionModel().getSelectedIndex() == 0) {
                settings.setSelectionMethod(new TruncationSelection());
            }else if(selection.getSelectionModel().getSelectedIndex() == 1) {
                settings.setSelectionMethod(new RouletteSelection());
            } else if(selection.getSelectionModel().getSelectedIndex() == 2) {
                settings.setSelectionMethod(new RankSelection());
            }
        }
    }

    private class FitnessHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            if (fitness.getSelectionModel().getSelectedIndex() == 1) {
                chooser.setInitialDirectory(new File("genotypes"));
                File f = chooser.showOpenDialog(null);
                if(f != null) {
                    settings.setTarget(f);
                }
            }
            settings.setFitnessMethod(fitness.getSelectionModel().getSelectedIndex());
        }
    }

    private class CrossoverHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            settings.setCrossover(crossover.isSelected());
        }
    }

    @Override
    public void updateMostFit(Chromosome chromosome) {

    }

    @Override
    public void updatePopulation(Population population) {
        drawShapes(gc, population);
    }

    @Override
    public void markFinished() {
        this.isFinished = true;
    }

    @Override
    public void startRun() {
        EvolutionaryModel evm = new EvolutionaryModel(settings, this);
        EvolutionaryModel.t.start();
    }
}
