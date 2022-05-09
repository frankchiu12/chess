package chess;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Rook implements Piece {

    private Color color;

    public Rook(Pane gamePane, int x, int y, Color color){

        this.color = color;
        Image image = new Image("chess/blackRook.png");

        if (color == Color.WHITE){
            image = new Image("chess/whiteRook.png");
        }

        ImageView imageView = new ImageView();
        imageView.setImage(image);

        imageView.setX(x * 80);
        imageView.setY(y * 80);
        imageView.setFitWidth(80);
        imageView.setFitHeight(80);
        gamePane.getChildren().add(imageView);
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public void getPossibleMoves() {

    }

    @Override
    public void move(int clickRow, int clickColumn) {

    }

    @Override
    public void removeImage() {

    }
}
