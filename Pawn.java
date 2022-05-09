package chess;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Pawn implements Piece {

    private Pane gamePane;
    private Game game;
    private ImageView imageView;
    private Color color;

    public Pawn(Pane gamePane, Game game, int x, int y, Color color){

        this.gamePane = gamePane;
        this.game = game;
        this.color = color;
        Image image = new Image("chess/blackPawn.png");

        if (this.color == Color.WHITE){
            image = new Image("chess/whitePawn.png");
        }

        this.imageView = new ImageView();
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
    public void move(){
        ArrayList<Pair> pairArrayList = new ArrayList<>();
        if (this.getRow() == 6 && this.color == Color.WHITE){
            if (this.game.checkCanMove(this.getRow(), this.getColumn(), -2, 0)){
                pairArrayList.add(new Pair(this.getRow() - 2, this.getColumn()));
//                this.game.getTiles()[this.getRow()][this.getColumn()].getPieceArrayList().remove(this);
//                this.setRow(this.getRow() - 2);
//                this.game.getTiles()[this.getRow()][this.getColumn()].getPieceArrayList().add(this);
            }
        }
        if (this.getRow() == 1 && this.color == Color.BLACK){
            if (this.game.checkCanMove(this.getRow(), this.getColumn(), 2, 0)){
                pairArrayList.add(new Pair(this.getRow() + 2, this.getColumn()));
//                this.game.getTiles()[this.getRow()][this.getColumn()].getPieceArrayList().remove(this);
//                this.setRow(this.getRow() + 2);
//                this.game.getTiles()[this.getRow()][this.getColumn()].getPieceArrayList().add(this);
            }
        }
        if (this.color == Color.WHITE && this.game.checkCanMove(this.getRow(), this.getColumn(), -1, 0)){
            pairArrayList.add(new Pair(this.getRow() - 1, this.getColumn()));
//            this.game.getTiles()[this.getRow()][this.getColumn()].getPieceArrayList().remove(this);
//            this.setRow(this.getRow() - 1);
//            this.game.getTiles()[this.getRow()][this.getColumn()].getPieceArrayList().add(this);
        }
        if (this.color == Color.BLACK && this.game.checkCanMove(this.getRow(), this.getColumn(), 1, 0)){
            pairArrayList.add(new Pair(this.getRow() + 1, this.getColumn()));
//            this.game.getTiles()[this.getRow()][this.getColumn()].getPieceArrayList().remove(this);
//            this.setRow(this.getRow() + 1);
//            this.game.getTiles()[this.getRow()][this.getColumn()].getPieceArrayList().add(this);
        }
        if (this.color == Color.WHITE && this.game.checkCanEat(this.getRow(), this.getColumn(), -1, 1, Color.WHITE)){
            pairArrayList.add(new Pair(this.getRow() - 1, this.getColumn() + 1));
//            this.game.getTiles()[this.getRow()][this.getColumn()].getPieceArrayList().remove(this);
//            this.setRow(this.getRow() - 1);
//            this.setColumn(this.getColumn() + 1);
//            this.game.getTiles()[this.getRow()][this.getColumn()].getPieceArrayList().clear();
//            this.game.getTiles()[this.getRow()][this.getColumn()].getPieceArrayList().add(this);
        }
        for (int i = 0; i < pairArrayList.size(); i++){
            int row = (int) pairArrayList.get(i).getR();
            int column = (int) pairArrayList.get(i).getC();
            this.game.changeColor(row, column);
        }
    }

    private void removeImage(){
        this.gamePane.getChildren().remove(this.imageView);
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

    private void setColumn(int column){
        this.imageView.setX(column * 80);}
}
