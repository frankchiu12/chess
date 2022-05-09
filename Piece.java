package chess;

import javafx.scene.paint.Color;

public interface Piece {

    Color getColor();
    void getPossibleMoves();
    void move(int clickRow, int clickColumn);
}
