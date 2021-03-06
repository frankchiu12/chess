package chess;

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
    private PlayerColor playerColor;
    private int previousClickRow;
    private int previousClickColumn;

    private boolean isCheck;
    private ChessPiece checkPiece;
    private Color checkedKingColor;

    private ChessPiece whiteKing;
    private ChessPiece blackKing;
    private ChessPiece whiteRook1;
    private ChessPiece whiteRook2;
    private ChessPiece blackRook1;
    private ChessPiece blackRook2;

    private Stack<Move> reverseStack;

    private Timeline timeline;
    private boolean isGameOver;

    private final Label turnLabel;

    private Timeline countDownTimeLine;
    private final Timer whiteTimer;
    private final Timer blackTimer;
    private int whiteMinute;
    private int whiteSecond;
    private int blackMinute;
    private int blackSecond;
    private final Label whiteTimerLabel;
    private final Label blackTimerLabel;

    private final Label errorMessageLabel;
    private final TextField textField;
    private final Button startButton;
    private final Button submitButton;
    private final Button restartButton;

    public Game(Pane gamePane) {
        this.gamePane = gamePane;
        this.playerColor = PlayerColor.WHITE;
        this.reverseStack = new Stack<>();
        this.isGameOver = false;

        this.turnLabel = new Label("white turn");
        this.turnLabel.setPrefWidth(200);
        this.turnLabel.setTranslateX(-20);
        this.turnLabel.setAlignment(Pos.CENTER);

        this.whiteTimer = new Timer();
        this.blackTimer = new Timer();
        this.whiteMinute = 15;
        this.whiteSecond = 0;
        this.blackMinute = 15;
        this.blackSecond = 0;
        this.whiteTimerLabel = new Label(whiteTimer.toString());
        this.whiteTimerLabel.setPrefWidth(200);
        this.whiteTimerLabel.setTranslateX(-20);
        this.whiteTimerLabel.setAlignment(Pos.CENTER);
        this.whiteTimerLabel.setStyle("-fx-background-color: #FFFFFF");
        this.blackTimerLabel = new Label(blackTimer.toString());
        this.blackTimerLabel.setPrefWidth(200);
        this.blackTimerLabel.setTranslateX(-20);
        this.blackTimerLabel.setAlignment(Pos.CENTER);
        this.blackTimerLabel.setStyle("-fx-background-color: #000000");
        this.blackTimerLabel.setTextFill(Color.WHITE);

        this.errorMessageLabel = new Label("Error messages are displayed here!");
        this.errorMessageLabel.setPrefWidth(200);
        this.errorMessageLabel.setTranslateX(-20);
        this.errorMessageLabel.setAlignment(Pos.CENTER);

        this.textField = new TextField();
        this.textField.setPrefWidth(200);
        this.textField.setTranslateX(-20);
        this.textField.setFocusTraversable(false);

        this.startButton = new Button("Start");
        this.startButton.setOnAction((ActionEvent e) -> this.countDownTimeLine.play());
        this.startButton.setPrefWidth(100);
        this.startButton.setTranslateX(-20);
        this.startButton.setAlignment(Pos.CENTER);
        this.startButton.setFocusTraversable(false);

        this.submitButton = new Button("Submit");
        this.submitButton.setOnAction((ActionEvent e) -> this.getPawnPromotionPieceText());
        this.submitButton.setPrefWidth(100);
        this.submitButton.setTranslateX(-20);
        this.submitButton.setAlignment(Pos.CENTER);
        this.submitButton.setFocusTraversable(false);

        this.restartButton = new Button("Restart");
        this.restartButton.setOnAction((ActionEvent e) -> this.restart());
        this.restartButton.setPrefWidth(100);
        this.restartButton.setTranslateX(-20);
        this.restartButton.setAlignment(Pos.CENTER);
        this.restartButton.setFocusTraversable(false);

        this.makeBoard();
        this.initializeBoard();
        this.setUpMainTimeLine();
        this.setUpCountDownTimeLine();
    }

    /**
     * makes the underlying board
     */
    public void makeBoard() {
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

    /**
     * initialize the ChessPieces on the board
     */
    public void initializeBoard() {
        // Pawn
        for (int column = 0; column < 8; column++) {
            ChessPiece pawn = new Pawn(this.gamePane, this, column, 6, Color.WHITE, "chess/chessPiecePNG/whitePawn.png");
            this.tiles[6][column].addPiece(pawn);
        }
        for (int column = 0; column < 8; column++) {
            ChessPiece pawn = new Pawn(this.gamePane, this, column, 1, Color.BLACK, "chess/chessPiecePNG/blackPawn.png");
            this.tiles[1][column].addPiece(pawn);
        }
        // Knight
        ChessPiece whiteKnight1 = new Knight(this.gamePane, this, 1, 7, Color.WHITE, "chess/chessPiecePNG/whiteKnight.png");
        this.tiles[7][1].addPiece(whiteKnight1);
        ChessPiece whiteKnight2 = new Knight(this.gamePane, this, 6, 7, Color.WHITE, "chess/chessPiecePNG/whiteKnight.png");
        this.tiles[7][6].addPiece(whiteKnight2);
        ChessPiece blackKnight1 = new Knight(this.gamePane, this, 1, 0, Color.BLACK, "chess/chessPiecePNG/blackKnight.png");
        this.tiles[0][1].addPiece(blackKnight1);
        ChessPiece blackKnight2 = new Knight(this.gamePane, this, 6, 0, Color.BLACK, "chess/chessPiecePNG/blackKnight.png");
        this.tiles[0][6].addPiece(blackKnight2);
        // Bishop
        ChessPiece whiteBishop1 = new Bishop(this.gamePane, this, 2, 7, Color.WHITE, "chess/chessPiecePNG/whiteBishop.png");
        this.tiles[7][2].addPiece(whiteBishop1);
        ChessPiece whiteBishop2 = new Bishop(this.gamePane, this, 5, 7, Color.WHITE, "chess/chessPiecePNG/whiteBishop.png");
        this.tiles[7][5].addPiece(whiteBishop2);
        ChessPiece blackBishop1 = new Bishop(this.gamePane, this, 2, 0, Color.BLACK, "chess/chessPiecePNG/blackBishop.png");
        this.tiles[0][2].addPiece(blackBishop1);
        ChessPiece blackBishop2 = new Bishop(this.gamePane, this, 5, 0, Color.BLACK, "chess/chessPiecePNG/blackBishop.png");
        this.tiles[0][5].addPiece(blackBishop2);
        // Rook
        this.whiteRook1 = new Rook(this.gamePane, this,0, 7, Color.WHITE, "chess/chessPiecePNG/whiteRook.png");
        this.tiles[7][0].addPiece(this.whiteRook1);
        this.whiteRook2 = new Rook(this.gamePane, this, 7, 7, Color.WHITE, "chess/chessPiecePNG/whiteRook.png");
        this.tiles[7][7].addPiece(this.whiteRook2);
        this.blackRook1 = new Rook(this.gamePane, this,7, 0, Color.BLACK, "chess/chessPiecePNG/blackRook.png");
        this.tiles[0][7].addPiece(this.blackRook1);
        this.blackRook2 = new Rook(this.gamePane, this, 0, 0, Color.BLACK, "chess/chessPiecePNG/blackRook.png");
        this.tiles[0][0].addPiece(this.blackRook2);
        // Queen
        ChessPiece whiteQueen = new Queen(this.gamePane, this, 3, 7, Color.WHITE, "chess/chessPiecePNG/whiteQueen.png");
        this.tiles[7][3].addPiece(whiteQueen);
        ChessPiece blackQueen = new Queen(this.gamePane, this, 3, 0, Color.BLACK, "chess/chessPiecePNG/blackQueen.png");
        this.tiles[0][3].addPiece(blackQueen);
        // King
        this.whiteKing = new King(this.gamePane, this, 4, 7, Color.WHITE, "chess/chessPiecePNG/whiteKing.png");
        this.tiles[7][4].addPiece(this.whiteKing);
        this.blackKing = new King(this.gamePane, this, 4, 0, Color.BLACK, "chess/chessPiecePNG/blackKing.png");
        this.tiles[0][4].addPiece(this.blackKing);
    }

    /**
     * clear the board of highlights
     */
    private void clearBoard() {
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

    /**
     * set up main timeline
     */
    private void setUpMainTimeLine() {
        KeyFrame kf = new KeyFrame(Duration.seconds(0.1), (ActionEvent timeline) -> this.mainTimelineActions());
        this.timeline = new Timeline(kf);
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();
    }

    /**
     * timeline actions that are called on a loop
     */
    private void mainTimelineActions() {
        this.movePieceOnClick();
        this.handleKeyPress();
    }

    /**
     * move ChessPieces when they are clicked
     */
    private void movePieceOnClick() {
        this.gamePane.setOnMouseClicked(this::movePiece);
        this.gamePane.setFocusTraversable(true);
    }

    /**
     * main game logic
     */
    private void movePiece(MouseEvent mouseClicked) {
        // if it is game over, don't execute the method
        if (this.isGameOver) {
            return;
        }
        // if the countdown hasn't started yet, don't execute the method
        if (this.countDownTimeLine.getStatus() == Animation.Status.STOPPED) {
            return;
        }
        // get the row and column of the tile that was clicked
        int clickRow = (int) mouseClicked.getSceneY() / 80;
        int clickColumn = (int) mouseClicked.getSceneX() / 80;
        try {
            // get the color of the tile that was clicked
            Color tileColor = this.tiles[clickRow][clickColumn].getColor();
            boolean isRedKingMove = tileColor == Color.RED && this.tilePieceArrayList(clickRow, clickColumn).get(0) instanceof King;
            // if the tile color is brown, white, or a King under check
            if (tileColor == Color.BROWN || tileColor == Color.WHITE || isRedKingMove) {
                this.clearBoard();
                // get the ChessPiece that was clicked
                ChessPiece pieceClicked = this.tilePieceArrayList(clickRow, clickColumn).get(0);
                // if the turn is correct
                if (this.playerColor.isRightTurn(pieceClicked.getColor())) {
                    // get the possible moves of the ChessPiece that was clicked
                    pieceClicked.getPossibleMoves();
                } else {
                    this.errorMessageLabel.setText("Please select a " + this.playerColor.getColorString() + " piece!");
                }
            }
            // if the tile color is green or red
            else if (tileColor == Color.GREEN || tileColor == Color.RED) {
                // get the ChessPiece that was clicked previously (before the click for the move)
                ChessPiece pieceClicked = this.tilePieceArrayList(this.previousClickRow, this.previousClickColumn).get(0);
                // if the turn is correct
                if (this.playerColor.isRightTurn(pieceClicked.getColor())) {
                    // set this.chessPieceEaten
                    pieceClicked.setChessPieceEaten(clickRow, clickColumn);
                    // add the move to the reverseStack
                    this.reverseStack.add(new Move(pieceClicked, new Coordinate<>(this.previousClickRow, this.previousClickColumn), new Coordinate<>(clickRow, clickColumn), pieceClicked.getChessPieceEaten(), null, 0, 0, this.playerColor, false));
                    // adding castling to the reverseStack
                    if (this.canLeftCastle()) {
                        if (pieceClicked.equals(this.whiteKing) && clickRow == 7 && clickColumn == 2) {
                            this.reverseStack.pop();
                            this.reverseStack.add(new Move(pieceClicked, new Coordinate<>(this.previousClickRow, this.previousClickColumn), new Coordinate<>(clickRow, clickColumn), null, this.whiteRook1, 7, 0, this.playerColor, false));
                        }
                        if (pieceClicked.equals(this.blackKing) && clickRow == 7 && clickColumn == 1) {
                            this.reverseStack.pop();
                            this.reverseStack.add(new Move(pieceClicked, new Coordinate<>(this.previousClickRow, this.previousClickColumn), new Coordinate<>(clickRow, clickColumn), null, this.blackRook1, 7, 0, this.playerColor, false));
                        }
                    }
                    if (this.canRightCastle()) {
                        if (pieceClicked.equals(this.whiteKing) && clickRow == 7 && clickColumn == 6) {
                            this.reverseStack.pop();
                            this.reverseStack.add(new Move(pieceClicked, new Coordinate<>(this.previousClickRow, this.previousClickColumn), new Coordinate<>(clickRow, clickColumn), null, this.whiteRook2, 7, 7, this.playerColor, false));
                        }
                        if (pieceClicked.equals(this.blackKing) && clickRow == 7 && clickColumn == 5) {
                            this.reverseStack.pop();
                            this.reverseStack.add(new Move(pieceClicked, new Coordinate<>(this.previousClickRow, this.previousClickColumn), new Coordinate<>(clickRow, clickColumn), null, this.blackRook2, 7, 7, this.playerColor, false));
                        }
                    }
                    // if there is a ChessPiece being eaten
                    if (this.tilePieceArrayList(clickRow, clickColumn).size() != 0) {
                        // remove the image of the ChessPiece being eaten
                        this.tilePieceArrayList(clickRow, clickColumn).get(0).removeImage();
                    }
                    pieceClicked.move(clickRow, clickColumn);
                    // clear the pieceArrayList of the ChessPiece that was clicked previously
                    this.tilePieceArrayList(this.previousClickRow, this.previousClickColumn).clear();
                    this.clearBoard();
                    this.errorMessageLabel.setText("No errors!");
                    // if there is a Pawn promotion
                    if (this.getPawnPromotionPiece() != null) {
                        this.promotePawn(pieceClicked, clickRow, clickColumn);
                    }
                    // if there is a check
                    if (this.underCheck(false)) {
                        this.clearBoard();
                        // if there is a checkmate
                        if (this.underCheckMate()) {
                            this.errorMessageLabel.setText("CHECKMATE! " + this.playerColor.getColorString() + " won!");
                            this.isGameOver = true;
                            this.countDownTimeLine.stop();
                            this.timeline.stop();
                        } else {
                            this.errorMessageLabel.setText("CHECK!");
                        }
                        this.checkPiece.getCheckMove();
                        // if there is a move by a player that checks themselves
                        if (this.checkedKingColor == this.playerColor.convertPlayerColorToColor()) {
                            this.errorMessageLabel.setText("Illegal move! " + this.playerColor.getOppositePlayerColor().getColorString() + " won!");
                            this.isGameOver = true;
                            this.countDownTimeLine.stop();
                            this.timeline.stop();
                        }
                        this.isCheck = true;
                    } else {
                        this.clearBoard();
                        this.isCheck = false;
                    }
                    this.rotate();
                    this.playerColor = this.playerColor.getOppositePlayerColor();
                    this.turnLabel.setText(this.playerColor.getColorString() + " turn");
                } else {
                    this.errorMessageLabel.setText("Please select a " + this.playerColor.getColorString() + " piece!");
                }
            }
        } catch (IndexOutOfBoundsException e) {
            this.errorMessageLabel.setText("No piece exists/invalid move!");
        } catch (InterruptedException ignored) {
        }
        this.previousClickRow = clickRow;
        this.previousClickColumn = clickColumn;
    }

    /**
     * check whether a move is valid
     */
    public boolean checkCanMove(int startRow, int startColumn, int rowOffset, int columnOffset) {
        try {
            int checkRow = startRow + rowOffset;
            int checkColumn = startColumn + columnOffset;
            return this.tilePieceArrayList(checkRow, checkColumn).size() == 0;
        }
        catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    /**
     * check whether you could eat a ChessPiece of the opponent
     */
    public boolean checkCanEat(int startRow, int startColumn, int rowOffset, int columnOffset, Color color) {
        try {
            int checkRow = startRow + rowOffset;
            int checkColumn = startColumn + columnOffset;
            return this.tilePieceArrayList(checkRow, checkColumn).size() != 0 && this.tilePieceArrayList(checkRow, checkColumn).get(0).getColor() == this.getOppositeColor(color);
        }
        catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    /**
     * check whether a player is under check
     */
    public boolean underCheck(boolean hasChecked) {
        // for all the rows and columns
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                // if there is a ChessPiece
                if (this.tilePieceArrayList(row, column).size() != 0) {
                    // get their possible moves
                    this.tilePieceArrayList(row, column).get(0).getPossibleMoves();
                    // of those possible moves
                    for (int r = 0; r < 8; r++) {
                        for (int c = 0; c < 8; c++) {
                            // if a King is highlighted as red
                            if (this.tiles[r][c].getColor() == Color.RED && this.tilePieceArrayList(r, c).get(0) instanceof King) {
                                if (!hasChecked) {
                                    this.checkPiece = this.tilePieceArrayList(row, column).get(0);
                                    this.checkedKingColor = this.tilePieceArrayList(r, c).get(0).getColor();
                                }
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * check whether there is a checkmate
     */
    public boolean underCheckMate() {
        // for all the rows and columns
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                // if there is a ChessPiece of the opponent's color
                if (this.tilePieceArrayList(row, column).size() != 0 && this.tilePieceArrayList(row, column).get(0).getColor() != this.playerColor.convertPlayerColorToColor()) {
                    // get their possible moves
                    ArrayList<Coordinate<Integer, Integer>> coordinateArrayList = new ArrayList<>();
                    this.tilePieceArrayList(row, column).get(0).overallMovement(coordinateArrayList);
                    // loop through their possible moves
                    for (Coordinate<Integer, Integer> coordinate : coordinateArrayList) {
                        // simulate the move
                        this.tilePieceArrayList(row, column).get(0).simulateMove(row, column, coordinate.getRow(), coordinate.getC());
                        // if it takes the opponent out of check, it is not a checkmate
                        if (!this.underCheck(true)) {
                            this.clearBoard();
                            this.tilePieceArrayList(coordinate.getRow(), coordinate.getC()).get(0).reverseMove(row, column, coordinate.getRow(), coordinate.getC(), this.tilePieceArrayList(coordinate.getRow(), coordinate.getC()).get(0).getChessPieceEaten(), null, 0, 0, this.playerColor, false);
                            return false;
                        } else {
                            this.clearBoard();
                            this.tilePieceArrayList(coordinate.getRow(), coordinate.getC()).get(0).reverseMove(row, column, coordinate.getRow(), coordinate.getC(), this.tilePieceArrayList(coordinate.getRow(), coordinate.getC()).get(0).getChessPieceEaten(), null, 0 ,0, this.playerColor, false);
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * check whether you could castle on the left
     */
    public boolean canLeftCastle() {
        try {
            // if the King is under check
            if (this.isCheck) {
                return false;
            }
            if (this.playerColor.convertPlayerColorToColor() == Color.WHITE) {
                // if the King isn't in the correct position
                if (!(this.tilePieceArrayList(7, 4).get(0) instanceof King)) {
                    return false;
                }
                // if there is a ChessPiece between the King and the Rook
                if (this.tilePieceArrayList(7, 1).size() != 0 || this.tilePieceArrayList(7, 2).size() != 0 || this.tilePieceArrayList(7, 3).size() != 0) {
                    return false;
                }
                // if there isn't a Rook in the right position
                if (this.tilePieceArrayList(7, 0).size() == 0 || !(this.tilePieceArrayList(7, 0).get(0).equals(this.whiteRook1))) {
                    return false;
                }
                // if the King has moved
                if (this.whiteKing.getHasMoved()) {
                    return false;
                }
                // if the Rook has moved
                if (this.whiteRook1.getHasMoved()) {
                    return false;
                }
            }
            if (this.playerColor.convertPlayerColorToColor() == Color.BLACK) {
                // if the King isn't in the correct position
                if (!(this.tilePieceArrayList(7, 3).get(0) instanceof King)) {
                    return false;
                }
                // if there is a ChessPiece between the King and the Rook
                if (this.tilePieceArrayList(7, 1).size() != 0 || this.tilePieceArrayList(7, 2).size() != 0) {
                    return false;
                }
                // if there isn't a Rook in the right position
                if (this.tilePieceArrayList(7, 0).size() == 0 || !(this.tilePieceArrayList(7, 0).get(0).equals(this.blackRook1))) {
                    return false;
                }
                // if the King has moved
                if (this.blackKing.getHasMoved()) {
                    return false;
                }
                // if the Rook has moved
                if (this.blackRook1.getHasMoved()) {
                    return false;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    /**
     * check whether you could castle on the right
     */
    public boolean canRightCastle() {
        try {
            // if the King is under check
            if (this.isCheck) {
                return false;
            }
            if (this.playerColor.convertPlayerColorToColor() == Color.WHITE) {
                // if the King isn't in the correct position
                if (!(this.tilePieceArrayList(7, 4).get(0) instanceof King)) {
                    return false;
                }
                // if there is a ChessPiece between the King and the Rook
                if (this.tilePieceArrayList(7, 5).size() != 0 || this.tilePieceArrayList(7, 6).size() != 0) {
                    return false;
                }
                // if there isn't a Rook in the right position
                if (this.tilePieceArrayList(7, 7).size() == 0 || !(this.tilePieceArrayList(7, 7).get(0).equals(this.whiteRook2))) {
                    return false;
                }
                // if the King has moved
                if (this.whiteKing.getHasMoved()) {
                    return false;
                }
                // if the Rook has moved
                if (this.whiteRook2.getHasMoved()) {
                    return false;
                }
            }
            if (this.playerColor.convertPlayerColorToColor() == Color.BLACK) {
                // if the King isn't in the correct position
                if (!(this.tilePieceArrayList(7, 3).get(0) instanceof King)) {
                    return false;
                }
                // if there is a ChessPiece between the King and the Rook
                if (this.tilePieceArrayList(7, 4).size() != 0 || this.tilePieceArrayList(7, 5).size() != 0 || this.tilePieceArrayList(7, 6).size() != 0) {
                    return false;
                }
                // if there isn't a Rook in the right position
                if (this.tilePieceArrayList(7, 7).size() == 0 || !(this.tilePieceArrayList(7, 7).get(0).equals(this.blackRook2))) {
                    return false;
                }
                // if the King has moved
                if (this.blackKing.getHasMoved()) {
                    return false;
                }
                // if the Rook has moved
                if (this.blackRook2.getHasMoved()) {
                    return false;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    /**
     * check whether a Pawn is in the top row (and therefore could be promoted)
     */
    public ChessPiece getPawnPromotionPiece() {
        for (int column = 0; column < 8; column++) {
            if (this.tilePieceArrayList(0, column).size() != 0 && this.tilePieceArrayList(0, column).get(0) instanceof Pawn) {
                return this.tilePieceArrayList(0, column).get(0);
            }
        }
        return null;
    }

    /**
     * get the text from the textField to see what the Pawn is being promoted to
     */
    public String getPawnPromotionPieceText() {
        this.gamePane.requestFocus();
        return this.textField.getText();
    }

    /**
     * promotes the Pawn
     */
    public void promotePawn(ChessPiece pieceClicked, int clickRow, int clickColumn) throws InterruptedException {
        String pawnPromotionPieceText = this.getPawnPromotionPieceText();
        // Queen (defaults to Queen if no text is entered into the textField)
        if (Objects.equals(pawnPromotionPieceText, "Queen") || pawnPromotionPieceText.isEmpty()) {
            int pawnRow = this.getPawnPromotionPiece().getRow();
            int pawnColumn = this.getPawnPromotionPiece().getColumn();
            this.getPawnPromotionPiece().removeImage();
            ChessPiece queen = null;
            if (this.playerColor.convertPlayerColorToColor() == Color.WHITE) {
                queen = new Queen(this.gamePane, this, pawnColumn, pawnRow, this.playerColor.convertPlayerColorToColor(), "chess/chessPiecePNG/whiteQueen.png");
            }
            if (this.playerColor.convertPlayerColorToColor() == Color.BLACK) {
                queen = new Queen(this.gamePane, this, pawnColumn, pawnRow, this.playerColor.convertPlayerColorToColor(), "chess/chessPiecePNG/blackQueen.png");
            }
            this.reverseStack.pop();
            this.reverseStack.add(new Move(pieceClicked, new Coordinate<>(this.previousClickRow, this.previousClickColumn), new Coordinate<>(clickRow, clickColumn), pieceClicked.getChessPieceEaten(), queen, clickRow, clickColumn, this.playerColor, true));
            this.tilePieceArrayList(pawnRow, pawnColumn).clear();
            this.tiles[pawnRow][pawnColumn].addPiece(queen);
        }
        // Rook
        else if (Objects.equals(pawnPromotionPieceText, "Rook")) {
            int pawnRow = this.getPawnPromotionPiece().getRow();
            int pawnColumn = this.getPawnPromotionPiece().getColumn();
            this.getPawnPromotionPiece().removeImage();
            ChessPiece rook = null;
            if (this.playerColor.convertPlayerColorToColor() == Color.WHITE) {
                rook = new Rook(this.gamePane, this, pawnColumn, pawnRow, this.playerColor.convertPlayerColorToColor(), "chess/chessPiecePNG/whiteRook.png");
            }
            if (this.playerColor.convertPlayerColorToColor() == Color.BLACK) {
                rook = new Rook(this.gamePane, this, pawnColumn, pawnRow, this.playerColor.convertPlayerColorToColor(), "chess/chessPiecePNG/blackRook.png");
            }
            this.reverseStack.pop();
            this.reverseStack.add(new Move(pieceClicked, new Coordinate<>(this.previousClickRow, this.previousClickColumn), new Coordinate<>(clickRow, clickColumn), pieceClicked.getChessPieceEaten(), rook, clickRow, clickColumn, this.playerColor, true));
            this.tilePieceArrayList(pawnRow, pawnColumn).clear();
            this.tiles[pawnRow][pawnColumn].addPiece(rook);
        }
        // Bishop
        else if (Objects.equals(pawnPromotionPieceText, "Bishop")) {
            int pawnRow = this.getPawnPromotionPiece().getRow();
            int pawnColumn = this.getPawnPromotionPiece().getColumn();
            this.getPawnPromotionPiece().removeImage();
            ChessPiece bishop = null;
            if (this.playerColor.convertPlayerColorToColor() == Color.WHITE) {
                bishop = new Bishop(this.gamePane, this, pawnColumn, pawnRow, this.playerColor.convertPlayerColorToColor(), "chess/chessPiecePNG/whiteBishop.png");
            }
            if (this.playerColor.convertPlayerColorToColor() == Color.BLACK) {
                bishop = new Bishop(this.gamePane, this, pawnColumn, pawnRow, this.playerColor.convertPlayerColorToColor(), "chess/chessPiecePNG/blackBishop.png");
            }
            this.reverseStack.pop();
            this.reverseStack.add(new Move(pieceClicked, new Coordinate<>(this.previousClickRow, this.previousClickColumn), new Coordinate<>(clickRow, clickColumn), pieceClicked.getChessPieceEaten(), bishop, clickRow, clickColumn, this.playerColor, true));
            this.tilePieceArrayList(pawnRow, pawnColumn).clear();
            this.tiles[pawnRow][pawnColumn].addPiece(bishop);
        }
        // Knight
        else if (Objects.equals(pawnPromotionPieceText, "Knight")) {
            int pawnRow = this.getPawnPromotionPiece().getRow();
            int pawnColumn = this.getPawnPromotionPiece().getColumn();
            this.getPawnPromotionPiece().removeImage();
            ChessPiece knight = null;
            if (this.playerColor.convertPlayerColorToColor() == Color.WHITE) {
                knight = new Knight(this.gamePane, this, pawnColumn, pawnRow, this.playerColor.convertPlayerColorToColor(), "chess/chessPiecePNG/whiteKnight.png");
            }
            if (this.playerColor.convertPlayerColorToColor() == Color.BLACK) {
                knight = new Knight(this.gamePane, this, pawnColumn, pawnRow, this.playerColor.convertPlayerColorToColor(), "chess/chessPiecePNG/blackKnight.png");
            }
            this.reverseStack.pop();
            this.reverseStack.add(new Move(pieceClicked, new Coordinate<>(this.previousClickRow, this.previousClickColumn), new Coordinate<>(clickRow, clickColumn), pieceClicked.getChessPieceEaten(), knight, clickRow, clickColumn, this.playerColor, true));
            this.tilePieceArrayList(pawnRow, pawnColumn).clear();
            this.tiles[pawnRow][pawnColumn].addPiece(knight);
        }
    }

    /**
     * rotate the entire board
     */
    private void rotate() {
        // rotate gamePane
        Rotate rotate = new Rotate();
        rotate.setAngle(180);
        rotate.setPivotX(320);
        rotate.setPivotY(320);
        this.gamePane.getTransforms().add(rotate);
        // make a copy of the board
        BoardSquare[][] tilesCopy = new BoardSquare[8][8];
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                tilesCopy[row][column] = new BoardSquare(this.gamePane, Color.TRANSPARENT, row, column);
                tilesCopy[row][column].replacePieceArrayList(new ArrayList<>(this.tilePieceArrayList(row, column)));
            }
        }
        // for all the rows and columns
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                // if there is a ChessPiece, we rotate it graphically
                if (this.tilePieceArrayList(row, column).size() != 0) {
                    this.tilePieceArrayList(row, column).get(0).setRow(7 - this.tilePieceArrayList(row, column).get(0).getRow());
                    this.tilePieceArrayList(row, column).get(0).setColumn(7 - this.tilePieceArrayList(row, column).get(0).getColumn());
                }
                // rotate the pieceArrayLists
                this.tiles[row][column].replacePieceArrayList(tilesCopy[7 - row][7 - column].getPieceArrayList());
            }
        }
        this.gamePane.getTransforms().add(rotate);
    }

    /**
     * set up countdown timeline
     */
    private void setUpCountDownTimeLine() {
        KeyFrame kf = new KeyFrame(Duration.seconds(1), (ActionEvent timeline) -> this.countDownTimeLineActions());
        this.countDownTimeLine = new Timeline(kf);
        this.countDownTimeLine.setCycleCount(Animation.INDEFINITE);
    }

    /**
     * countdown
     */
    private void countDownTimeLineActions() {
        if (this.playerColor.convertPlayerColorToColor() == Color.WHITE) {
            this.whiteCountDown();
        } else {
            this.blackCountDown();
        }
    }

    /**
     * white countdown
     */
    private void whiteCountDown() {
        if (this.whiteSecond == 0) {
            this.whiteMinute--;
            this.whiteSecond = 59;
        } else {
            this.whiteSecond--;
        }
        whiteTimer.setMinutes(this.whiteMinute);
        whiteTimer.setSeconds(this.whiteSecond);
        whiteTimerLabel.setText(whiteTimer.toString());
        // end the game if the countdown hits 0
        if (this.whiteMinute == 0 && this.whiteSecond == 0) {
            this.errorMessageLabel.setText("black won!");
            this.isGameOver = true;
            this.countDownTimeLine.stop();
            this.timeline.stop();
        }
    }

    /**
     * black countdown
     */
    private void blackCountDown() {
        if (this.blackSecond == 0) {
            this.blackMinute--;
            this.blackSecond = 59;
        } else {
            this.blackSecond--;
        }
        blackTimer.setMinutes(this.blackMinute);
        blackTimer.setSeconds(this.blackSecond);
        blackTimerLabel.setText(blackTimer.toString());
        // end the game if the countdown hits 0
        if (this.blackMinute == 0 && this.blackSecond == 0) {
            this.errorMessageLabel.setText("white won!");
            this.isGameOver = true;
            this.countDownTimeLine.stop();
            this.timeline.stop();
        }
    }

    /**
     * handle key press
     */
    private void handleKeyPress() {
        this.gamePane.setOnKeyPressed(this::keyPress);
        this.gamePane.setFocusTraversable(true);
    }

    /**
     * defines key presses
     */
    private void keyPress(KeyEvent keyPress) {
        KeyCode keyPressed = keyPress.getCode();
        if (!this.isGameOver && keyPressed == KeyCode.P) {
            // P starts the game
            this.countDownTimeLine.play();
        }
        if (!this.isGameOver && keyPressed == KeyCode.R) {
            // R reverses the most recent move
            this.clearBoard();
            this.reverseMove();
        }
        // S restarts the game
        if (keyPressed == KeyCode.S) {
            this.restart();
        }
        // Q quits the game
        if (keyPressed == KeyCode.Q) {
            System.exit(0);
        }
    }

    /**
     * reverse the move
     */
    private void reverseMove() {
        try {
            Move moveToReverse = this.reverseStack.pop();
            ChessPiece currentChessPiece = moveToReverse.getCurrentPiece();
            this.rotate();
            currentChessPiece.reverseMove(moveToReverse.getFromCoordinate().getRow(), moveToReverse.getFromCoordinate().getC(), moveToReverse.getToCoordinate().getRow(), moveToReverse.getToCoordinate().getC(), moveToReverse.getEatenChessPiece(), moveToReverse.getSpecialMovePiece(), moveToReverse.getSpecialMoveRow(), moveToReverse.getSpecialMoveColumn(), moveToReverse.getPlayerColor(), moveToReverse.getIsPawnPromotion());
            this.playerColor = this.playerColor.getOppositePlayerColor();
        } catch (EmptyStackException e) {
            this.errorMessageLabel.setText("No moves to reverse!");
        }
    }

    /**
     * restart the game
     */
    private void restart() {
        this.gamePane.getChildren().clear();

        this.playerColor = PlayerColor.WHITE;
        this.isCheck = false;
        this.checkPiece = null;
        this.checkedKingColor = null;
        this.reverseStack = new Stack<>();
        this.isGameOver = false;
        this.countDownTimeLine.stop();

        this.turnLabel.setText("white turn");

        this.whiteMinute = 15;
        this.whiteSecond = 0;
        this.blackMinute = 15;
        this.blackSecond = 0;
        whiteTimer.setMinutes(this.whiteMinute);
        whiteTimer.setSeconds(this.whiteSecond);
        whiteTimerLabel.setText(whiteTimer.toString());
        blackTimer.setMinutes(this.blackMinute);
        blackTimer.setSeconds(this.blackSecond);
        blackTimerLabel.setText(blackTimer.toString());

        this.errorMessageLabel.setText("No errors!");
        this.textField.clear();
        this.textField.setText("");

        this.makeBoard();
        this.initializeBoard();
        this.setUpMainTimeLine();
    }

    public BoardSquare[][] getTiles() {return this.tiles;}

    public ArrayList<ChessPiece> tilePieceArrayList(int row, int column) {return this.tiles[row][column].getPieceArrayList();}

    public void changeColor(int row, int column, Color color) {this.tiles[row][column].changeColor(color);}

    public Color getPlayerColor() {return this.playerColor.convertPlayerColorToColor();}

    public Color getOppositeColor(Color color) {
        Color colorToReturn = null;
        if (color == Color.WHITE) {
            colorToReturn = Color.BLACK;
        }
        if (color == Color.BLACK) {
            colorToReturn = Color.WHITE;
        }
        return colorToReturn;
    }

    public boolean getCanLeftCastle() {return this.canLeftCastle();}

    public boolean getCanRightCastle() {return this.canRightCastle();}

    public Label getTurnLabel() {return turnLabel;}

    public Label getWhiteTimerLabel() {return this.whiteTimerLabel;}

    public Label getBlackTimerLabel() {return this.blackTimerLabel;}

    public Label getErrorMessageLabel() {return this.errorMessageLabel;}

    public TextField getTextField() {return this.textField;}

    public Button getStartButton() {return this.startButton;}

    public Button getRestartButton() {return this.restartButton;}

    public Button getSubmitButton() {return this.submitButton;}
}
