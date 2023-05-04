package data;

import logic.Chromosome;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class FileLoader {
	
	public FileLoader() { }

    public Chromosome load(File file) {
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
            int[][] geneArray = new int[rows][columns];
            while (sc.hasNextLine()) {
                for (int i = 0; i < geneArray.length; i++) {
                    String[] line = sc.nextLine().trim().split(" ");
                    for (int j = 0; j < line.length; j++) {
                        geneArray[i][j] = Integer.parseInt(line[j]);
                    }
                }
            }
            sc.close();
            return new Chromosome(geneArray, 0, -1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
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
}
