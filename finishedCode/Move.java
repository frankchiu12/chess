package chess.finishedCode;

import chess.ChessPiece;

public class Move {

    private final ChessPiece currentPiece;
    private final Coordinate<Integer, Integer> fromCoordinate;
    private final Coordinate<Integer, Integer> toCoordinate;
    private final ChessPiece eatenChessPiece;

    public Move(ChessPiece currentPiece, Coordinate<Integer, Integer> fromPair, Coordinate<Integer, Integer> toPair, ChessPiece eatenChessPiece) {
        this.currentPiece = currentPiece;
        this.fromCoordinate = fromPair;
        this.toCoordinate = toPair;
        this.eatenChessPiece = eatenChessPiece;
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
}
