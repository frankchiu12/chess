package chess;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Rook implements Piece {

    private Pane gamePane;
    private Game game;
    private Color color;
    private ImageView imageView;

    public Rook(Pane gamePane, Game game, int x, int y, Color color){

        this.gamePane = gamePane;
        this.game = game;
        this.color = color;
        this.imageView = new ImageView();

        Image image = new Image("chess/blackRook.png");
        if (color == Color.WHITE){
            image = new Image("chess/whiteRook.png");
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
        int iterationRow = this.getRow();
        int iterationColumn = this.getColumn();
        ArrayList<Pair<Integer, Integer>> pairArrayList = new ArrayList<>();

        if (iterationRow - 1 >= 0){
            iterationRow = iterationRow - 1;
            while(this.getPieceArrayList(iterationRow, iterationColumn).size() == 0){
                if (iterationRow - 1 >= 0){
                    if (this.game.checkCanMove(iterationRow, iterationColumn, -1, 0)){
                        pairArrayList.add(new Pair<>(iterationRow - 1, iterationColumn));
                    }
                }
                iterationRow = iterationRow - 1;
                if (iterationRow < 0){
                    break;
                }
            }
        }
        if (iterationRow + 1 < 8){
            iterationRow = iterationRow + 1;
            while(this.getPieceArrayList(iterationRow, iterationColumn).size() == 0){
                if (iterationRow + 1 < 8){
                    if (this.game.checkCanMove(iterationRow, iterationColumn, 1, 0)){
                        pairArrayList.add(new Pair<>(iterationRow + 1, iterationColumn));
                    }
                }
                iterationRow = iterationRow + 1;
                if (iterationRow >= 8){
                    break;
                }
            }
        }

        if (iterationColumn - 1 >= 0){
            iterationColumn = iterationColumn - 1;
            while(this.getPieceArrayList(iterationRow, iterationColumn).size() == 0){
                if (iterationColumn - 1 >= 0){
                    if (this.game.checkCanMove(iterationRow, iterationColumn, 0, -1)){
                        pairArrayList.add(new Pair<>(iterationRow, iterationColumn - 1));
                    }
                }
                iterationColumn = iterationColumn - 1;
                if (iterationColumn < 0){
                    break;
                }
            }
        }

        if (iterationColumn + 1 < 8){
            iterationColumn = iterationColumn + 1;
            while(this.getPieceArrayList(iterationRow, iterationColumn).size() == 0){
                if (iterationColumn + 1 < 8){
                    if (this.game.checkCanMove(iterationRow, iterationColumn, 0, 1)){
                        pairArrayList.add(new Pair<>(iterationRow, iterationColumn + 1));
                    }
                }
                iterationColumn = iterationColumn + 1;
                if (iterationColumn >= 8){
                    break;
                }
            }
        }

        this.game.changeColor(this.getRow(), this.getColumn(), Color.YELLOW);

        for (Pair pair : pairArrayList) {
            int row = (int) pair.getR();
            int column = (int) pair.getC();
            if (this.getPieceArrayList(row, column).size() != 0) {
                this.game.changeColor(row, column, Color.RED);
            } else {
                this.game.changeColor(row, column, Color.GREEN);
            }
        }
    }

    @Override
    public void move(int clickRow, int clickColumn) {
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

    public int getRow(){
        return (int) (this.imageView.getY() / 80);
    }

    public int getColumn(){
        return (int) (this.imageView.getX() / 80);
    }

    private void setRow(int row){
        this.imageView.setY(row * 80);
    }

    private void setColumn(int column){this.imageView.setX(column * 80);}
}
