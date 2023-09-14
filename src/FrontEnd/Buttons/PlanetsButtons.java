package FrontEnd.Buttons;

import javafx.scene.control.Button;

public class PlanetsButtons {

    Button[] allButtons;
    String[] names = { "Sun", "Mercury", "Venus", "Earth", "Mars", "Neptun", "Uranus", "Saturn", "Jupiter",
            "SpaceShip" };

    PlanetsButtons() {
        allButtons = new Button[10];
        for (int i = 0; i < allButtons.length; i++) {
            allButtons[i] = new Button(names[i]);
        }
    }

}
