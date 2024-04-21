package test;


public class Word
{
    public Tile[] tiles;
    int row;
    int col;
    boolean vertical;

    public Word(Tile[] tiles, int row, int col, boolean vertical) {
        this.tiles = tiles;
        this.row = row;
        this.col = col;
        this.vertical = vertical;
    }
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Word word = (Word) object;
        return row == word.row && col == word.col && vertical == word.vertical && java.util.Objects.equals(tiles, word.tiles);
    }
    public int getCol() {
        return col;
    }
    public boolean isVertical() {
        return vertical;
    }
    public int getRow() {
        return row;
    }
    public Tile[] getTiles() {
        return tiles;
    }
}
