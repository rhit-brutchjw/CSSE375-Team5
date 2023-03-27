public class Mutation {

    public Mutation() {

    } // Mutation

    public int[][] changeOnClick(int[][] chromoArray, int x, int y) {
        int iIndex = gridHelperI(y);
        int jIndex = gridHelperJ(x);
        if (chromoArray[iIndex][jIndex] == 1) {
            chromoArray[iIndex][jIndex] = 0;
        } else {
            chromoArray[iIndex][jIndex] = 1;
        }
        return chromoArray;
    } // changeOnClick

    public int gridHelperI(int y) {
        int iIndex = y / 40;
        return iIndex;

    } // gridHelperI

    public int gridHelperJ(int x) {
        int jIndex = x / 40;
        return jIndex;

    } // gridHelperJ

    /*
    private int[][] manualMutation(int mutationFactor, int[][] chromoArray) {
        Random random = new Random();
        ArrayList<Integer> keys = new ArrayList<Integer>();
        for (int i = 0; i < mutationFactor; i++) {
            int key = random.nextInt(chromoArray.length * 10);
            if (!keys.contains(key)) {
                keys.add(key);
            } else {
                i--;
            }
        }
        for (int j = 0; j < chromoArray.length; j++) {
            for (int k = 0; k < chromoArray[0].length; k++) {
                int chance = random.nextInt(chromoArray.length * 10);
                for (int key : keys) {
                    if (key == chance) {
                        if (chromoArray[j][k] == 1) {
                            chromoArray[j][k] = 0;
                        } else {
                            chromoArray[j][k] = 1;
                        }
                    }
                }
            }
        }
        return chromoArray;
    } // manualMutation
	*/

    /*
    public Chromosome autoMutation(Chromosome chromosome, int mutationFactor) {
        chromosome.geneArray = manualMutation(mutationFactor, chromosome.geneArray);
        return chromosome;
    } // autoMutation
    */
} // end Mutation
