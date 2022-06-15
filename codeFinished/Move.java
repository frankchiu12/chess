package chess.codeFinished;

public class Move {

    private final ChessPiece currentPiece;
    private final Coordinate<Integer, Integer> fromCoordinate;
    private final Coordinate<Integer, Integer> toCoordinate;
    private final ChessPiece eatenChessPiece;
    private final ChessPiece specialMovePiece;
    private final int specialMoveRow;
    private final int specialMoveColumn;
    private final PlayerColor playerColor;
    private final boolean isPawnPromotion;

    public Move(ChessPiece currentPiece, Coordinate<Integer, Integer> fromPair, Coordinate<Integer, Integer> toPair, ChessPiece eatenChessPiece, ChessPiece specialMovePiece, int specialMoveRow, int specialMoveColumn, PlayerColor playerColor, boolean isPawnPromotion) {
        this.currentPiece = currentPiece;
        this.fromCoordinate = fromPair;
        this.toCoordinate = toPair;
        this.eatenChessPiece = eatenChessPiece;
        this.specialMovePiece = specialMovePiece;
        this.specialMoveRow = specialMoveRow;
        this.specialMoveColumn = specialMoveColumn;
        this.playerColor = playerColor;
        this.isPawnPromotion = isPawnPromotion;
    }

    public ChessPiece getCurrentPiece() {
        return this.currentPiece;
    }

    public Coordinate<Integer, Integer> getFromCoordinate() {
        return this.fromCoordinate;
    }

    public Coordinate<Integer, Integer> getToCoordinate() {
        return this.toCoordinate;
    }

    public ChessPiece getEatenChessPiece() {
        return this.eatenChessPiece;
    }

    public ChessPiece getSpecialMovePiece() {
        return this.specialMovePiece;
    }

    public int getSpecialMoveRow() {return specialMoveRow;}

    public int getSpecialMoveColumn() {return specialMoveColumn;}

    public PlayerColor getPlayerColor() {return playerColor;}

    public boolean isPawnPromotion() {return isPawnPromotion;}
}
