package chess;

import chess.codeFinished.Coordinate;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Knight extends ChessPiece {
    private final Game game;
    private final Color color;
    private final ArrayList<Coordinate<Integer, Integer>> possibleMovesArray;


    public Knight(Pane gamePane, Game game, int x, int y, Color color, String imagePath) {
        super(gamePane, game, x, y, color, imagePath);
        this.game = game;
        this.color = color;
        this.possibleMovesArray = new ArrayList<>();
        this.possibleMovesArray.add(new Coordinate(1, 2));
        this.possibleMovesArray.add(new Coordinate(1, -2));
        this.possibleMovesArray.add(new Coordinate(2, 1));
        this.possibleMovesArray.add(new Coordinate(2, -1));
    }


    @Override
    public void overallMovement(ArrayList<Coordinate<Integer, Integer>> coordinateArrayList) {
        this.knightMovement(2,1, coordinateArrayList);
        this.knightMovement(2,-1, coordinateArrayList);
        this.knightMovement(-2,-1, coordinateArrayList);
        this.knightMovement(-2,1, coordinateArrayList);
        this.knightMovement(-1,2, coordinateArrayList);
        this.knightMovement(-1,-2, coordinateArrayList);
        this.knightMovement(1,-2, coordinateArrayList);
        this.knightMovement(1,2, coordinateArrayList);
    }

    private void knightMovement(int rowOffset, int columnOffset, ArrayList<Coordinate<Integer, Integer>> coordinateArrayList){
        if(this.game.checkCanMove(this.getRow(), this.getColumn(), rowOffset, columnOffset) || this.game.checkCanEat(this.getRow(), this.getColumn(), rowOffset, columnOffset, this.color)) {
            coordinateArrayList.add(new Coordinate<>(this.getRow() + rowOffset, this.getColumn() + columnOffset));
        }
    }
}
