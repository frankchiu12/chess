package chess.codeFinished;

import chess.PaneOrganizer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        PaneOrganizer organizer = new PaneOrganizer();
        Scene scene = new Scene(organizer.getRoot(), 880, 640);
        stage.setScene(scene);
        stage.setTitle("Chess :)");
        stage.show();
    }

    public static void main(String[] argv){
        launch(argv);
    }
}
