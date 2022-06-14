package chess.codeFinished;

import chess.ChessPiece;
import chess.Game;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class Pawn extends ChessPiece {

    private final Game game;
    private final Color color;

    public Pawn(Pane gamePane, Game game, int x, int y, Color color, String imagePath) {
        super(gamePane, game, x, y, color, imagePath);
        this.game = game;
        this.color = color;
    }

    @Override
    public void overallMovement(ArrayList<Coordinate<Integer, Integer>> coordinateArrayList) {

        // pawn move two spaces
        if (this.getRow() == 6 && this.color == this.game.getPlayerColor() && this.game.checkCanMove(this.getRow(), this.getColumn(), -1, 0) && this.game.checkCanMove(this.getRow(), this.getColumn(),  -2, 0)) {
            coordinateArrayList.add(new Coordinate<>(this.getRow() - 2, this.getColumn()));
        }
        if (this.getRow() == 1 && this.color != this.game.getPlayerColor() && this.game.checkCanMove(this.getRow(), this.getColumn(), 1, 0) && this.game.checkCanMove(this.getRow(), this.getColumn(),  2, 0)) {
            coordinateArrayList.add(new Coordinate<>(this.getRow() + 2, this.getColumn()));
        }

        if (this.color == this.game.getPlayerColor()) {
            // move forward one space
            if (this.game.checkCanMove(this.getRow(), this.getColumn(), - 1, 0)) {
                coordinateArrayList.add(new Coordinate<>(this.getRow() - 1, this.getColumn()));
            }
            // eat right diagonal
            if (this.getRow() > 0 && this.getColumn() < 7 && this.game.checkCanEat(this.getRow(), this.getColumn(), -1, 1, this.color)) {
                coordinateArrayList.add(new Coordinate<>(this.getRow() - 1, this.getColumn() + 1));
            }
            // eat left diagonal
            if (this.getRow() > 0 && this.getColumn() > 0 && this.game.checkCanEat(this.getRow(), this.getColumn(), -1, -1, this.color)) {
                coordinateArrayList.add(new Coordinate<>(this.getRow() - 1, this.getColumn() - 1));
            }
        } else {
            if (this.game.checkCanMove(this.getRow(), this.getColumn(), 1, 0)) {
                coordinateArrayList.add(new Coordinate<>(this.getRow() + 1, this.getColumn()));
            }

            // eat right diagonal
            if (this.getRow() > 0 && this.getColumn() < 7 && this.game.checkCanEat(this.getRow(), this.getColumn(), 1, 1, this.color)) {
                coordinateArrayList.add(new Coordinate<>(this.getRow() + 1, this.getColumn() + 1));
            }

            // eat left diagonal
            if (this.getRow() > 0 && this.getColumn() > 0 && this.game.checkCanEat(this.getRow(), this.getColumn(), 1, -1, this.color)) {
                coordinateArrayList.add(new Coordinate<>(this.getRow() + 1, this.getColumn() - 1));
            }
        }
    }
}
