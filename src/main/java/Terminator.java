public class Terminator {

    public Terminator() {
    } // Terminator

    public boolean mostFit(Population population) {
    	if (population.maxFitAchieved()) {
    		return true;
    	}
        return false;
    } // mostFit

    public boolean genCount(int curGen, int maxGen) {
        return curGen == maxGen;
    } // genCount
} // end Terminator
