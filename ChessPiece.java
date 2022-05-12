package chess;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class ChessPiece {

    private final Pane gamePane;
    private final Game game;
    private final Color color;
    private ImageView imageView;

    public ChessPiece(Pane gamePane, Game game, int x, int y, Color color, String imagePath) {
        this.gamePane = gamePane;
        this.game = game;
        this.color = color;
        this.addImage(x, y, imagePath);
    }

    public void addImage(int x, int y, String imagePath) {
        Image image = new Image(imagePath);
        this.imageView = new ImageView();
        this.imageView.setImage(image);
        this.imageView.setX(x * 80);
        this.imageView.setY(y * 80);
        this.imageView.setFitWidth(80);
        this.imageView.setFitHeight(80);
        this.gamePane.getChildren().add(this.imageView);
    }

    public void getPossibleMoves() {
        ArrayList<Pair<Integer, Integer>> pairArrayList = new ArrayList<>();

        this.overallMovement(pairArrayList);

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

    public void overallMovement(ArrayList<Pair<Integer, Integer>> pairArrayList) {}

    public void move(int clickRow, int clickColumn){
        Color tileColor = this.game.getTiles()[clickRow][clickColumn].getColor();
        if (tileColor == Color.GREEN || tileColor == Color.RED){
            this.setRow(clickRow);
            this.setColumn(clickColumn);
            this.getPieceArrayList(clickRow, clickColumn).clear();
            this.getPieceArrayList(clickRow, clickColumn).add(this);
        }
    }

    public void removeImage(){
        this.gamePane.getChildren().remove(this.imageView);
    }

    public int getRow(){return (int) (this.imageView.getY() / 80);}

    public int getColumn(){
        return (int) (this.imageView.getX() / 80);
    }

    public void setRow(int row){this.imageView.setY(row * 80);}

    public void setColumn(int column) {
        this.imageView.setX(column * 80);
    }

    public Color getColor() {
        return this.color;
    }

    public ArrayList<ChessPiece> getPieceArrayList(int row, int column){return this.game.getTiles()[row][column].getPieceArrayList();}
}