package chess;

import chess.finishedCode.Coordinate;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class King extends ChessPiece {

    private final Game game;

    public King(Pane gamePane, Game game, int x, int y, Color color, String imagePath) {
        super(gamePane, game, x, y, color, imagePath);
        this.game = game;
    }

    @Override
    public void overallMovement(ArrayList<Coordinate<Integer, Integer>> coordinateArrayList) {
        this.diagonalMovement(coordinateArrayList);
        this.horizontalMovement(coordinateArrayList);
    }

    public void diagonalMovement(ArrayList<Coordinate<Integer, Integer>> coordinateArrayList){

        int row = this.getRow();
        int column = this.getColumn();

        // top right
        if (this.game.checkCanMove(row, column, -1, 1) || this.game.checkCanEat(row, column, -1, 1, this.getColor())){
            coordinateArrayList.add(new Coordinate<>(row - 1, column + 1));
        }

        // top left
        if (this.game.checkCanMove(row, column, -1, -1) || this.game.checkCanEat(row, column, -1, -1, this.getColor())){
            coordinateArrayList.add(new Coordinate<>(row - 1, column - 1));
        }

        // bottom right
        if (this.game.checkCanMove(row, column, 1, 1) || this.game.checkCanEat(row, column, 1, 1, this.getColor())){
            coordinateArrayList.add(new Coordinate<>(row + 1, column + 1));
        }

        // bottom left
        if (this.game.checkCanMove(row, column, 1, -1) || this.game.checkCanEat(row, column, 1, -1, this.getColor())){
            coordinateArrayList.add(new Coordinate<>(row + 1, column - 1));
        }
    }

    public void horizontalMovement(ArrayList<Coordinate<Integer, Integer>> coordinateArrayList){

        int row = this.getRow();
        int column = this.getColumn();

        // top
        if (this.game.checkCanMove(row, column, -1, 0) || this.game.checkCanEat(row, column, -1, 0, this.getColor())){
            coordinateArrayList.add(new Coordinate<>(row - 1, column));
        }

        // bottom
        if (this.game.checkCanMove(row, column, 1, 0) || this.game.checkCanEat(row, column, 1, 0, this.getColor())){
            coordinateArrayList.add(new Coordinate<>(row + 1, column));
        }

        // right
        if (this.game.checkCanMove(row, column, 0, 1) || this.game.checkCanEat(row, column, 0, 1, this.getColor())){
            coordinateArrayList.add(new Coordinate<>(row, column + 1));
        }

        // left
        if (this.game.checkCanMove(row, column, 0, -1) || this.game.checkCanEat(row, column, 0, -1, this.getColor())){
            coordinateArrayList.add(new Coordinate<>(row, column - 1));
        }

        if (this.game.getCanLeftCastle() && !this.getHasMoved()){
            coordinateArrayList.add(new Coordinate<>(row, column - 2));
        }

        if (this.game.getCanRightCastle() && !this.getHasMoved()){
            coordinateArrayList.add(new Coordinate<>(row, column + 2));
        }
    }
}
