package BackEnd.Rocket;

public class GUILauncher {
    public void launchApp() {
        Thread thread = new Thread(() -> {
            // Launch the JavaFX application
            LApp.launch(LApp.class);
        });
        thread.start();
    }
}


