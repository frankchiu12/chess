package chess;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PaneOrganizer {

    private final BorderPane root;

    public PaneOrganizer(){
        this.root = new BorderPane();
        Pane gamePane = new Pane();
        Game game = new Game(gamePane);
        VBox vBox = new VBox();

        this.root.setCenter(gamePane);
        this.root.setRight(vBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(25);
        vBox.getChildren().add(game.getErrorMessageLabel());
        vBox.getChildren().add(game.getTextField());
        vBox.getChildren().add(game.getSubmitButton());

        Button quitBtn = new Button("Quit");
        quitBtn.setOnAction((ActionEvent e) -> System.exit(0));
        quitBtn.setPrefWidth(100);
        quitBtn.setTranslateX((880-640)/2 * -1 + 100);
        quitBtn.setAlignment(Pos.CENTER);
        quitBtn.setFocusTraversable(false);
        vBox.getChildren().add(quitBtn);
    }

    public BorderPane getRoot(){
        return this.root;
    }
}
