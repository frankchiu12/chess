package chess;

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
    public void overallMovement(ArrayList<Pair<Integer, Integer>> pairArrayList) {
        this.diagonalMovement(pairArrayList);
        this.horizontalMovement(pairArrayList);
    }

    public void diagonalMovement(ArrayList<Pair<Integer, Integer>> pairArrayList){

        int row = this.getRow();
        int column = this.getColumn();

        if (row - 1 >= 0 && column + 1 < 8){
            if (this.game.checkCanMove(row, column, -1, 1) || this.game.checkCanEat(row, column, -1, 1, this.getColor())){
                pairArrayList.add(new Pair<>(row - 1, column + 1));
            }
        }

        if (row - 1 >= 0 && column - 1 >= 0){
            if (this.game.checkCanMove(row, column, -1, -1) || this.game.checkCanEat(row, column, -1, -1, this.getColor())){
                pairArrayList.add(new Pair<>(row - 1, column - 1));
            }
        }

        if (row + 1 < 8 && column + 1 < 8){
            if (this.game.checkCanMove(row, column, 1, 1) || this.game.checkCanEat(row, column, 1, 1, this.getColor())){
                pairArrayList.add(new Pair<>(row + 1, column + 1));
            }
        }

        if (row + 1 < 8 && column - 1 >= 0){
            if (this.game.checkCanMove(row, column, 1, -1) || this.game.checkCanEat(row, column, 1, -1, this.getColor())){
                pairArrayList.add(new Pair<>(row + 1, column - 1));
            }
        }
    }

    public void horizontalMovement(ArrayList<Pair<Integer, Integer>> pairArrayList){
        int row = this.getRow();
        int column = this.getColumn();

        if (row - 1 >= 0){
            if (this.game.checkCanMove(row, column, -1, 0) || this.game.checkCanEat(row, column, -1, 0, this.getColor())){
                pairArrayList.add(new Pair<>(row - 1, column));
            }
        }

        if (row + 1 < 8){
            if (this.game.checkCanMove(row, column, 1, 0) || this.game.checkCanEat(row, column, 1, 0, this.getColor())){
                pairArrayList.add(new Pair<>(row + 1, column));
            }
        }

        if (column - 1 >= 0){
            if (this.game.checkCanMove(row, column, 0, -1) || this.game.checkCanEat(row, column, 0, -1, this.getColor())){
                pairArrayList.add(new Pair<>(row, column - 1));
            }
        }

        if (column + 1 < 8){
            if (this.game.checkCanMove(row, column, 0, 1) || this.game.checkCanEat(row, column, 0, 1, this.getColor())){
                pairArrayList.add(new Pair<>(row, column + 1));
            }
        }
    }
}