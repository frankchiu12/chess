package chess;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Pawn extends ChessPiece {

    private final Game game;
    private final Color color;
    private final int startingPosition;
    private final int direction;

    public Pawn(Pane gamePane, Game game, int x, int y, Color color, String imagePath){
        super(gamePane, game, x, y, color, imagePath);
        this.game = game;
        this.color = color;
        this.startingPosition = y;
        this.direction = this.color == Color.WHITE ? -1 : 1;
    }

    @Override
    public void overallMovement(ArrayList<Coordinate<Integer, Integer>> coordinateArrayList) {
        boolean isBeforeEndOfBoard = this.color == Color.WHITE ? this.getRow() > 0 : this.getRow() < 7;

        if (this.getRow() == this.startingPosition && this.game.checkCanMove(this.getRow(), this.getColumn(), this.direction, 0) && this.game.checkCanMove(this.getRow(), this.getColumn(), this.direction * 2, 0)){
            coordinateArrayList.add(new Coordinate<>(this.getRow() + this.direction * 2, this.getColumn()));
        }

        // TODO: what is this boolean
        if (isBeforeEndOfBoard && this.game.checkCanMove(this.getRow(), this.getColumn(), this.direction, 0)){
            coordinateArrayList.add(new Coordinate<>(this.getRow() + this.direction, this.getColumn()));
        }

        // eat right diagonal
        if (this.getRow() > 0 && this.getColumn() < 7 && this.game.checkCanEat(this.getRow(), this.getColumn(), this.direction, 1, this.color)){
            coordinateArrayList.add(new Coordinate<>(this.getRow() + this.direction, this.getColumn() + 1));
        }

        // eat left diagonal
        if (this.getRow() > 0 && this.getColumn() > 0 && this.game.checkCanEat(this.getRow(), this.getColumn(), this.direction, -1, this.color)){
            coordinateArrayList.add(new Coordinate<>(this.getRow() + this.direction, this.getColumn() - 1));
        }

        for (int i = 0; i < coordinateArrayList.size(); i++){
            System.out.println(coordinateArrayList.get(i).getR());
            System.out.println(coordinateArrayList.get(i).getC());
        }
    }
}
