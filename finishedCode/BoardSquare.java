package chess.finishedCode;

import chess.ChessPiece;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;

public class BoardSquare {

    private final Rectangle boardSquare;
    private Color color;
    private final ArrayList<ChessPiece> pieceArrayList;

    public BoardSquare(Pane gamePane, Color color, int row, int column) {
        this.boardSquare = new Rectangle(column * 80, row * 80, 80, 80);
        this.color = color;
        this.pieceArrayList = new ArrayList<>();

        gamePane.getChildren().add(this.boardSquare);
        this.boardSquare.setStroke(Color.BLACK);

        this.changeColor(color);
    }

    public void changeColor(Color color) {
        this.color = color;
        this.boardSquare.setFill(this.color);
        this.boardSquare.setOpacity(0.5);
    }

    public void addPiece(ChessPiece chessPiece) {this.pieceArrayList.add(chessPiece);}

    public Color getColor(){
        return this.color;
    }

    public ArrayList<ChessPiece> getPieceArrayList() {return this.pieceArrayList;}

    public void replacePieceArrayList(ArrayList<ChessPiece> newPieceArrayList) {
        this.pieceArrayList.clear();
        this.pieceArrayList.addAll(newPieceArrayList);
    }
}
