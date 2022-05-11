package chess;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Bishop extends Queen {

    private Pane gamePane;
    private Game game;
    private Color color;
    private ImageView imageView;

    public Bishop(Pane gamePane, Game game, int x, int y, Color color, String imagePath) {
        super(gamePane, game, x, y, color, imagePath);
        this.gamePane = gamePane;
        this.game = game;
        this.color = color;
    }

    @Override
    public void overallMovement(ArrayList<Pair<Integer, Integer>> pairArrayList) {
        this.diagonalMovement(pairArrayList);
    }

}
