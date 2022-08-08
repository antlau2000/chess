package demo;

import demo.pieces.Piece;

public class Board {
    private Piece[][] array = new Piece[8][8];

    public Piece[][] getArray() {
        return array;
    }

    public void setArray(Piece[][] array) {
        this.array = array;
    }
}
