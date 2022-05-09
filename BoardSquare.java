package chess;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class BoardSquare {

    private Pane gamePane;
    private ArrayList<Piece> pieceArrayList;

    public BoardSquare(Pane gamePane, Color color, int row, int column){
        Rectangle boardSquare = new Rectangle(column * 80, row * 80, 80, 80);
        this.gamePane = gamePane;
        this.pieceArrayList = new ArrayList<>();
        boardSquare.setFill(color);
        boardSquare.setOpacity(0.5);
        gamePane.getChildren().add(boardSquare);
    }

    public void addPiece(Piece piece){
        this.pieceArrayList.add(piece);
    }

    public ArrayList<Piece> getPieceArrayList(){
        return this.pieceArrayList;
    }
}
