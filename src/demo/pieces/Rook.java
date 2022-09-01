package demo.pieces;

import demo.Player;

public class Rook extends Piece {
    public Rook(demo.pieces.Colour colour, int currentRow, int currentColumn) {
        super(colour, currentRow, currentColumn);
        name = "Rook";
        point = 5;
        if (colour != demo.pieces.Colour.White) {
            view = '\u2656';
        } else {
            view = '\u265C';
        }
    }

    @Override
    public boolean canMove(Player player, Piece[][] board) {
        placesToMoveTo = new boolean[8][8];
        placesToMoveTo = checkPlaces(player, board, 1, 0, placesToMoveTo, true);
        placesToMoveTo = checkPlaces(player, board, -1, 0, placesToMoveTo, true);
        placesToMoveTo = checkPlaces(player, board, 0, 1, placesToMoveTo, true);
        placesToMoveTo = checkPlaces(player, board, 0, -1, placesToMoveTo, true);
        for (boolean[] places : placesToMoveTo) {
            for (boolean place : places) {
                if (place) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void protect(Player player, Piece[][] board) {
        protectPieces(player, board, 1, 0, true);
        protectPieces(player, board, -1, 0, true);
        protectPieces(player, board, 0, 1, true);
        protectPieces(player, board, 0, -1, true);
    }
}
