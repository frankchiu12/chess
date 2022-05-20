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
    public void overallMovement(ArrayList<Pair<Integer, Integer>> pairArrayList) {
        boolean isBeforeEndOfBoard = this.color == Color.WHITE ? this.getRow() > 0 : this.getRow() < 7;

        if (this.getRow() == this.startingPosition && this.game.checkCanMove(this.getRow(), this.getColumn(), this.direction, 0) && this.game.checkCanMove(this.getRow(), this.getColumn(), this.direction * 2, 0)){
            pairArrayList.add(new Pair<>(this.getRow() + this.direction * 2, this.getColumn()));
        }

        // TODO: what is this boolean
        if (isBeforeEndOfBoard && this.game.checkCanMove(this.getRow(), this.getColumn(), this.direction, 0)){
            pairArrayList.add(new Pair<>(this.getRow() + this.direction, this.getColumn()));
        }

        // eat right diagonal
        if (this.getRow() > 0 && this.getColumn() < 7 && this.game.checkCanEat(this.getRow(), this.getColumn(), this.direction, 1, this.color)){
            pairArrayList.add(new Pair<>(this.getRow() + this.direction, this.getColumn() + 1));
        }

        // eat left diagonal
        if (this.getRow() > 0 && this.getColumn() > 0 && this.game.checkCanEat(this.getRow(), this.getColumn(), this.direction, -1, this.color)){
            pairArrayList.add(new Pair<>(this.getRow() + this.direction, this.getColumn() - 1));
        }

        for (int i = 0; i < pairArrayList.size(); i++){
            System.out.println(pairArrayList.get(i).getR());
            System.out.println(pairArrayList.get(i).getC());
        }
    }
}
