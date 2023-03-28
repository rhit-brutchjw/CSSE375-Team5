

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Chromosome implements Comparable<Chromosome> {
    private int[][] geneArray;
    private int geneSeed;
    private int rank = -1;

    public Chromosome() {
    } // Chromosome

    public Chromosome(int[][] geneArray, int seed, int rank) {
        this.geneArray = geneArray;
        this.geneSeed = seed;
        this.rank = rank;
    } // Chromosome

    public int getLength() {
    	if (geneArray.length == 0) {
    		return 0;
    	} else {
    		return geneArray.length * geneArray[0].length;
    	}
    } // getLength

    public Chromosome clone() {
        int[][] clonedGenes = new int[geneArray.length][geneArray[0].length];
        for (int i = 0; i < geneArray.length; i++) {
        	clonedGenes[i] = this.geneArray[i].clone();
        }
        return new Chromosome(clonedGenes, this.geneSeed, this.rank);
    } // clone

    @Override
    public int compareTo(Chromosome otherChromosome) {
        return otherChromosome.rank - this.rank;
    } // compareTo

    public Chromosome mutation(int mutationFactor, Random random) {
        ArrayList<Integer> keys = new ArrayList<Integer>();
        for (int i = 0; i < mutationFactor; i++) {
            int key = random.nextInt(geneArray.length * 10);
            if (!keys.contains(key)) {
                keys.add(key);
                continue;
            }
            i --;
        }
        for (int j = 0; j < geneArray.length; j++) {
            for (int k = 0; k < geneArray[0].length; k++) {
                int chance = random.nextInt(geneArray.length * 10);
                for (int key : keys) {
                    if (key == chance) {
                        if (geneArray[j][k] == 1) {
                        	geneArray[j][k] = 0;
                        } else {
                        	geneArray[j][k] = 1;
                        }
                    }
                }
            }
        }
        
        // Returns Chromosome for ease of use, but this is not needed to be used.
        return this;
    }

    public int[][] load(File file) {
        Scanner sc;
        Scanner scan;
        try {
            sc = new Scanner(file);
            scan = new Scanner(file);
            int rows = 0;
            int columns = 10;
            int count = 0;
            while (scan.hasNextLine()) {
                count++;
                scan.nextLine();
            }
            rows = count;
            geneArray = new int[rows][columns];
            while (sc.hasNextLine()) {
                for (int i = 0; i < geneArray.length; i++) {
                    String[] line = sc.nextLine().trim().split(" ");
                    for (int j = 0; j < line.length; j++) {
                        geneArray[i][j] = Integer.parseInt(line[j]);
                    }
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return geneArray;
    } // load

    public void save(File file, int[][] data) {
        FileWriter fos;
        try {
            fos = new FileWriter(file);
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    String text = Integer.toString(data[i][j]);
                    fos.write(text);
                    fos.write(" ");
                }
                if (i < 9) {
                    fos.write("\n");
                }
            }
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // save

    public void drawOn(Graphics2D g, int x, int y, int value, int index) {

        g = (Graphics2D) g.create();
        if (value == 0) {
            g.setColor(Color.BLACK);
        } else if (value == 1) {
            g.setColor(Color.GREEN);
        }
        g.fillRect(x, y, 40, 40);

        if (value == 0) {
            g.setColor(Color.WHITE);
        } else if (value == 1) {
            g.setColor(Color.BLACK);
        }
        g.drawString(Integer.toString(index), x + 20, y + 20);
    } // drawOn

    public void drawSmallOn(Graphics2D g2, int x, int y, int value) {
        g2 = (Graphics2D) g2.create();
        if (value == 0) {
            g2.setColor(Color.BLACK);
        } else if (value == 1) {
            g2.setColor(Color.GREEN);
        }
        g2.fillRect(x, y, 4, 4);

        if (value == 0) {
            g2.setColor(Color.WHITE);
        } else if (value == 1) {
            g2.setColor(Color.BLACK);
        }
    } // drawSmallOn
    
    public int getRank() { return this.rank; }
    public void setRank(int rank) { this.rank = rank; }
    public int[][] getGenes() { return this.geneArray; }
    public void updateGeneValue(int x, int y, int geneValue) { geneArray[x][y] = geneValue; }
} // end Chromosome
