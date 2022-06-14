package chess;

import chess.finishedCode.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import java.util.*;

public class Game {

    private final Pane gamePane;
    private BoardSquare[][] tiles;
    private ChessPiece checkPiece;
    private int previousClickRow;
    private int previousClickColumn;
    private final Stack<Move> reverseStack;
    private boolean isCheck;
    private Color checkedKingColor;
    private PlayerColor playerColor;
    private final Label errorMessageLabel;
    private final Label pawnPromotionLabel;
    private final TextField textField;
    private final Button submitButton;

    public Game(Pane gamePane){
        // TODO: save
        this.gamePane = gamePane;
        this.playerColor = PlayerColor.WHITE;
        this.reverseStack = new Stack<>();

        this.errorMessageLabel = new Label("Error messages are displayed here!");
        this.errorMessageLabel.setPrefWidth(200);
        this.errorMessageLabel.setTranslateX((880-640)/2 * -1 + 200/2);
        this.errorMessageLabel.setAlignment(Pos.CENTER);

        this.pawnPromotionLabel = new Label("Chess Piece: ");
        this.pawnPromotionLabel.setPrefWidth(200);
        this.pawnPromotionLabel.setTranslateX((880-640)/2 * -1 + 200/2);
        this.pawnPromotionLabel.setAlignment(Pos.CENTER);

        this.textField = new TextField();
        this.textField.setPrefWidth(200);
        this.textField.setTranslateX((880-640)/2 * -1 + 200/2);

        this.submitButton = new Button("Submit");
        this.submitButton.setOnAction((ActionEvent e) -> this.getPawnPromotionPiece());
        this.submitButton.setPrefWidth(100);
        this.submitButton.setTranslateX((880-640)/2 * -1 + 100);
        this.submitButton.setAlignment(Pos.CENTER);
        this.submitButton.setFocusTraversable(false);

        this.makeBoard();
        this.initializeBoard();
        this.setUpMainLine();
    }

