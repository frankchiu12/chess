package chess.finishedCode;

import javafx.scene.paint.Color;

public enum PlayerColor {

    WHITE, BLACK;

    public Color convertPlayerColorToColor() {
        if (this == PlayerColor.WHITE) {
            return Color.WHITE;
        }
        else {
            return Color.BLACK;
        }
    }

    public boolean isRightTurn(Color color) {
        if (this == PlayerColor.WHITE && color == Color.WHITE) {
            return true;
        }
        return this == PlayerColor.BLACK && color == Color.BLACK;
    }

    public PlayerColor getOppositePlayerColor() {
        if (this == PlayerColor.WHITE) {
            return PlayerColor.BLACK;
        }
        else {
            return PlayerColor.WHITE;
        }
    }

    public String getOppositeColorString() {
        if (this == PlayerColor.WHITE) {
            return "white";
        } else {
            return "black";
        }
    }
}
