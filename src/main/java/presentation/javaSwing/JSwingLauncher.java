package presentation.javaSwing;

import logic.EvolutionaryModel;
import logic.Settings;
import presentation.Display;

public class JSwingLauncher {

    public static void main(String[] args) {
        //create a new display subclass, example below
        Settings settings = new Settings();
        Display display = new EvolutionViewer(settings);
        EvolutionaryModel evm = new EvolutionaryModel(settings, display);
    } // main
} // end GAMain
