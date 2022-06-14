package chess;

import chess.codeFinished.Coordinate;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Queen extends ChessPiece {

    private final Game game;
    private final Color color;

    public Queen(Pane gamePane, Game game, int x, int y, Color color, String imagePath) {
        super(gamePane, game, x, y, color, imagePath);
        this.game = game;
        this.color = color;
    }

    @Override
    public void overallMovement(ArrayList<Coordinate<Integer, Integer>> coordinateArrayList) {
        this.diagonalMovement(coordinateArrayList);
        this.horizontalMovement(coordinateArrayList);
    }

    public void diagonalMovement(ArrayList<Coordinate<Integer, Integer>> coordinateArrayList){

        int iterationRow = this.getRow();
        int iterationColumn = this.getColumn();

        if (iterationRow - 1 >= 0){
            for (int row = iterationRow; row >= 0; row--){
                iterationColumn ++;
                if (row - 1 < 0 || iterationColumn >= 8){
                    break;
                }
                if (iterateWithRow(coordinateArrayList, iterationColumn, row, -1)) break;
            }

            iterationRow = this.getRow();
            iterationColumn = this.getColumn();
            for (int row = iterationRow; row >= 0; row--){
                iterationColumn --;
                if (row - 1 < 0 || iterationColumn < 0){
                    break;
                }
                if (iterateWithRow(coordinateArrayList, iterationColumn, row, -1)) break;
            }
        }

        iterationRow = this.getRow();
        iterationColumn = this.getColumn();
        if (iterationRow + 1 < 8){
            for (int row = iterationRow; row < 7; row++){
                iterationColumn = iterationColumn - 1;
                if (iterationColumn < 0){
                    break;
                }
                if (iterateWithRow(coordinateArrayList, iterationColumn, row, 1)) break;
            }

            iterationRow = this.getRow();
            iterationColumn = this.getColumn();
            for (int row = iterationRow; row < 7; row++){
                iterationColumn = iterationColumn + 1;
                if (iterationColumn >= 8){
                    break;
                }
                if (iterateWithRow(coordinateArrayList, iterationColumn, row, 1)) break;
            }
        }
    }

    public void horizontalMovement(ArrayList<Coordinate<Integer, Integer>> coordinateArrayList){

        int iterationRow = this.getRow();
        int iterationColumn = this.getColumn();

        if (iterationRow - 1 >= 0){
            for (int row = iterationRow; row >= 0; row--){
                if (row - 1 < 0){
                    break;
                }
                if (this.getPieceArrayList(row - 1, iterationColumn).size() != 0){
                    if (this.getPieceArrayList(row - 1, iterationColumn).size() == 1 && this.getPieceArrayList(row - 1, iterationColumn).get(0).getColor() == this.game.getOppositeColor(this.color)){
                        coordinateArrayList.add(new Coordinate<>(row - 1, iterationColumn));
                    }
                    break;
                }
                if (this.game.checkCanMove(row, iterationColumn, -1, 0)){
                    coordinateArrayList.add(new Coordinate<>(row - 1, iterationColumn));
                }
            }
        }

        iterationRow = this.getRow();
        iterationColumn = this.getColumn();
        if (iterationRow + 1 < 8){
            for (int i = iterationRow; i < 7; i++){
                if (this.getPieceArrayList(i + 1, iterationColumn).size() != 0){
                    if (this.getPieceArrayList(i + 1, iterationColumn).get(0).getColor() == this.game.getOppositeColor(this.color)){
                        coordinateArrayList.add(new Coordinate<>(i + 1, iterationColumn));
                    }
                    break;
                }
                if (this.game.checkCanMove(i, iterationColumn, 1, 0)){
                    coordinateArrayList.add(new Coordinate<>(i + 1, iterationColumn));
                }
            }
        }

        iterationRow = this.getRow();
        iterationColumn = this.getColumn();
        if (iterationColumn - 1 >= 0){
            for (int i = iterationColumn; i >= 0; i--){
                if (i - 1 < 0){
                    break;
                }
                if (this.getPieceArrayList(iterationRow, i - 1).size() != 0){
                    if (this.getPieceArrayList(iterationRow, i - 1).get(0).getColor() == this.game.getOppositeColor(this.color)){
                        coordinateArrayList.add(new Coordinate<>(iterationRow, i - 1));
                    }
                    break;
                }
                if (this.game.checkCanMove(iterationRow, i, 0, -1)){
                    coordinateArrayList.add(new Coordinate<>(iterationRow, i - 1));
                }
            }
        }

        iterationRow = this.getRow();
        iterationColumn = this.getColumn();
        if (iterationColumn + 1 < 8){
            for (int column = iterationColumn; column < 7; column++){
                if (this.getPieceArrayList(iterationRow, column + 1).size() != 0){
                    if (this.getPieceArrayList(iterationRow, column + 1).get(0).getColor() == this.game.getOppositeColor(this.color)){
                        coordinateArrayList.add(new Coordinate<>(iterationRow, column + 1));
                    }
                    break;
                }
                if (this.game.checkCanMove(iterationRow, column, 0, 1)){
                    coordinateArrayList.add(new Coordinate<>(iterationRow, column + 1));
                }
            }
        }
    }

    private boolean iterateWithRow(ArrayList<Coordinate<Integer, Integer>> coordinateArrayList, int iterationColumn, int i, int offset) {
        if (this.getPieceArrayList(i + offset, iterationColumn).size() != 0){
            if (this.getPieceArrayList(i + offset, iterationColumn).get(0).getColor() == this.game.getOppositeColor(this.color)){
                coordinateArrayList.add(new Coordinate<>(i + offset, iterationColumn));
            }
            return true;
        }
        if (this.game.checkCanMove(i, iterationColumn, offset, 0)){
            coordinateArrayList.add(new Coordinate<>(i + offset, iterationColumn));
        }
        return false;
    }
}
