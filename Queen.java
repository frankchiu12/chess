package chess;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Queen extends ChessPiece {

    private Game game;
    private Color color;

    public Queen(Pane gamePane, Game game, int x, int y, Color color, String imagePath) {
        super(gamePane, game, x, y, color, imagePath);
        this.game = game;
        this.color = color;
    }

    @Override
    public void overallMovement(ArrayList<Pair<Integer, Integer>> pairArrayList) {
        this.diagonalMovement(pairArrayList);
        this.horizontalMovement(pairArrayList);
    }

    public void diagonalMovement(ArrayList<Pair<Integer, Integer>> pairArrayList){

        int iterationRow = this.getRow();
        int iterationColumn = this.getColumn();

        if (iterationRow - 1 >= 0){
            for (int row = iterationRow; row >= 0; row--){
                iterationColumn ++;
                if (row - 1 < 0 || iterationColumn >= 8){
                    break;
                }
                if (iterateWithRowMinus1(pairArrayList, iterationColumn, row)) break;
            }

            iterationRow = this.getRow();
            iterationColumn = this.getColumn();
            for (int row = iterationRow; row >= 0; row--){
                iterationColumn --;
                if (row - 1 < 0 || iterationColumn < 0){
                    break;
                }
                if (iterateWithRowMinus1(pairArrayList, iterationColumn, row)) break;
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
                if (iterateWithRowPlus1(pairArrayList, iterationColumn, row)) break;
            }

            iterationRow = this.getRow();
            iterationColumn = this.getColumn();
            for (int row = iterationRow; row < 7; row++){
                iterationColumn = iterationColumn + 1;
                if (iterationColumn >= 8){
                    break;
                }
                if (iterateWithRowPlus1(pairArrayList, iterationColumn, row)) break;
            }
        }
    }

    public void horizontalMovement(ArrayList<Pair<Integer, Integer>> pairArrayList){

        int iterationRow = this.getRow();
        int iterationColumn = this.getColumn();

        if (iterationRow - 1 >= 0){
            for (int row = iterationRow; row >= 0; row--){
                if (row - 1 < 0){
                    break;
                }
                if (this.getPieceArrayList(row - 1, iterationColumn).size() != 0){
                    if (this.getPieceArrayList(row - 1, iterationColumn).size() == 1 && this.getPieceArrayList(row - 1, iterationColumn).get(0).getColor() == this.game.getOppositeColor(this.color)){
                        pairArrayList.add(new Pair<>(row - 1, iterationColumn));
                    }
                    break;
                }
                if (this.game.checkCanMove(row, iterationColumn, -1, 0)){
                    pairArrayList.add(new Pair<>(row - 1, iterationColumn));
                }
            }
        }

        iterationRow = this.getRow();
        iterationColumn = this.getColumn();
        if (iterationRow + 1 < 8){
            for (int i = iterationRow; i < 7; i++){
                if (this.getPieceArrayList(i + 1, iterationColumn).size() != 0){
                    if (this.getPieceArrayList(i + 1, iterationColumn).get(0).getColor() == this.game.getOppositeColor(this.color)){
                        pairArrayList.add(new Pair<>(i + 1, iterationColumn));
                    }
                    break;
                }
                if (this.game.checkCanMove(i, iterationColumn, 1, 0)){
                    pairArrayList.add(new Pair<>(i + 1, iterationColumn));
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
                        pairArrayList.add(new Pair<>(iterationRow, i - 1));
                    }
                    break;
                }
                if (this.game.checkCanMove(iterationRow, i, 0, -1)){
                    pairArrayList.add(new Pair<>(iterationRow, i - 1));
                }
            }
        }

        iterationRow = this.getRow();
        iterationColumn = this.getColumn();
        if (iterationColumn + 1 < 8){
            for (int i = iterationColumn; i < 7; i++){
                if (this.getPieceArrayList(iterationRow, i + 1).size() != 0){
                    if (this.getPieceArrayList(iterationRow, i + 1).get(0).getColor() == this.game.getOppositeColor(this.color)){
                        pairArrayList.add(new Pair<>(iterationRow, i + 1));
                    }
                    break;
                }
                if (this.game.checkCanMove(iterationRow, i, 0, 1)){
                    pairArrayList.add(new Pair<>(iterationRow, i + 1));
                }
            }
        }
    }

    // TODO: combine
    private boolean iterateWithRowMinus1(ArrayList<Pair<Integer, Integer>> pairArrayList, int iterationColumn, int i) {
        if (this.getPieceArrayList(i - 1, iterationColumn).size() != 0){
            if (this.getPieceArrayList(i - 1, iterationColumn).get(0).getColor() == this.game.getOppositeColor(this.color)){
                pairArrayList.add(new Pair<>(i - 1, iterationColumn));
            }
            return true;
        }
        if (this.game.checkCanMove(i, iterationColumn, -1, 0)){
            pairArrayList.add(new Pair<>(i - 1, iterationColumn));
        }
        return false;
    }

    private boolean iterateWithRowPlus1(ArrayList<Pair<Integer, Integer>> pairArrayList, int iterationColumn, int i) {
        if (this.getPieceArrayList(i + 1, iterationColumn).size() != 0){
            if (this.getPieceArrayList(i + 1, iterationColumn).get(0).getColor() == this.game.getOppositeColor(this.color)){
                pairArrayList.add(new Pair<>(i + 1, iterationColumn));
            }
            return true;
        }
        if (this.game.checkCanMove(i, iterationColumn, 1, 0)){
            pairArrayList.add(new Pair<>(i + 1, iterationColumn));
        }
        return false;
    }
}
