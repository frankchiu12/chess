package chess;

public class Pair<Row,Column> {

    private final Row r;
    private final Column c;

    public Pair(Row r, Column c){
        this.r = r;
        this.c = c;
    }
    public Row getR(){ return r; }
    public Column getC(){ return c; }
}
