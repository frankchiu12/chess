package chess.codeFinished;

import chess.Game;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

        ImageView imageView = new ImageView();
        Image image = new Image("chess/chessPiecePNG/chessBackground.png");
        imageView.setImage(image);
        imageView.setOpacity(0.3);

        this.root.getChildren().add(imageView);
        this.root.setCenter(gamePane);
        this.root.setRight(vBox);

        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(25);

        Button quitButton = new Button("Quit");
        quitButton.setOnAction((ActionEvent e) -> System.exit(0));
        quitButton.setPrefWidth(100);
        quitButton.setTranslateX(-20);
        quitButton.setAlignment(Pos.CENTER);
        quitButton.setFocusTraversable(false);

        vBox.getChildren().addAll(game.getWhiteTimerLabel(), game.getBlackTimerLabel(), game.getErrorMessageLabel(), game.getTextField(), game.getStartButton(), game.getSubmitButton(), game.getRestartButton(), quitButton);
    }

    public BorderPane getRoot(){
        return this.root;
    }
}
