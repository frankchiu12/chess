package chess;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Knight extends ChessPiece {
    private final Game game;
    private final Color color;
    private final int startingPosition;
    private final int direction;
    private final ArrayList<Pair<Integer, Integer>> possibleMovesArray;


    public Knight(Pane gamePane, Game game, int x, int y, Color color, String imagePath) {
        super(gamePane, game, x, y, color, imagePath);
        this.game = game;
        this.color = color;
        this.startingPosition = y;
        this.direction = this.color == Color.WHITE ? -1 : 1;
        this.possibleMovesArray = new ArrayList<>();
        this.possibleMovesArray.add(new Pair(1, 2));
        this.possibleMovesArray.add(new Pair(1, -2));
        this.possibleMovesArray.add(new Pair(2, 1));
        this.possibleMovesArray.add(new Pair(2, -1));
    }


    @Override
    public void overallMovement(ArrayList<Pair<Integer, Integer>> pairArrayList) {

//        for (Pair possibleMove : this.possibleMovesArray) {
//            if(this.game.checkCanMove(this.getRow(), this.getColumn(), possibleMove.getR(), 1) || this.game.checkCanEat(this.getRow(), this.getColumn(), 2, 1, this.color)) {
//                pairArrayList.add(new Pair<>(this.getRow() + 2, this.getColumn() + 1));
//            }
//        }

        if(this.game.checkCanMove(this.getRow(), this.getColumn(), 2, 1) || this.game.checkCanEat(this.getRow(), this.getColumn(), 2, 1, this.color)) {
            pairArrayList.add(new Pair<>(this.getRow() + 2, this.getColumn() + 1));
        }
        if(this.game.checkCanMove(this.getRow(), this.getColumn(), 2, -1)  || this.game.checkCanEat(this.getRow(), this.getColumn(), 2, -1, this.color)) {
            pairArrayList.add(new Pair<>(this.getRow() + 2, this.getColumn() - 1));
        }

        if(this.game.checkCanMove(this.getRow(), this.getColumn(), -2, -1)  || this.game.checkCanEat(this.getRow(), this.getColumn(), -2, -1, this.color)) {
            pairArrayList.add(new Pair<>(this.getRow() - 2, this.getColumn() - 1));
        }

        if(this.game.checkCanMove(this.getRow(), this.getColumn(), -2, 1)  || this.game.checkCanEat(this.getRow(), this.getColumn(), -2, 1, this.color)) {
            pairArrayList.add(new Pair<>(this.getRow() - 2, this.getColumn() + 1));
        }

        if(this.game.checkCanMove(this.getRow(), this.getColumn(), -1, 2)  || this.game.checkCanEat(this.getRow(), this.getColumn(), -1, 2, this.color)) {
            pairArrayList.add(new Pair<>(this.getRow() - 1, this.getColumn() + 2));
        }

        if(this.game.checkCanMove(this.getRow(), this.getColumn(), -1, -2)  || this.game.checkCanEat(this.getRow(), this.getColumn(), -1, -2, this.color)) {
            pairArrayList.add(new Pair<>(this.getRow() - 1, this.getColumn() - 2));
        }

        if(this.game.checkCanMove(this.getRow(), this.getColumn(), 1, -2)  || this.game.checkCanEat(this.getRow(), this.getColumn(), 1, -2, this.color)) {
            pairArrayList.add(new Pair<>(this.getRow() + 1, this.getColumn() - 2));
        }

        if(this.game.checkCanMove(this.getRow(), this.getColumn(), 1, 2)  || this.game.checkCanEat(this.getRow(), this.getColumn(), 1, 2, this.color)) {
            pairArrayList.add(new Pair<>(this.getRow() + 1, this.getColumn() + 2));
        }
//        if (this.getRow() == this.startingPosition && this.game.checkCanMove(this.getRow(), this.getColumn(), this.direction, 0) && this.game.checkCanMove(this.getRow(), this.getColumn(), this.direction * 2, 0)){
//            pairArrayList.add(new Pair<>(this.getRow() + this.direction * 2, this.getColumn()));
//        }
//
//        if (isBeforeEndOfBoard && this.game.checkCanMove(this.getRow(), this.getColumn(), this.direction, 0)){
//            pairArrayList.add(new Pair<>(this.getRow() + this.direction, this.getColumn()));
//        }
//
//        if (this.getRow() > 0 && this.getColumn() < 7 && this.game.checkCanEat(this.getRow(), this.getColumn(), this.direction, 1, this.color)){
//            pairArrayList.add(new Pair<>(this.getRow() + this.direction, this.getColumn() + 1));
//        }
//
//        if (this.getRow() > 0 && this.getColumn() > 0 && this.game.checkCanEat(this.getRow(), this.getColumn(), this.direction, -1, this.color)){
//            pairArrayList.add(new Pair<>(this.getRow() + this.direction, this.getColumn() - 1));
//        }
    }
}
