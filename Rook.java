package chess;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class Rook extends Queen {

    public Rook(Pane gamePane, Game game, int x, int y, Color color, String imagePath) {
        super(gamePane, game, x, y, color, imagePath);
    }

    @Override
    public void overallMovement(ArrayList<Coordinate<Integer, Integer>> coordinateArrayList) {
        this.horizontalMovement(coordinateArrayList);
    }
}
