package chess;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Game {

    private Pane gamePane;
    private BoardSquare[][] tiles;
    private Timeline timeline;

    public Game(Pane gamePane){
        this.gamePane = gamePane;
        this.makeBoard();
        this.initializeBoard();
        this.setUpMainLine();
    }

    public void makeBoard(){
        this.tiles = new BoardSquare[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 == 0) {
                    this.tiles[row][col] = new BoardSquare(this.gamePane, Color.BROWN, row, col);
                } else {
                    this.tiles[row][col] = new BoardSquare(this.gamePane, Color.WHITE, row, col);
                }
            }
        }
    }

    public void initializeBoard(){
        for (int column = 0; column < 8; column++){
            Piece pawn = new Pawn(this.gamePane, this, column, 6, Color.WHITE);
            this.tiles[6][column].addPiece(pawn);
        }
        for (int column = 0; column < 8; column++){
            Piece pawn = new Pawn(this.gamePane, this, column, 1, Color.BLACK);
            this.tiles[1][column].addPiece(pawn);
        }

        Piece whiteRook1 = new Rook(this.gamePane, 0, 7, Color.WHITE);
        this.tiles[7][0].addPiece(whiteRook1);
        Piece whiteRook2 = new Rook(this.gamePane, 7, 7, Color.WHITE);
        this.tiles[7][7].addPiece(whiteRook2);
        Piece blackRook1 = new Rook(this.gamePane, 7, 0, Color.BLACK);
        this.tiles[0][7].addPiece(blackRook1);
        Piece blackRook2 = new Rook(this.gamePane, 0, 0, Color.BLACK);
        this.tiles[0][0].addPiece(blackRook2);
    }

    private void setUpMainLine(){
        KeyFrame kf = new KeyFrame(Duration.seconds(0.1), (ActionEvent timeline) -> this.timelineActions());
        this.timeline = new Timeline(kf);
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();
    }

    public void timelineActions(){
        this.movePieceOnPressed();
    }

    private void movePieceOnPressed() {
        this.gamePane.setOnMouseClicked((MouseEvent mouseClicked) -> this.movePiece(mouseClicked));
        this.gamePane.setFocusTraversable(true);
    }

    private void movePiece(MouseEvent mouseClicked){
        int clickRow = (int) mouseClicked.getSceneY() / 80;
        int clickColumn = (int) mouseClicked.getSceneX() / 80;
        try{
            Piece pieceClicked = this.tiles[clickRow][clickColumn].getPieceArrayList().get(0);
            pieceClicked.move();
        } catch (IndexOutOfBoundsException e){
            System.out.println("No piece exists!");
        }
    }

    public boolean checkCanMove(int startRow, int startColumn, int rowOffset, int columnOffset){
        // add constraints
        if (this.tiles[startRow + rowOffset][startColumn + columnOffset].getPieceArrayList().size() == 0){
            return true;
        }
        return false;
    }

    public boolean checkCanEat(int startRow, int startColumn, int rowOffset, int columnOffset, Color color){
        if (this.tiles[startRow + rowOffset][startColumn + columnOffset].getPieceArrayList().size() == 1 && this.tiles[startRow + rowOffset][startColumn + columnOffset].getPieceArrayList().get(0).getColor() == Color.BLACK){
            return true;
        }
        return false;
    }

    public void changeColor(int row, int column){
        this.getTiles()[row][column].changeColor(Color.GREEN);
    }

    public BoardSquare[][] getTiles(){
        return this.tiles;
    }
}
