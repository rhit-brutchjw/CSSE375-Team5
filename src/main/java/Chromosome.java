

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.*;

public class Chromosome implements Comparable<Chromosome> {
    private int[][] geneArray;
    private int geneSeed;
    private int rank = -1;

    public Chromosome() { }
    
    public Chromosome(int[][] geneArray, int seed, int rank) {
        this.geneArray = geneArray;
        this.geneSeed = seed;
        this.rank = rank;
    }

    public int getLength() {
    	return (geneArray.length == 0) ? 0 : geneArray.length * geneArray[0].length;
    }

    @Override
    public Chromosome clone() {
        int[][] clonedGenes = new int[geneArray.length][];
        for (int i = 0; i < geneArray.length; i++) {
            clonedGenes[i] = geneArray[i].clone();
        }
        return new Chromosome(clonedGenes, geneSeed, rank);
    } // clone

    @Override
    public int compareTo(Chromosome otherChromosome) {
        return otherChromosome.rank - this.rank;
    } // compareTo

    public Chromosome mutation(int mutationFactor, Random random) {
        int geneLength = geneArray.length;
        int geneWidth = geneArray[0].length;

        Set<Integer> keys = new HashSet<>();
        while (keys.size() < mutationFactor) {
            keys.add(random.nextInt(geneLength * geneWidth));
        }

        for (int key : keys) {
            int row = key / geneWidth;
            int col = key % geneWidth;
            geneArray[row][col] ^= 1;
        }

        // Returns Chromosome for ease of use, but this is not needed to be used.
        return this;
    }

    public void drawOn(Graphics2D g, int x, int y, int value, int index) {
        g = (Graphics2D) g.create();
        g.setColor(getRectColorBasedOnCellValue(value));
        g.fillRect(x, y, 40, 40);
        g.setColor(getTextColorBasedOnCellValue(value));
        g.drawString(Integer.toString(index), x + 20, y + 20);
    } // drawOn

    public void drawSmallOn(Graphics2D g2, int x, int y, int value) {
        g2 = (Graphics2D) g2.create();
        g2.setColor(getRectColorBasedOnCellValue(value));
        g2.fillRect(x, y, 4, 4);
    } // drawSmallOn

    public Color getRectColorBasedOnCellValue(int value) {
        return value == 0 ? Color.BLACK : Color.GREEN;
    }
    public Color getTextColorBasedOnCellValue(int value) {
        return value == 0 ? Color.WHITE : Color.BLACK;
    }

    public int getRank() { return this.rank; }
    public void setRank(int rank) { this.rank = rank; }
    public int[][] getGenes() { return this.geneArray; }
    public void updateGeneValue(int x, int y, int geneValue) { geneArray[x][y] = geneValue; }
} // end Chromosome
