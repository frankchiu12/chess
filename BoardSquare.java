package chess;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class BoardSquare {

    private Pane gamePane;
    private Rectangle boardSquare;
    private Color color;
    private ArrayList<Piece> pieceArrayList;

    public BoardSquare(Pane gamePane, Color color, int row, int column){
        this.boardSquare = new Rectangle(column * 80, row * 80, 80, 80);
        this.gamePane = gamePane;
        this.color = color;
        this.pieceArrayList = new ArrayList<>();
        this.changeColor(color);
        gamePane.getChildren().add(boardSquare);
        this.boardSquare.setStroke(Color.BLACK);
    }

    public void addPiece(Piece piece){
        this.pieceArrayList.add(piece);
    }

    public void changeColor(Color color){
        this.color = color;
        this.boardSquare.setFill(this.color);
        this.boardSquare.setOpacity(0.5);
    }

    public Color getColor(){
        return this.color;
    }

    public ArrayList<Piece> getPieceArrayList(){
        return this.pieceArrayList;
    }
}
