package demo.pieces;

import demo.Player;

public class Bishop extends Piece {
    public Bishop(Colour colour, int currentRow, int currentColumn) {
        super(colour, currentRow, currentColumn);
        name = "Bishop";
        point = 3;
        if (colour != Colour.White) {
            view = '\u2657';
        } else {
            view = '\u265D';
        }
    }

    @Override
    public boolean canMove(Player player, Piece[][] board) {
        placesToMoveTo = new boolean[8][8];
        placesToMoveTo = checkPlaces(player, board, 1, 1, placesToMoveTo, true);
        placesToMoveTo = checkPlaces(player, board, -1, 1, placesToMoveTo, true);
        placesToMoveTo = checkPlaces(player, board, 1, -1, placesToMoveTo, true);
        placesToMoveTo = checkPlaces(player, board, -1, -1, placesToMoveTo, true);
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
        protectPieces(player, board, 1, 1, true);
        protectPieces(player, board, -1, 1, true);
        protectPieces(player, board, 1, -1, true);
        protectPieces(player, board, -1, -1, true);
    }
}
