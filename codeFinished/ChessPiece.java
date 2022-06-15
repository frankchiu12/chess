package chess.codeFinished;

import chess.Game;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class ChessPiece {

    private final Pane gamePane;
    private final Game game;
    private final Color color;
    private final ImageView imageView;
    private ChessPiece chessPieceEaten;
    public boolean kingHasMoved;

    public ChessPiece(Pane gamePane, Game game, int x, int y, Color color, String imagePath) {
        this.gamePane = gamePane;
        this.game = game;
        this.color = color;
        this.imageView = new ImageView();
        this.kingHasMoved = false;

        this.addImage(x, y, imagePath);
    }

    public void addImage(int x, int y, String imagePath) {
        this.imageView.setImage(new Image(imagePath));
        this.imageView.setX(x * 80);
        this.imageView.setY(y * 80);
        this.imageView.setFitWidth(80);
        this.imageView.setFitHeight(80);
        this.gamePane.getChildren().add(this.imageView);
    }

    /**
     * populates the coordinateArrayList with all the possible move coordinates of a given ChessPiece
     */
    public void overallMovement(ArrayList<Coordinate<Integer, Integer>> coordinateArrayList) {}

    /**
     * gets the coordinateArrayList and highlights the piece clicked as yellow, possible moves as green, and possible moves that eat opposing pieces as red
     */
    public void getPossibleMoves() {
        ArrayList<Coordinate<Integer, Integer>> coordinateArrayList = new ArrayList<>();
        this.overallMovement(coordinateArrayList);

        this.game.changeColor(this.getRow(), this.getColumn(), Color.YELLOW);
        for (Coordinate<Integer, Integer> coordinate : coordinateArrayList) {
            int row = coordinate.getRow();
            int column = coordinate.getC();
            if (this.getPieceArrayList(row, column).size() != 0) {
                this.game.changeColor(row, column, Color.RED);
            } else {
                this.game.changeColor(row, column, Color.GREEN);
            }
        }
    }

    /**
     * moves the ChessPiece
     */
    public void move(int clickRow, int clickColumn) {
        Color tileColor = this.game.getTiles()[clickRow][clickColumn].getColor();
        // if the tileColor is green or red
        if (tileColor == Color.GREEN || tileColor == Color.RED) {
            // set the ChessPiece to the clickRow and clickColumn
            this.setRow(clickRow);
            this.setColumn(clickColumn);
            // if there is a ChessPiece in the pieceArrayList, you set the variable this.chessPieceEaten to that ChessPiece, else it is null
            if (this.getPieceArrayList(clickRow, clickColumn).size() != 0) {
                this.chessPieceEaten = this.getPieceArrayList(clickRow, clickColumn).get(0);
            } else {
                this.chessPieceEaten = null;
            }
            // you clear the pieceArrayList and add the new ChessPiece into the pieceArrayList
            this.getPieceArrayList(clickRow, clickColumn).clear();
            this.getPieceArrayList(clickRow, clickColumn).add(this);
        }
        // if it is a King and the King hasn't moved yet, you can castle
        if (this instanceof King && !this.kingHasMoved) {
            // right Rook castle for white
            if (this.game.getPlayerColor() == Color.WHITE && clickRow == 7 && clickColumn == 6) {
                this.rightRookCastle();
            }
            // right Rook castle for black
            if (this.game.getPlayerColor() == Color.BLACK && clickRow == 7 && clickColumn == 5) {
                this.rightRookCastle();
            }
            // left Rook castle for white
            if (this.game.getPlayerColor() == Color.WHITE && clickRow == 7 && clickColumn == 2) {
                this.leftRookCastle();
            }
            // left Rook castle for black
            if (this.game.getPlayerColor() == Color.BLACK && clickRow == 7 && clickColumn == 1) {
                this.leftRookCastle();
            }
            // King has moved
            this.kingHasMoved = true;
        }
    }

    /**
     * gets the coordinateArrayList and highlights the piece clicked as yellow and checked King as red
     */
    public void getCheckMove() {
        ArrayList<Coordinate<Integer, Integer>> coordinateArrayList = new ArrayList<>();
        this.overallMovement(coordinateArrayList);

        this.game.changeColor(7 - this.getRow(), 7 - this.getColumn(), Color.YELLOW);
        for (Coordinate<Integer, Integer> coordinate : coordinateArrayList) {
            int row = coordinate.getRow();
            int column = coordinate.getC();
            if (this.getPieceArrayList(row, column).size() != 0 && this.getPieceArrayList(row, column).get(0) instanceof King) {
                this.game.changeColor(7 - row, 7 - column, Color.RED);
                return;
            }
        }
    }

    /**
     * simulates moving the ChessPiece to calculate checkmate
     */
    public void simulateMove(int previousRow, int previousColumn, int currentRow, int currentColumn) {
        // set the ChessPiece to the clickRow and clickColumn
        this.setRow(currentRow);
        this.setColumn(currentColumn);
        // clear the pieceArrayList of the previousRow and previousColumn
        this.getPieceArrayList(previousRow, previousColumn).clear();
        // if there is a ChessPiece in the pieceArrayList, you set the variable this.chessPieceEaten to that ChessPiece and remove its image, else it is null
        if (this.getPieceArrayList(currentRow, currentColumn).size() != 0) {
            this.chessPieceEaten = this.getPieceArrayList(currentRow, currentColumn).get(0);
            this.chessPieceEaten.removeImage();
        } else {
            this.chessPieceEaten = null;
        }
        // you clear the pieceArrayList of the currentRow and currentColumn and add the new ChessPiece into that pieceArrayList
        this.getPieceArrayList(currentRow, currentColumn).clear();
        this.getPieceArrayList(currentRow, currentColumn).add(this);
    }

    /**
     * reverses a move
     */
    public void reverseMove(int previousRow, int previousColumn, int currentRow, int currentColumn, ChessPiece chessPieceEaten, ChessPiece specialMovePiece, int specialMoveRow, int specialMoveColumn, PlayerColor playerColor, boolean isPawnPromotion){
        this.setRow(previousRow);
        this.setColumn(previousColumn);
        this.getPieceArrayList(currentRow, currentColumn).clear();
        this.getPieceArrayList(previousRow, previousColumn).add(this);
        // if the chessPieceEaten is not null
        if (chessPieceEaten != null){
            chessPieceEaten.setRow(currentRow);
            chessPieceEaten.setColumn(currentColumn);
            this.getPieceArrayList(currentRow, currentColumn).add(chessPieceEaten);
            chessPieceEaten.addImage();
        }
        // if the ChessPiece is a King, set kingHasMoved to false, so it could castle again
        if (this instanceof King) {
            this.kingHasMoved = false;
        }
        // if it is a castle
        if (specialMovePiece instanceof Rook && !isPawnPromotion) {
            specialMovePiece.setRow(specialMoveRow);
            specialMovePiece.setColumn(specialMoveColumn);
            this.getPieceArrayList(specialMoveRow, specialMoveColumn).add(specialMovePiece);
            if (specialMoveRow == 7 && specialMoveColumn == 0 && playerColor.convertPlayerColorToColor() == Color.WHITE) {
                this.getPieceArrayList(7, 3).clear();
            }
            if (specialMoveRow == 7 && specialMoveColumn == 0 && playerColor.convertPlayerColorToColor() == Color.BLACK) {
                this.getPieceArrayList(7, 2).clear();
            }
            if (specialMoveRow == 7 && specialMoveColumn == 7 && playerColor.convertPlayerColorToColor() == Color.WHITE) {
                this.getPieceArrayList(7, 5).clear();
            }
            if (specialMoveRow == 7 && specialMoveColumn == 7 && playerColor.convertPlayerColorToColor() == Color.BLACK) {
                this.getPieceArrayList(7, 4).clear();
            }
            this.kingHasMoved = false;
        }
        // if it is a pawn promotion
        else if (isPawnPromotion) {
            this.getPieceArrayList(currentRow, currentColumn).add(specialMovePiece);
            this.addImage();
            specialMovePiece.removeImage();
        }
    }

    /**
     * moves the left Rook for castling
     */
    public void leftRookCastle(){
        if (this.game.getPlayerColor() == Color.WHITE) {
            this.getPieceArrayList(7, 0).get(0).rookJump(7, 3);
        }
        if (this.game.getPlayerColor() == Color.BLACK) {
            this.getPieceArrayList(7, 0).get(0).rookJump(7, 2);
        }
    }

    /**
     * moves the right Rook for castling
     */
    public void rightRookCastle(){
        if (this.game.getPlayerColor() == Color.WHITE){
            this.getPieceArrayList(7,7).get(0).rookJump( 7, 5);
        }
        if (this.game.getPlayerColor() == Color.BLACK){
            this.getPieceArrayList(7,7).get(0).rookJump(7, 4);
        }
    }

    /**
     * jumps the Rook to the correct spot over the King for castling
     */
    public void rookJump(int jumpRow, int jumpColumn){
        this.setRow(jumpRow);
        this.setColumn(jumpColumn);
        this.getPieceArrayList(jumpRow, jumpColumn).add(this);
    }

    public int getRow() {return (int) (this.imageView.getY() / 80);}

    public int getColumn() {return (int) (this.imageView.getX() / 80);}

    public void setRow(int row) {this.imageView.setY(row * 80);}

    public void setColumn(int column) {this.imageView.setX(column * 80);}

    public Color getColor() {return this.color;}

    public void removeImage() {this.gamePane.getChildren().remove(this.imageView);}

    public void addImage() {this.gamePane.getChildren().add(this.imageView);}

    public ChessPiece getChessPieceEaten() {return this.chessPieceEaten;}

    public boolean getKingHasMoved() {return this.kingHasMoved;}

    public ArrayList<ChessPiece> getPieceArrayList(int row, int column) {return this.game.getTiles()[row][column].getPieceArrayList();}
}
