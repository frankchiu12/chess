package chess;

public class Pair<Row,Column> {

    private Row r;
    private Column c;

    public Pair(Row r, Column c){
        this.r = r;
        this.c = c;
    }
    public Row getR(){ return r; }
    public Column getC(){ return c; }
    public void setR(Row r){ this.r = r; }
    public void setC(Column c){ this.c = c; }
}
