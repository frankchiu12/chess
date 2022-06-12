package chess;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class ChessPiece {

    private final Pane gamePane;
    private final Game game;
    private ChessPiece chessPieceEaten;
    private final Color color;
    private ImageView imageView;
    public boolean hasMoved;
    public boolean leftRookJump;

    public ChessPiece(Pane gamePane, Game game, int x, int y, Color color, String imagePath) {
        this.gamePane = gamePane;
        this.game = game;
        this.color = color;
        this.imageView = new ImageView();
        this.addImage(x, y, imagePath);
        this.hasMoved = false;
    }

    public void addImage(int x, int y, String imagePath) {
        Image image = new Image(imagePath);
        this.imageView.setImage(image);
        this.imageView.setX(x * 80);
        this.imageView.setY(y * 80);
        this.imageView.setFitWidth(80);
        this.imageView.setFitHeight(80);
        this.gamePane.getChildren().add(this.imageView);
    }

    public void getPossibleMoves() {
        ArrayList<Coordinate<Integer, Integer>> coordinateArrayList = new ArrayList<>();

        this.overallMovement(coordinateArrayList);
        this.game.changeColor(this.getRow(), this.getColumn(), Color.YELLOW);

        for (Coordinate<Integer, Integer> coordinate : coordinateArrayList) {
            int row = coordinate.getR();
            int column = coordinate.getC();
            if (this.getPieceArrayList(row, column).size() != 0) {
                this.game.changeColor(row, column, Color.RED);
            } else {
                this.game.changeColor(row, column, Color.GREEN);
            }
        }
    }

    public void getCheckMoves(){
        ArrayList<Coordinate<Integer, Integer>> coordinateArrayList = new ArrayList<>();

        this.overallMovement(coordinateArrayList);
        this.game.changeColor(7 - this.getRow(), 7 - this.getColumn(), Color.YELLOW);

        for (Coordinate<Integer, Integer> coordinate : coordinateArrayList) {
            int row = coordinate.getR();
            int column = coordinate.getC();
            if (this.getPieceArrayList(row, column).size() != 0 && this.getPieceArrayList(row, column).get(0) instanceof King) {
                this.game.changeColor(7 - row, 7 - column, Color.RED);
            }
        }
    }

    public void overallMovement(ArrayList<Coordinate<Integer, Integer>> coordinateArrayList) {}

    public void move(int clickRow, int clickColumn){
        Color tileColor = this.game.getTiles()[clickRow][clickColumn].getColor();
        if (tileColor == Color.GREEN || tileColor == Color.RED){
            this.setRow(clickRow);
            this.setColumn(clickColumn);
            if (this.getPieceArrayList(clickRow, clickColumn).size() > 0){
                this.chessPieceEaten = this.getPieceArrayList(clickRow, clickColumn).get(0);
            }
            this.getPieceArrayList(clickRow, clickColumn).clear();
            this.getPieceArrayList(clickRow, clickColumn).add(this);
        }
        if (this instanceof King && !this.hasMoved){
            if (clickRow == 7 && clickColumn == 1){
                this.leftRookJump();
            }
            this.hasMoved = true;
        }
    }

    public void rookJump(int previousRow, int previousColumn, int jumpRow, int jumpColumn){
        this.setRow(jumpRow);
        this.setColumn(jumpColumn);
        this.getPieceArrayList(previousRow, previousColumn).clear();
        this.getPieceArrayList(jumpRow, jumpColumn).add(this);
    }

    public void reverseMove(int previousRow, int previousColumn, int currentRow, int currentColumn, ChessPiece chessPieceEaten){
        this.setRow(previousRow);
        this.setColumn(previousColumn);
        this.getPieceArrayList(currentRow, currentColumn).clear();
        this.getPieceArrayList(previousRow, previousColumn).add(this);
        if (chessPieceEaten != null){
            chessPieceEaten.setRow(currentRow);
            chessPieceEaten.setColumn(currentColumn);
            this.getPieceArrayList(currentRow, currentColumn).add(chessPieceEaten);
            chessPieceEaten.addImage();
        }
    }

    public void leftRookJump(){
        if (this.game.getTiles()[7][0].getPieceArrayList().get(0) instanceof Rook){
            this.game.getTiles()[7][0].getPieceArrayList().get(0).rookJump(7, 0, 7, 2);
        }
    }

    public void removeImage(){this.gamePane.getChildren().remove(this.imageView);}

    public void addImage(){this.gamePane.getChildren().add(this.imageView);}

    public boolean getHasMoved(){return this.hasMoved;}

    public int getRow(){return (int) (this.imageView.getY() / 80);}

    public int getColumn(){return (int) (this.imageView.getX() / 80);}

    public void setRow(int row){this.imageView.setY(row * 80);}

    public void setColumn(int column) {this.imageView.setX(column * 80);}

    public Color getColor() {return this.color;}

    public ChessPiece getChessPieceEaten(){return this.chessPieceEaten;}

    public ArrayList<ChessPiece> getPieceArrayList(int row, int column){return this.game.getTiles()[row][column].getPieceArrayList();}
}
