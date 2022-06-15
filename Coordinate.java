package chess;

public class Coordinate<Row, Column> {

    private final Row row;
    private final Column column;

    public Coordinate(Row row, Column column) {
        this.row = row;
        this.column = column;
    }

    public Row getRow() { return row;}

    public Column getC() { return column;}
}
