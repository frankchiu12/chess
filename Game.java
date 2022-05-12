package chess;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;

public class Game {

    private final Pane gamePane;
    private BoardSquare[][] tiles;
    private int previousClickRow;
    private int previousClickColumn;

    public Game(Pane gamePane){
        this.gamePane = gamePane;
        this.makeBoard();
        this.initializeBoard();
        this.setUpMainLine();
    }

    public void makeBoard(){
        this.tiles = new BoardSquare[8][8];
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if ((row + column) % 2 == 0) {
                    this.tiles[row][column] = new BoardSquare(this.gamePane, Color.BROWN, row, column);
                } else {
                    this.tiles[row][column] = new BoardSquare(this.gamePane, Color.WHITE, row, column);
                }
            }
        }
    }

    public void initializeBoard(){
        for (int column = 0; column < 8; column++){
            ChessPiece pawn = new Pawn(this.gamePane, this, column, 6, Color.WHITE, "chess/whitePawn.png");
            this.tiles[6][column].addPiece(pawn);
        }
        for (int column = 0; column < 8; column++){
            ChessPiece pawn = new Pawn(this.gamePane, this, column, 1, Color.BLACK, "chess/blackPawn.png");
            this.tiles[1][column].addPiece(pawn);
        }

        ChessPiece whiteRook1 = new Rook(this.gamePane, this,0, 7, Color.WHITE, "chess/whiteRook.png");
        this.tiles[7][0].addPiece(whiteRook1);
        ChessPiece whiteRook2 = new Rook(this.gamePane, this, 7, 7, Color.WHITE, "chess/whiteRook.png");
        this.tiles[7][7].addPiece(whiteRook2);
        ChessPiece blackRook1 = new Rook(this.gamePane, this,7, 0, Color.BLACK, "chess/blackRook.png");
        this.tiles[0][7].addPiece(blackRook1);
        ChessPiece blackRook2 = new Rook(this.gamePane, this, 0, 0, Color.BLACK, "chess/blackRook.png");
        this.tiles[0][0].addPiece(blackRook2);
        ChessPiece whiteBishop1 = new Bishop(this.gamePane, this, 2, 7, Color.WHITE, "chess/whiteBishop.png");
        this.tiles[7][2].addPiece(whiteBishop1);
        ChessPiece whiteBishop2 = new Bishop(this.gamePane, this, 5, 7, Color.WHITE, "chess/whiteBishop.png");
        this.tiles[7][5].addPiece(whiteBishop2);
        ChessPiece blackBishop1 = new Bishop(this.gamePane, this, 2, 0, Color.BLACK, "chess/blackBishop.png");
        this.tiles[0][2].addPiece(blackBishop1);
        ChessPiece blackBishop2 = new Bishop(this.gamePane, this, 5, 0, Color.BLACK, "chess/blackBishop.png");
        this.tiles[0][5].addPiece(blackBishop2);
        ChessPiece whiteQueen = new Queen(this.gamePane, this, 4, 7, Color.WHITE, "chess/whiteQueen.png");
        this.tiles[7][4].addPiece(whiteQueen);
        ChessPiece blackQueen = new Queen(this.gamePane, this, 4, 0, Color.BLACK, "chess/blackQueen.png");
        this.tiles[0][4].addPiece(blackQueen);
        ChessPiece whiteKing = new King(this.gamePane, this, 3, 7, Color.WHITE, "chess/whiteKing.png");
        this.tiles[7][3].addPiece(whiteKing);
        ChessPiece blackKing = new King(this.gamePane, this, 3, 0, Color.BLACK, "chess/blackKing.png");
        this.tiles[0][3].addPiece(blackKing);
    }

    private void clearBoard(){
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 == 0) {
                    this.tiles[row][col].changeColor(Color.BROWN);
                } else {
                    this.tiles[row][col].changeColor(Color.WHITE);
                }
            }
        }
    }

    private void setUpMainLine(){
        KeyFrame kf = new KeyFrame(Duration.seconds(0.1), (ActionEvent timeline) -> this.timelineActions());
        Timeline timeline = new Timeline(kf);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void timelineActions(){
        this.movePieceOnPressed();
    }

    private void movePieceOnPressed() {
        this.gamePane.setOnMouseClicked(this::movePiece);
        this.gamePane.setFocusTraversable(true);
    }

    private void movePiece(MouseEvent mouseClicked){
        int clickRow = (int) mouseClicked.getSceneY() / 80;
        int clickColumn = (int) mouseClicked.getSceneX() / 80;
        Color tileColor = this.tiles[clickRow][clickColumn].getColor();
        try{
            if (tileColor == Color.BROWN || tileColor == Color.WHITE){
                this.clearBoard();
                ChessPiece pieceClicked = this.tilePieceArrayList(clickRow, clickColumn).get(0);
                pieceClicked.getPossibleMoves();
            }
            if (tileColor == Color.GREEN || tileColor == Color.RED){
                ChessPiece pieceClicked = this.tilePieceArrayList(this.previousClickRow, this.previousClickColumn).get(0);
                if (this.tilePieceArrayList(clickRow, clickColumn).size() != 0){
                    this.tilePieceArrayList(clickRow, clickColumn).get(0).removeImage();
                }
                this.tilePieceArrayList(this.previousClickRow, this.previousClickColumn).clear();
                pieceClicked.move(clickRow, clickColumn);
                this.clearBoard();
                this.searchForCheck();
                System.out.println(this.searchForCheck());
                this.clearBoard();
            }
        } catch (IndexOutOfBoundsException e){
            if (tileColor == Color.BLACK || tileColor == Color.WHITE) {
                System.out.println("No piece exists!");
            }
            else {
                System.out.println("Move invalid!");
            }
        }
        this.previousClickRow = clickRow;
        this.previousClickColumn = clickColumn;
    }

    public boolean checkCanMove(int startRow, int startColumn, int rowOffset, int columnOffset){
        int checkRow = startRow + rowOffset;
        int checkColumn = startColumn + columnOffset;
        return this.tilePieceArrayList(checkRow, checkColumn).size() == 0;
    }

    public boolean checkCanEat(int startRow, int startColumn, int rowOffset, int columnOffset, Color color){
        int checkRow = startRow + rowOffset;
        int checkColumn = startColumn + columnOffset;
        return this.tilePieceArrayList(checkRow, checkColumn).size() == 1 && this.tilePieceArrayList(checkRow, checkColumn).get(0).getColor() == this.getOppositeColor(color);
    }

    public boolean searchForCheck(){
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (this.tiles[row][column].getPieceArrayList().size() != 0){
                    this.tiles[row][column].getPieceArrayList().get(0).getPossibleMoves();
                    for (int r = 0; r < 8; r++) {
                        for (int c = 0; c < 8; c++) {
                            if (this.tiles[r][c].getColor() == Color.RED && this.tiles[r][c].getPieceArrayList().size() != 0){
                                if (this.tiles[r][c].getPieceArrayList().get(0) instanceof King){
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public Color getOppositeColor(Color color){
        Color colorToReturn = null;
        if (color == Color.WHITE){
            colorToReturn = Color.BLACK;
        }
        if (color == Color.BLACK){
            colorToReturn = Color.WHITE;
        }
        return colorToReturn;
    }

    public ArrayList<ChessPiece> tilePieceArrayList(int row, int column){
        return this.tiles[row][column].getPieceArrayList();
    }

    public void changeColor(int row, int column, Color color){
        this.tiles[row][column].changeColor(color);
    }

    public BoardSquare[][] getTiles(){
        return this.tiles;
    }
}
