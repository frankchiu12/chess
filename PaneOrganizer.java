package chess;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class PaneOrganizer {

    private final BorderPane root;

    public PaneOrganizer(){
        this.root = new BorderPane();
        Pane gamePane = new Pane();
        new Game(gamePane);

        this.root.setCenter(gamePane);
    }

    public BorderPane getRoot(){
        return this.root;
    }
}
