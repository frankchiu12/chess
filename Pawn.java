package chess;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Pawn implements Piece {

    private final int startingPosition;
    private final int direction;
    private Pane gamePane;
    private Game game;
    private Color color;
    private ImageView imageView;

    public Pawn(Pane gamePane, Game game, int x, int y, Color color){

        this.gamePane = gamePane;
        this.game = game;
        this.color = color;
        this.imageView = new ImageView();

        Image image = this.color == Color.WHITE ? new Image("chess/whitePawn.png") : new Image("chess/blackPawn.png");
        this.startingPosition = this.color == Color.WHITE ? 6 : 1;
        this.direction = this.color == Color.WHITE ? -1 : 1;

        this.imageView.setImage(image);
        this.imageView.setX(x * 80);
        this.imageView.setY(y * 80);
        this.imageView.setFitWidth(80);
        this.imageView.setFitHeight(80);

        gamePane.getChildren().add(this.imageView);
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public void getPossibleMoves(){
        ArrayList<Pair<Integer, Integer>> pairArrayList = new ArrayList<>();
        if (this.getRow() == this.startingPosition && this.game.checkCanMove(this.getRow(), this.getColumn(), this.direction, 0) && this.game.checkCanMove(this.getRow(), this.getColumn(), this.direction * 2, 0)){
                pairArrayList.add(new Pair<>(this.getRow() + this.direction * 2, this.getColumn()));
        }
        boolean isBeforeEndOfBoard = this.color == Color.WHITE ? this.getRow() > 0 : this.getRow() < 7;

        if (isBeforeEndOfBoard && this.game.checkCanMove(this.getRow(), this.getColumn(), this.direction, 0)){
                pairArrayList.add(new Pair<>(this.getRow() + this.direction, this.getColumn()));
        }

        if (this.getRow() > 0 && this.getColumn() < this.startingPosition - this.direction && this.game.checkCanEat(this.getRow(), this.getColumn(), this.direction, 1, this.color)){
                pairArrayList.add(new Pair<>(this.getRow() + this.direction, this.getColumn() + 1));
        }

        if (this.getRow() > 0 && this.getColumn() > this.startingPosition - this.direction && this.game.checkCanEat(this.getRow(), this.getColumn(), this.direction, -1, this.color)){
                pairArrayList.add(new Pair<>(this.getRow() + this.direction, this.getColumn() - 1));
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

    private ArrayList<Piece> getPieceArrayList(int row, int column){
        return this.game.getTiles()[row][column].getPieceArrayList();
    }

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
