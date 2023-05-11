package presentation;

import components.ChromosomeComponentFX;
import data.FileLoader;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.Chromosome;

import java.io.File;
import java.util.Random;

public class ChromosomeFX extends Application {
    private static final int FRAME_WIDTH = 418;
    private static final int FRAME_HEIGHT = 500;
    private static final String DEFAULT_FILE_PATH = "genotypes/size_100.txt";
    private static final String DEFAULT_FOLDER_PATH = "genotypes";
    private FileChooser chooser;
    private Stage stage;
    private HBox chromosomeHBox;
    private Button mutateButton;
    private Button closeButton;
    private Button saveButton;
    private Button loadButton;
    private TextField mutationFactor;

    private FileLoader fileLoader;
    private File currentFile;

    private Label fileTitle;

    private Chromosome chromosome;
    private int[][] genes;
    private ChromosomeComponentFX chromosomeComponent;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        chromosomeHBox = new HBox();
        mutateButton = new Button("Mutate");
        closeButton = new Button("Close");
        saveButton = new Button("Save");
        loadButton = new Button("Load");

        mutationFactor = new TextField();
        fileTitle = new Label();
        fileTitle.setAlignment(Pos.CENTER);

        chooser = new FileChooser();
        chooser.setInitialDirectory(new File("genotypes"));
        fileLoader = new FileLoader();
        currentFile = new File(DEFAULT_FILE_PATH);
        chromosome = fileLoader.load(currentFile);
        genes = chromosome.getGenes();
        chromosomeComponent = new ChromosomeComponentFX(genes);

        setUpFrame();
        setUpChromosomePanel();

        chromosomeComponent.setOnMouseClicked(new MouseMutateListener());

        setUpActionHandlers();

        update(genes);
        primaryStage.show();
    } // ChromosomeViewer

    private void setUpFrame() {
        stage.setWidth(FRAME_WIDTH);
        stage.setHeight(FRAME_HEIGHT);
        stage.setTitle("Chromosome Creator");
        BorderPane root = new BorderPane();
        root.setCenter(chromosomeComponent);
        root.setBottom(chromosomeHBox);
        root.setTop(fileTitle);
        stage.setScene(new Scene(root));
    }

    private void setUpChromosomePanel() {
        mutationFactor.setPrefSize(100, 20);
        chromosomeHBox.getChildren().addAll(closeButton, saveButton, loadButton, mutateButton, mutationFactor);
        chromosomeHBox.setSpacing(10);
        chromosomeHBox.setPadding(new Insets(10));
        chromosomeHBox.setAlignment(Pos.CENTER);
    }

    private void setUpActionHandlers() {
        closeButton.setOnAction(event -> stage.close());

        loadButton.setOnAction(event -> {
            File selectedFile = chooser.showOpenDialog(stage);
            if (selectedFile != null) {
                currentFile = selectedFile;
                chromosome = fileLoader.load(currentFile);
                genes = chromosome.getGenes();
                fileTitle.setText(currentFile.getName());
                update(genes);
            }
        });

        saveButton.setOnAction(event -> {
            File selectedFile = chooser.showSaveDialog(stage);
            if (selectedFile != null) {
                fileLoader.save(selectedFile, chromosome.getGenes());
            }
        });

        mutateButton.setOnAction(event -> {
            try {
                int intMutationFactor = Integer.parseInt(mutationFactor.getText());
                genes = chromosome.mutation(intMutationFactor, new Random()).getGenes();
                fileTitle.setText(currentFile.getName() + " (mutated)");
                update(genes);
            } catch (NumberFormatException e) {
                System.out.println("Failed! Please enter an integer.");
            }
        });
    }

    public void update(int[][] chromosome) {
        fileTitle.setText(currentFile.getName());
        chromosomeComponent.updateGrid(chromosome);
    }

    private class MouseMutateListener implements EventHandler<MouseEvent> {
        private final int cellSize = 40;
        public void handle(MouseEvent event) {
            double x = event.getX();
            double y = event.getY();
            if (x < 400 && x > 0 && y < genes.length * cellSize && y > 0) {
                int iIndex = (int) (y / cellSize);
                int jIndex = (int) (x / cellSize);
                genes[iIndex][jIndex] ^= 1;
                fileTitle.setText(currentFile.getName() + " (mutated)");
                update(genes);
            }
        }
    }
} // end ChromosomeViewer