    public void makeBoard(){
        this.tiles = new BoardSquare[8][8];
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if ((row + column) % 2 == 0) {
                    this.tiles[row][column] = new BoardSquare(this.gamePane, Color.WHITE, row, column);
                } else {
                    this.tiles[row][column] = new BoardSquare(this.gamePane, Color.BROWN, row, column);
                }
            }
        }
    }

    public void initializeBoard(){
        for (int column = 0; column < 8; column++){
            ChessPiece pawn = new Pawn(this.gamePane, this, column, 6, Color.WHITE, "chess/png/whitePawn.png");
            this.tiles[6][column].addPiece(pawn);
        }
        for (int column = 0; column < 8; column++){
            ChessPiece pawn = new Pawn(this.gamePane, this, column, 1, Color.BLACK, "chess/png/blackPawn.png");
            this.tiles[1][column].addPiece(pawn);
        }

        ChessPiece whiteRook1 = new Rook(this.gamePane, this,0, 7, Color.WHITE, "chess/png/whiteRook.png");
        this.tiles[7][0].addPiece(whiteRook1);
        ChessPiece whiteRook2 = new Rook(this.gamePane, this, 7, 7, Color.WHITE, "chess/png/whiteRook.png");
        this.tiles[7][7].addPiece(whiteRook2);
        ChessPiece blackRook1 = new Rook(this.gamePane, this,7, 0, Color.BLACK, "chess/png/blackRook.png");
        this.tiles[0][7].addPiece(blackRook1);
        ChessPiece blackRook2 = new Rook(this.gamePane, this, 0, 0, Color.BLACK, "chess/png/blackRook.png");
        this.tiles[0][0].addPiece(blackRook2);
        ChessPiece whiteBishop1 = new Bishop(this.gamePane, this, 2, 7, Color.WHITE, "chess/png/whiteBishop.png");
        this.tiles[7][2].addPiece(whiteBishop1);
        ChessPiece whiteBishop2 = new Bishop(this.gamePane, this, 5, 7, Color.WHITE, "chess/png/whiteBishop.png");
        this.tiles[7][5].addPiece(whiteBishop2);
        ChessPiece blackBishop1 = new Bishop(this.gamePane, this, 2, 0, Color.BLACK, "chess/png/blackBishop.png");
        this.tiles[0][2].addPiece(blackBishop1);
        ChessPiece blackBishop2 = new Bishop(this.gamePane, this, 5, 0, Color.BLACK, "chess/png/blackBishop.png");
        this.tiles[0][5].addPiece(blackBishop2);
        ChessPiece whiteQueen = new Queen(this.gamePane, this, 3, 7, Color.WHITE, "chess/png/whiteQueen.png");
        this.tiles[7][3].addPiece(whiteQueen);
        ChessPiece blackQueen = new Queen(this.gamePane, this, 3, 0, Color.BLACK, "chess/png/blackQueen.png");
        this.tiles[0][3].addPiece(blackQueen);
        ChessPiece whiteKing = new King(this.gamePane, this, 4, 7, Color.WHITE, "chess/png/whiteKing.png");
        this.tiles[7][4].addPiece(whiteKing);
        ChessPiece blackKing = new King(this.gamePane, this, 4, 0, Color.BLACK, "chess/png/blackKing.png");
        this.tiles[0][4].addPiece(blackKing);
        ChessPiece whiteKnight1 = new Knight(this.gamePane, this, 1, 7, Color.WHITE, "chess/png/whiteKnight.png");
        this.tiles[7][1].addPiece(whiteKnight1);
        ChessPiece whiteKnight2 = new Knight(this.gamePane, this, 6, 7, Color.WHITE, "chess/png/whiteKnight.png");
        this.tiles[7][6].addPiece(whiteKnight2);
        ChessPiece blackKnight1 = new Knight(this.gamePane, this, 1, 0, Color.BLACK, "chess/png/blackKnight.png");
        this.tiles[0][1].addPiece(blackKnight1);
        ChessPiece blackKnight2 = new Knight(this.gamePane, this, 6, 0, Color.BLACK, "chess/png/blackKnight.png");
        this.tiles[0][6].addPiece(blackKnight2);
    }

    private void clearBoard(){
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 == 0) {
                    this.tiles[row][col].changeColor(Color.WHITE);
                } else {
                    this.tiles[row][col].changeColor(Color.BROWN);
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
        this.handleKeyPress();
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
            boolean isRedKingMove = tileColor == Color.RED && this.tilePieceArrayList(clickRow, clickColumn).get(0) instanceof King;
            if (tileColor == Color.BROWN || tileColor == Color.WHITE || isRedKingMove) {
                this.clearBoard();
                ChessPiece pieceClicked = this.tilePieceArrayList(clickRow, clickColumn).get(0);
                if (this.playerColor.isRightTurn(pieceClicked.getColor())){
                    pieceClicked.getPossibleMoves();
                } else {
                    this.errorMessageLabel.setText("Please select a " + this.playerColor.getOppositeColorString() + " piece!");
                }
            }
            if (tileColor == Color.GREEN || tileColor == Color.RED){
                ChessPiece pieceClicked = this.tilePieceArrayList(this.previousClickRow, this.previousClickColumn).get(0);
                if (this.playerColor.isRightTurn(pieceClicked.getColor())){
                    if (this.tilePieceArrayList(clickRow, clickColumn).size() != 0){
                        this.tilePieceArrayList(clickRow, clickColumn).get(0).removeImage();
                    }
                    this.tilePieceArrayList(this.previousClickRow, this.previousClickColumn).clear();
                    pieceClicked.move(clickRow, clickColumn);
                    this.errorMessageLabel.setText("No errors!");
                    this.reverseStack.add(new Move(pieceClicked, new Coordinate<>(this.previousClickRow, this.previousClickColumn), new Coordinate<>(clickRow, clickColumn), pieceClicked.getChessPieceEaten()));
                    this.clearBoard();
                    if (this.checkPawnPromotion() != null){
                        this.promotePawn();
                    }
                    if (this.underCheck()){
                        this.clearBoard();
                        if (this.searchForCheckMate()){
                            this.errorMessageLabel.setText("CHECKMATE!");
                        } else {
                            this.errorMessageLabel.setText("CHECK!");
                        }
                        this.checkPiece.getCheckMove();
                        if (this.checkedKingColor == this.playerColor.convertPlayerColorToColor()) {
                            this.errorMessageLabel.setText("Cannot move to check yourself!");
                        }
                        this.isCheck = true;
                    } else {
                        this.clearBoard();
                        this.isCheck = false;
                    }
                    this.rotate();
                    this.playerColor = this.playerColor.getOppositePlayerColor();
                } else {
                    this.errorMessageLabel.setText("Please select a " + this.playerColor.getOppositeColorString() + " piece!");
                }
            }
        } catch (IndexOutOfBoundsException e){
            this.errorMessageLabel.setText("No piece exists/invalid move!");
        } catch (InterruptedException ignored){
        }
        this.previousClickRow = clickRow;
        this.previousClickColumn = clickColumn;
    }

    public boolean checkCanMove(int startRow, int startColumn, int rowOffset, int columnOffset){
        try {
            int checkRow = startRow + rowOffset;
            int checkColumn = startColumn + columnOffset;
            return this.tilePieceArrayList(checkRow, checkColumn).size() == 0;
        }
        catch (IndexOutOfBoundsException e){
            return false;
        }
    }

    public boolean checkCanEat(int startRow, int startColumn, int rowOffset, int columnOffset, Color color){
        try {
            int checkRow = startRow + rowOffset;
            int checkColumn = startColumn + columnOffset;
            return this.tilePieceArrayList(checkRow, checkColumn).size() == 1 && this.tilePieceArrayList(checkRow, checkColumn).get(0).getColor() == this.getOppositeColor(color);
        }
        catch (IndexOutOfBoundsException e){
            return false;
        }
    }

    public boolean underCheck(){
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (this.tiles[row][column].getPieceArrayList().size() != 0){
                    this.tiles[row][column].getPieceArrayList().get(0).getPossibleMoves();
                    for (int r = 0; r < 8; r++) {
                        for (int c = 0; c < 8; c++) {
                            if (this.tiles[r][c].getColor() == Color.RED){
                                if (this.tiles[r][c].getPieceArrayList().get(0) instanceof King){
                                    this.checkPiece = this.tiles[row][column].getPieceArrayList().get(0);
                                    this.checkedKingColor = this.tiles[r][c].getPieceArrayList().get(0).getColor();
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

    public boolean searchForCheckMate(){
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (this.tiles[row][column].getPieceArrayList().size() != 0 && this.tiles[row][column].getPieceArrayList().get(0).getColor() != this.playerColor.convertPlayerColorToColor()){
                    ArrayList<Coordinate<Integer, Integer>> coordinateArrayList = new ArrayList<>();
                    this.tiles[row][column].getPieceArrayList().get(0).overallMovement(coordinateArrayList);
                    for (Coordinate<Integer, Integer> coordinate : coordinateArrayList) {
                        this.tiles[row][column].getPieceArrayList().get(0).simulateMove(row, column, coordinate.getRow(), coordinate.getC());
                        if (!this.underCheck()) {
                            this.clearBoard();
                            this.tiles[coordinate.getRow()][coordinate.getC()].getPieceArrayList().get(0).reverseMove(row, column, coordinate.getRow(), coordinate.getC(), this.tiles[coordinate.getRow()][coordinate.getC()].getPieceArrayList().get(0).getChessPieceEaten());
                            return false;
                        } else {
                            this.clearBoard();
                            this.tiles[coordinate.getRow()][coordinate.getC()].getPieceArrayList().get(0).reverseMove(row, column, coordinate.getRow(), coordinate.getC(), this.tiles[coordinate.getRow()][coordinate.getC()].getPieceArrayList().get(0).getChessPieceEaten());
                        }
                    }
                }
            }
        }
        return true;
    }

    private void handleKeyPress() {
        this.gamePane.setOnKeyPressed(this::keyPress);
        this.gamePane.setFocusTraversable(true);
    }

    private void keyPress(KeyEvent keyPress){
        KeyCode keyPressed = keyPress.getCode();
        if (keyPressed == KeyCode.R) {
            this.clearBoard();
            this.reverseMove();
        }
    }

    public boolean canLeftCastle(){
        try {
            if (this.isCheck){
                return false;
            }
            if (this.playerColor.convertPlayerColorToColor() == Color.WHITE){
                if (!(this.tiles[7][4].getPieceArrayList().get(0) instanceof King)){
                    return false;
                }
                if (this.tiles[7][1].getPieceArrayList().size() != 0 || this.tiles[7][2].getPieceArrayList().size() != 0 || this.tiles[7][3].getPieceArrayList().size() != 0){
                    return false;
                }
                if (this.tiles[7][0].getPieceArrayList().size() == 0 || !(this.tiles[7][0].getPieceArrayList().get(0) instanceof Rook)){
                    return false;
                }
            }
            if (this.playerColor.convertPlayerColorToColor() == Color.BLACK){
                if (!(this.tiles[7][3].getPieceArrayList().get(0) instanceof King)) {
                    return false;
                }
                if (this.tiles[7][1].getPieceArrayList().size() != 0 || this.tiles[7][2].getPieceArrayList().size() != 0){
                    return false;
                }
                if (this.tiles[7][0].getPieceArrayList().size() == 0 || !(this.tiles[7][0].getPieceArrayList().get(0) instanceof Rook)){
                    return false;
                }
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        return true;
    }

    public boolean canRightCastle(){
        try {
            if (this.isCheck){
                return false;
            }
            if (this.playerColor.convertPlayerColorToColor() == Color.WHITE){
                if (!(this.tiles[7][4].getPieceArrayList().get(0) instanceof King)){
                    return false;
                }
                if (this.tiles[7][5].getPieceArrayList().size() != 0 || this.tiles[7][6].getPieceArrayList().size() != 0){
                    return false;
                }
                if (this.tiles[7][7].getPieceArrayList().size() == 0 || !(this.tiles[7][7].getPieceArrayList().get(0) instanceof Rook)){
                    return false;
                }
            }
            if (this.playerColor.convertPlayerColorToColor() == Color.BLACK){
                if (!(this.tiles[7][3].getPieceArrayList().get(0) instanceof King)){
                    return false;
                }
                if (this.tiles[7][4].getPieceArrayList().size() != 0 || this.tiles[7][5].getPieceArrayList().size() != 0 || this.tiles[7][6].getPieceArrayList().size() != 0){
                    return false;
                }
                if (this.tiles[7][7].getPieceArrayList().size() == 0 || !(this.tiles[7][7].getPieceArrayList().get(0) instanceof Rook)){
                    return false;
                }
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        return true;
    }

    private void rotate(){
        // rotate gamePane
        Rotate rotate = new Rotate();
        rotate.setAngle(180);
        rotate.setPivotX(320);
        rotate.setPivotY(320);
        this.gamePane.getTransforms().add(rotate);

        BoardSquare[][] tilesCopy = new BoardSquare[8][8];
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                tilesCopy[row][column] = new BoardSquare(this.gamePane, Color.TRANSPARENT, row, column);
                tilesCopy[row][column].replacePieceArrayList(new ArrayList<>(this.tiles[row][column].getPieceArrayList()));
            }
        }
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                this.tiles[row][column].replacePieceArrayList(tilesCopy[7 - row][7 - column].getPieceArrayList());
                if (this.tiles[row][column].getPieceArrayList().size() != 0){
                    this.tiles[row][column].getPieceArrayList().get(0).setRow(7 - this.tiles[row][column].getPieceArrayList().get(0).getRow());
                    this.tiles[row][column].getPieceArrayList().get(0).setColumn(7 - this.tiles[row][column].getPieceArrayList().get(0).getColumn());
                }
            }
        }
        this.gamePane.getTransforms().add(rotate);
    }

    private void reverseMove(){
        try{
            Move moveToReverse = this.reverseStack.pop();
            ChessPiece currentChessPiece = moveToReverse.getCurrentPiece();
            this.rotate();
            currentChessPiece.reverseMove(moveToReverse.getFromCoordinate().getRow(), moveToReverse.getFromCoordinate().getC(), moveToReverse.getToCoordinate().getRow(), moveToReverse.getToCoordinate().getC(), moveToReverse.getEatenChessPiece());
            this.playerColor = this.playerColor.getOppositePlayerColor();
        } catch (EmptyStackException e) {
            this.errorMessageLabel.setText("No moves to reverse!");
        }
    }

    public String getPawnPromotionPiece(){
        return this.textField.getText();
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

    public ArrayList<ChessPiece> tilePieceArrayList(int row, int column){return this.tiles[row][column].getPieceArrayList();}

    public boolean getCanLeftCastle(){
        return this.canLeftCastle();
    }

    public boolean getCanRightCastle(){return this.canRightCastle();}

    public ChessPiece checkPawnPromotion(){
        for (int column = 0; column < 8; column++){
            if (this.tiles[0][column].getPieceArrayList().size() != 0 && this.tiles[0][column].getPieceArrayList().get(0) instanceof Pawn){
                return this.tiles[0][column].getPieceArrayList().get(0);
            }
        }
        return null;
    }

    public void promotePawn() throws InterruptedException {
        String pawnPromotionPieceText = this.getPawnPromotionPiece();
        if (Objects.equals(pawnPromotionPieceText, "Queen") || pawnPromotionPieceText.isEmpty()){
            this.checkPawnPromotion().removeImage();
            int pawnRow = this.checkPawnPromotion().getRow();
            int pawnColumn = this.checkPawnPromotion().getColumn();
            this.tilePieceArrayList(pawnRow, pawnColumn).clear();
            ChessPiece queen = null;
            if (this.playerColor.convertPlayerColorToColor() == Color.WHITE){
                queen = new Queen(this.gamePane, this, pawnColumn, pawnRow, this.playerColor.convertPlayerColorToColor(), "chess/png/whiteQueen.png");
            }
            if (this.playerColor.convertPlayerColorToColor() == Color.BLACK){
                queen = new Queen(this.gamePane, this, pawnColumn, pawnRow, this.playerColor.convertPlayerColorToColor(), "chess/png/blackQueen.png");
            }
            this.tiles[pawnRow][pawnColumn].addPiece(queen);
        }
        if (Objects.equals(pawnPromotionPieceText, "Rook")){
            this.checkPawnPromotion().removeImage();
            int pawnRow = this.checkPawnPromotion().getRow();
            int pawnColumn = this.checkPawnPromotion().getColumn();
            this.tilePieceArrayList(pawnRow, pawnColumn).clear();
            ChessPiece rook = null;
            if (this.playerColor.convertPlayerColorToColor() == Color.WHITE){
                rook = new Rook(this.gamePane, this, pawnColumn, pawnRow, this.playerColor.convertPlayerColorToColor(), "chess/png/whiteRook.png");
            }
            if (this.playerColor.convertPlayerColorToColor() == Color.BLACK){
                rook = new Rook(this.gamePane, this, pawnColumn, pawnRow, this.playerColor.convertPlayerColorToColor(), "chess/png/blackRook.png");
            }
            this.tiles[pawnRow][pawnColumn].addPiece(rook);
        }
        if (Objects.equals(pawnPromotionPieceText, "Bishop")){
            this.checkPawnPromotion().removeImage();
            int pawnRow = this.checkPawnPromotion().getRow();
            int pawnColumn = this.checkPawnPromotion().getColumn();
            this.tilePieceArrayList(pawnRow, pawnColumn).clear();
            ChessPiece bishop = null;
            if (this.playerColor.convertPlayerColorToColor() == Color.WHITE){
                bishop = new Bishop(this.gamePane, this, pawnColumn, pawnRow, this.playerColor.convertPlayerColorToColor(), "chess/png/whiteBishop.png");
            }
            if (this.playerColor.convertPlayerColorToColor() == Color.BLACK){
                bishop = new Bishop(this.gamePane, this, pawnColumn, pawnRow, this.playerColor.convertPlayerColorToColor(), "chess/png/blackBishop.png");
            }
            this.tiles[pawnRow][pawnColumn].addPiece(bishop);
        }
        if (Objects.equals(pawnPromotionPieceText, "Knight")){
            this.checkPawnPromotion().removeImage();
            int pawnRow = this.checkPawnPromotion().getRow();
            int pawnColumn = this.checkPawnPromotion().getColumn();
            this.tilePieceArrayList(pawnRow, pawnColumn).clear();
            ChessPiece knight = null;
            if (this.playerColor.convertPlayerColorToColor() == Color.WHITE){
                knight = new Knight(this.gamePane, this, pawnColumn, pawnRow, this.playerColor.convertPlayerColorToColor(), "chess/png/whiteKnight.png");
            }
            if (this.playerColor.convertPlayerColorToColor() == Color.BLACK){
                knight = new Knight(this.gamePane, this, pawnColumn, pawnRow, this.playerColor.convertPlayerColorToColor(), "chess/png/blackKnight.png");
            }
            this.tiles[pawnRow][pawnColumn].addPiece(knight);
        }
    }

    public Color getPlayerColor(){
        return this.playerColor.convertPlayerColorToColor();
    }

    public void changeColor(int row, int column, Color color){
        this.tiles[row][column].changeColor(color);
    }

    public BoardSquare[][] getTiles(){
        return this.tiles;
    }

    public Label getErrorMessageLabel(){return this.errorMessageLabel;}

    public Label getPawnPromotionLabel(){return this.pawnPromotionLabel;}

    public TextField getTextField(){return this.textField;}

    public Button getSubmitButton(){return this.submitButton;}
}
