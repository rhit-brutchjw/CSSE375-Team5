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


import java.awt.*;
import java.io.File;
import java.util.ArrayList;


public class EvolutionFX extends Application implements Display {
    private static final int FRAME_WIDTH = 1500;
    private static final int FRAME_HEIGHT = 800;
    private static final int OTHER_VIEWS_WIDTH_OFFSET = 1150;
    private static final int BEST_FIT_Y_OFFSET = 25;
    private static final int ALL_POP_Y_OFFSET = 375;
    private FileChooser chooser = new FileChooser();
    private Stage evolutionGUI = new Stage();


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

        selection.getItems().addAll("Truncation", "Roulette", "Ranked", "Diversity", "Worst");
        fitness.getItems().addAll("Simple", "Matching", "Consecutive", "Alternating");
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
                    gc.setStroke(Color.BLACK);
                    gc.fillText("Best Fit View", 1175, 20);
                    gc.fillText("All Pop View", 1175, 370);
                    System.out.println("Hello World!");
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
    public Color getRectColorBasedOnCellValue(int value) {
        return value == 0 ? Color.BLACK : Color.GREEN;
    }
    public Color getTextColorBasedOnCellValue(int value) {
        return value == 0 ? Color.WHITE : Color.BLACK;
    }



    private void drawRects(int x, int y, int value, int index) {
        gc.setFill(getRectColorBasedOnCellValue(value));
        gc.fillRect(x, y,33, 33);
        gc.setFill(getTextColorBasedOnCellValue(value));
        gc.fillText(Integer.toString(index), x + 10, y +15);
    }

    private void drawBestFit(Chromosome chromosome) {
        gc.setStroke(Color.BLACK);
        double height = (chromosome.getLength() / 10) * 33;
        gc.strokeRect(1150, 25, 330, height);
        int[][] bestGenes = chromosome.getGenes();

            for (int i = 0; i < bestGenes.length; i++) {
                int y = BEST_FIT_Y_OFFSET;
                y += i * 33;
                for (int j = 0; j < bestGenes[i].length; j++) {
                    drawRects(OTHER_VIEWS_WIDTH_OFFSET + j * 33,  y, bestGenes[i][j], i * 10 + j);
                }
            }
    }

    private void drawAllPop(GraphicsContext gcAllPop, ArrayList<Chromosome> population) {
        gc.setStroke(Color.BLACK);
        gc.strokeRect(1150, 375, 330, 325);
        int x = 0;
        int y = 0;

        for (int i = 0; i < population.size() / 10; i++) {
            for (int l = 0; l < 10; l++) {
                Chromosome curChrome = population.get(i * 10 + l);
                int[][] curChromeGenes = curChrome.getGenes();
                int newRow = ALL_POP_Y_OFFSET + (i) * 3 * (curChromeGenes.length);
                int newCol = OTHER_VIEWS_WIDTH_OFFSET + l * 33;
                x = newCol;
                y = newRow;
                for (int k = 0; k < curChromeGenes.length; k++) {
                    for (int j = 0; j < curChromeGenes[0].length; j++) {
                        drawSmallOn(gcAllPop, x, y, curChromeGenes[k][j]);
                        x += 2;
                    }
                    x -= 20;
                    y += 2;
                }
            }
        }
    }

    private void drawSmallOn(GraphicsContext gcAllPop, int x, int y, int value) {
        gcAllPop.setFill(getRectColorBasedOnCellValue(value));
        gcAllPop.fillRect(x, y, 3, 3);
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
            settings.setSelectionMethod(selection.getSelectionModel().getSelectedIndex());
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
                }else {
                    System.out.println("No target selected for matching...Using default");
                    settings.setTarget(new File("genotypes/size_100.txt"));
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
        if(chromosome.getLength() <= 200) {
            drawBestFit(chromosome);
        }

    }

    @Override
    public void updatePopulation(Population population) {
        drawShapes(gc, population);
        if(population.getSize() >= 10 && population.getSize() <= 100 && population.getGenomeLength() <= 100) {
            drawAllPop(gc, population.getPopulation());
        }

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
