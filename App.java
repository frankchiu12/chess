package chess;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage){
        PaneOrganizer organizer = new PaneOrganizer();
        Scene scene = new Scene(organizer.getRoot(), 640, 640);
        stage.setScene(scene);
        stage.setTitle("Chess :)");
        stage.show();
    }

    public static void main(String[] argv){
        // launch is a method inherited from Application
        launch(argv);
    }
}