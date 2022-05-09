package chess;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class PaneOrganizer {

    private BorderPane root;
    private Pane gamePane;

    public PaneOrganizer(){
        this.root = new BorderPane();
        this.gamePane = new Pane();
        Game game = new Game(this.gamePane);

        this.root.setCenter(gamePane);
    }

    public BorderPane getRoot(){
        return this.root;
    }
}
