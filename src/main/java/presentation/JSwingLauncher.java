package presentation;

import logic.EvolutionaryModel;
import logic.Settings;

public class JSwingLauncher {

    public static void main(String[] args) {
        //create a new display subclass, example below
        Settings settings = new Settings();
        Display display = new EvolutionViewer(settings);
        EvolutionaryModel evm = new EvolutionaryModel(settings, display);
    } // main
} // end GAMain
