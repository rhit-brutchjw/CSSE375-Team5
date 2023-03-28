

public class GAMain {

    public static void main(String[] args) {
        //create a new display subclass, example below
        Settings settings = new Settings();
        Display display = new EvolutionViewer(settings);
        new EvolutionaryModel(settings, display);
    } // main
} // end GAMain
