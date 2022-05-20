package chess;

public class Move<currentPiece, fromPair, toPair, eaten> {

    private ChessPiece currentPiece;
    private Coordinate<Integer, Integer> fromPair;
    private Coordinate<Integer, Integer> toPair;
    private ChessPiece eatenChessPiece;

    public Move(ChessPiece currentPiece, Coordinate<Integer, Integer> fromPair, Coordinate<Integer, Integer> toPair, ChessPiece eatenChessPiece){
        this.currentPiece = currentPiece;
        this.fromPair = fromPair;
        this.toPair = toPair;
        this.eatenChessPiece = eatenChessPiece;
    }

    public ChessPiece getCurrentPiece(){
        return this.currentPiece;
    }

    public Coordinate<Integer, Integer> getFromPair(){
        return this.fromPair;
    }

    public Coordinate<Integer, Integer> getToPair(){
        return this.toPair;
    }

    public ChessPiece getEatenChessPiece(){
        return this.eatenChessPiece;
    }
}
