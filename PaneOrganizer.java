package chess;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PaneOrganizer {

    private final BorderPane root;
    private VBox vBox;

    public PaneOrganizer(){
        this.root = new BorderPane();
        this.vBox = new VBox();
        Pane gamePane = new Pane();
        Game game = new Game(gamePane);

        this.root.setCenter(gamePane);
        this.root.setRight(this.vBox);
        this.vBox.setAlignment(Pos.CENTER);
        this.vBox.getChildren().add(game.getErrorMessageLabel());
    }

    public BorderPane getRoot(){
        return this.root;
    }
}
