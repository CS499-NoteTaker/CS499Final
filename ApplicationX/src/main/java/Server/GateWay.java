package Server;

import Core.Controller;

public class GateWay {

    private static Controller controller;

    public static void setController(Controller c) {
        controller = c;
    }

    public static Controller getController() {
        return controller;
    }
}
