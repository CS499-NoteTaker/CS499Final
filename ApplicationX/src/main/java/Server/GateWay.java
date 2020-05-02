package Server;

import Core.CitationController;

public class GateWay {

    private static CitationController controller;

    public static void setController(CitationController c) {
        controller = c;
    }

    public static CitationController getController() {
        return controller;
    }
}
