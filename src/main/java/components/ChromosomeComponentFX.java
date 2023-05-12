package components;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import logic.Chromosome;

public class ChromosomeComponentFX extends Region {
    private int[][] genes;

    private final int cellSize = 40;

    public ChromosomeComponentFX(int[][] genes) {
        this.genes = genes;
        setPrefSize(genes[0].length * cellSize, genes.length * cellSize);
    }

    public void updateGrid(int[][] newGrid) {
        this.genes = newGrid;
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        redraw();
    }

    private void redraw() {
        Canvas canvas = new Canvas(getWidth(), getHeight());
        GraphicsContext g = canvas.getGraphicsContext2D();

        for (int i = 0; i < genes.length; i++) {
            for (int j = 0; j < genes[i].length; j++) {
                int x = j * cellSize;
                int y = i * cellSize;
                drawOnFX(g, x, y, genes[i][j], i * genes[i].length + j);
            }
        }

        getChildren().clear();
        getChildren().add(canvas);
    }

    private void drawOnFX(GraphicsContext g, int x, int y, int value, int index) {
        g.setFill(getRectColorBasedOnCellValue(value));
        g.fillRect(x, y, cellSize, cellSize);
        g.setFill(getTextColorBasedOnCellValue(value));
        g.fillText(Integer.toString(index), x + cellSize / 2.0, y + cellSize / 2.0);
    }

    private Color getRectColorBasedOnCellValue(int value) {
        return (value == 0) ? Color.web("#00cc00") : Color.BLACK;
    }

    private Color getTextColorBasedOnCellValue(int value) {
        return (value == 0) ? Color.BLACK : Color.WHITE;
    }
}