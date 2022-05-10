package chess;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Bishop implements Piece {

    private Pane gamePane;
    private Game game;
    private Color color;
    private ImageView imageView;

    public Bishop(Pane gamePane, Game game, int x, int y, Color color) {
        this.gamePane = gamePane;
        this.game = game;
        this.color = color;
        this.imageView = new ImageView();

        Image image = new Image("chess/blackRook.png");
        if (color == Color.WHITE){
            image = new Image("chess/whiteBishop.png");
        }

        this.imageView.setImage(image);
        this.imageView.setX(x * 80);
        this.imageView.setY(y * 80);
        this.imageView.setFitWidth(80);
        this.imageView.setFitHeight(80);

        this.gamePane.getChildren().add(this.imageView);
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public void getPossibleMoves() {
        ArrayList<Pair<Integer, Integer>> pairArrayList = new ArrayList<>();

        int iterationRow = this.getRow();
        int iterationColumn = this.getColumn();
        if (iterationRow - 1 >= 0){
            for (int i = iterationRow; i >= 0; i--){
                iterationColumn = iterationColumn + 1;
                if (i - 1 < 0){
                    break;
                }
                if (iterationColumn >= 8){
                    break;
                }
                if (iterateWithRowMinus1(pairArrayList, iterationColumn, i)) break;
            }
        }

        iterationRow = this.getRow();
        iterationColumn = this.getColumn();
        if (iterationRow - 1 >= 0){
            for (int i = iterationRow; i >= 0; i--){
                iterationColumn = iterationColumn - 1;
                if (i - 1 < 0){
                    break;
                }
                if (iterationColumn < 0){
                    break;
                }
                if (iterateWithRowMinus1(pairArrayList, iterationColumn, i)) break;
            }
        }

        iterationRow = this.getRow();
        iterationColumn = this.getColumn();
        if (iterationRow + 1 < 8){
            for (int i = iterationRow; i < 7; i++){
                int row = i + 1;
                iterationColumn = iterationColumn - 1;
                if (iterationColumn < 0){
                    break;
                }
                if (iterateWithRowPlus1(pairArrayList, iterationColumn, i, row)) break;
            }
        }

        iterationRow = this.getRow();
        iterationColumn = this.getColumn();
        if (iterationRow + 1 < 8){
            for (int i = iterationRow; i < 7; i++){
                int row = i + 1;
                iterationColumn = iterationColumn + 1;
                if (iterationColumn >= 8){
                    break;
                }
                if (iterateWithRowPlus1(pairArrayList, iterationColumn, i, row)) break;
            }
        }

        this.game.changeColor(this.getRow(), this.getColumn(), Color.YELLOW);

        for (Pair<Integer, Integer> pair : pairArrayList) {
            int row = pair.getR();
            int column = pair.getC();
            if (this.getPieceArrayList(row, column).size() != 0) {
                this.game.changeColor(row, column, Color.RED);
            } else {
                this.game.changeColor(row, column, Color.GREEN);
            }
        }
    }

    private boolean iterateWithRowMinus1(ArrayList<Pair<Integer, Integer>> pairArrayList, int iterationColumn, int i) {
        if (this.getPieceArrayList(i - 1, iterationColumn).size() != 0){
            if (this.getPieceArrayList(i - 1, iterationColumn).size() == 1 && this.getPieceArrayList(i - 1, iterationColumn).get(0).getColor() == this.game.getOppositeColor(this.color)){
                pairArrayList.add(new Pair<>(i - 1, iterationColumn));
            }
            return true;
        }
        if (this.game.checkCanMove(i, iterationColumn, -1, 0) || this.game.checkCanEat(i, iterationColumn, -1, 0, this.color)){
            pairArrayList.add(new Pair<>(i - 1, iterationColumn));
        }
        return false;
    }

    private boolean iterateWithRowPlus1(ArrayList<Pair<Integer, Integer>> pairArrayList, int iterationColumn, int i, int row) {
        if (this.getPieceArrayList(row, iterationColumn).size() != 0){
            if (this.getPieceArrayList(row, iterationColumn).size() == 1 && this.getPieceArrayList(row, iterationColumn).get(0).getColor() == this.game.getOppositeColor(this.color)){
                pairArrayList.add(new Pair<>(row, iterationColumn));
            }
            return true;
        }
        if (this.game.checkCanMove(i, iterationColumn, 1, 0) || this.game.checkCanEat(i, iterationColumn, 1, 0, this.color)){
            pairArrayList.add(new Pair<>(row, iterationColumn));
        }
        return false;
    }

    @Override
    public void move(int clickRow, int clickColumn){
        Color tileColor = this.game.getTiles()[clickRow][clickColumn].getColor();
        if (tileColor == Color.GREEN || tileColor == Color.RED){
            this.setRow(clickRow);
            this.setColumn(clickColumn);
            this.getPieceArrayList(clickRow, clickColumn).clear();
            this.getPieceArrayList(clickRow, clickColumn).add(this);
        }
    }

    @Override
    public void removeImage(){
        this.gamePane.getChildren().remove(this.imageView);
    }

    private ArrayList<Piece> getPieceArrayList(int row, int column){return this.game.getTiles()[row][column].getPieceArrayList();}

    public int getRow(){return (int) (this.imageView.getY() / 80);}

    public int getColumn(){
        return (int) (this.imageView.getX() / 80);
    }

    private void setRow(int row){this.imageView.setY(row * 80);}

    private void setColumn(int column){this.imageView.setX(column * 80);}
}
