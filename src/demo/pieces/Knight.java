package demo.pieces;

import demo.Player;

public class Knight extends Piece {
    public Knight(Colour colour, int currentRow, int currentColumn) {
        super(colour, currentRow, currentColumn);
        name = "Knight";
        point = 3;
        if (colour != Colour.White) {
            view = '\u2658';
        } else {
            view = '\u265E';
        }
    }

    @Override
    public boolean canMove(Player player, Piece king, Piece[][] board) {
        placesToMoveTo = new boolean[8][8];
        if (isPinned(player, king, board)) {
            return false;
        } else {
            placesToMoveTo = checkPlaces(player, board, 1, 2, placesToMoveTo, false);
            placesToMoveTo = checkPlaces(player, board, 1, -2, placesToMoveTo, false);
            placesToMoveTo = checkPlaces(player, board, -1, 2, placesToMoveTo, false);
            placesToMoveTo = checkPlaces(player, board, -1, -2, placesToMoveTo, false);
            placesToMoveTo = checkPlaces(player, board, 2, 1, placesToMoveTo, false);
            placesToMoveTo = checkPlaces(player, board, 2, -1, placesToMoveTo, false);
            placesToMoveTo = checkPlaces(player, board, -2, 1, placesToMoveTo, false);
            placesToMoveTo = checkPlaces(player, board, -2, -1, placesToMoveTo, false);
        }
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
        protectPieces(player, board, 1, 2, false);
        protectPieces(player, board, 1, -2, false);
        protectPieces(player, board, -1, 2, false);
        protectPieces(player, board, -1, -2, false);
        protectPieces(player, board, 2, 1, false);
        protectPieces(player, board, 2, -1, false);
        protectPieces(player, board, -2, 1, false);
        protectPieces(player, board, -2, -1, false);
    }
}
