package chess;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

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
        if (this.getRow() == 6 && this.color == Color.WHITE){
            if (this.game.checkCanMove(this.getRow(), this.getColumn(), -2, 0)){
                this.game.getTiles()[this.getRow()][this.getColumn()].getPieceArrayList().remove(this);
                this.setRow(this.getRow() - 2);
                this.game.getTiles()[this.getRow()][this.getColumn()].getPieceArrayList().add(this);
            }
        }
        else if (this.getRow() == 1 && this.color == Color.BLACK){
            if (this.game.checkCanMove(this.getRow(), this.getColumn(), 2, 0)){
                this.game.getTiles()[this.getRow()][this.getColumn()].getPieceArrayList().remove(this);
                this.setRow(this.getRow() + 2);
                this.game.getTiles()[this.getRow()][this.getColumn()].getPieceArrayList().add(this);
            }
        }
        else if (this.color == Color.WHITE && this.game.checkCanMove(this.getRow(), this.getColumn(), -1, 0)){
            this.game.getTiles()[this.getRow()][this.getColumn()].getPieceArrayList().remove(this);
            this.setRow(this.getRow() - 1);
            this.game.getTiles()[this.getRow()][this.getColumn()].getPieceArrayList().add(this);
        }
        else if (this.color == Color.BLACK && this.game.checkCanMove(this.getRow(), this.getColumn(), 1, 0)){
            this.game.getTiles()[this.getRow()][this.getColumn()].getPieceArrayList().remove(this);
            this.setRow(this.getRow() + 1);
            this.game.getTiles()[this.getRow()][this.getColumn()].getPieceArrayList().add(this);
        }
        else if (this.color == Color.WHITE && this.game.checkCanEat(this.getRow(), this.getColumn(), -1, 1, Color.WHITE)){
            this.game.getTiles()[this.getRow()][this.getColumn()].getPieceArrayList().remove(this);
            this.setRow(this.getRow() - 1);
            this.setColumn(this.getColumn() + 1);
            this.game.getTiles()[this.getRow()][this.getColumn()].getPieceArrayList().clear();
            this.game.getTiles()[this.getRow()][this.getColumn()].getPieceArrayList().add(this);
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
        this.imageView.setX(column * 80);
    }
}
