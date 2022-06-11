package chess;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.beans.EventHandler;

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
        this.vBox.setSpacing(100);
        this.vBox.getChildren().add(game.getErrorMessageLabel());

        Button quitBtn = new Button("Quit");
        quitBtn.setOnAction((ActionEvent e) -> System.exit(0));
        quitBtn.setPrefWidth(100);
        quitBtn.setTranslateX((880-640)/2 * -1 + 100);
        quitBtn.setAlignment(Pos.CENTER);
        quitBtn.setFocusTraversable(false);
        this.vBox.getChildren().add(quitBtn);
    }

    public BorderPane getRoot(){
        return this.root;
    }
}
