package chess;

public class Coordinate<Row,Column> {

    private final Row r;
    private final Column c;

    public Coordinate(Row r, Column c){
        this.r = r;
        this.c = c;
    }
    public Row getR(){ return r; }
    public Column getC(){ return c; }
}
