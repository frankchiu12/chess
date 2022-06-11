package chess;

import javafx.scene.paint.Color;

public enum PlayerColor {
    WHITE, BLACK;

    public PlayerColor getOppositePlayer(){
        if (this == PlayerColor.WHITE){
            return PlayerColor.BLACK;
        }
        else{
            return PlayerColor.WHITE;
        }
    }

    public boolean isRightColor(Color color){
        if (this == PlayerColor.WHITE && color == Color.WHITE){
            return true;
        }
        if (this == PlayerColor.BLACK && color == Color.BLACK){
            return true;
        }
        return false;
    }

    public String getOppositeColor(){
        if (this == PlayerColor.WHITE){
            return "white";
        } else {
            return "black";
        }
    }

    public Color convertToColor(){
        if (this == PlayerColor.WHITE){
            return Color.WHITE;
        }
        else {
            return Color.BLACK;
        }
    }
}
