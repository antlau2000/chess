package demo.pieces;

import demo.Player;

public class Queen extends Piece {
    public Queen(demo.pieces.Colour colour, int currentRow, int currentColumn) {
        super(colour, currentRow, currentColumn);
        name = "Queen";
        point = 9;
        if (colour != demo.pieces.Colour.White) {
            view = '\u2655';
        } else {
            view = '\u265B';
        }
    }

    @Override
    public boolean canMove(Player player, Piece king, Piece[][] board) {
        placesToMoveTo = new boolean[8][8];
        if (isPinned(player, king, board)) {
            int rowDistanceNotAbs = king.currentRow - currentRow;
            int columnDistanceNotAbs = king.currentColumn - currentColumn;
            if (rowDistanceNotAbs == 0) {
                placesToMoveTo = checkPlaces(player, board, 0, 1, placesToMoveTo, true);
                placesToMoveTo = checkPlaces(player, board, 0, -1, placesToMoveTo, true);
            } else if (columnDistanceNotAbs == 0) {
                placesToMoveTo = checkPlaces(player, board, 1, 0, placesToMoveTo, true);
                placesToMoveTo = checkPlaces(player, board, -1, 0, placesToMoveTo, true);
            } else if (rowDistanceNotAbs == columnDistanceNotAbs) {
                placesToMoveTo = checkPlaces(player, board, 1, 1, placesToMoveTo, true);
                placesToMoveTo = checkPlaces(player, board, -1, -1, placesToMoveTo, true);
            } else {
                placesToMoveTo = checkPlaces(player, board, -1, 1, placesToMoveTo, true);
                placesToMoveTo = checkPlaces(player, board, 1, -1, placesToMoveTo, true);
            }
        } else {
            placesToMoveTo = checkPlaces(player, board, 1, 1, placesToMoveTo, true);
            placesToMoveTo = checkPlaces(player, board, -1, 1, placesToMoveTo, true);
            placesToMoveTo = checkPlaces(player, board, 1, -1, placesToMoveTo, true);
            placesToMoveTo = checkPlaces(player, board, -1, -1, placesToMoveTo, true);
            placesToMoveTo = checkPlaces(player, board, 1, 0, placesToMoveTo, true);
            placesToMoveTo = checkPlaces(player, board, -1, 0, placesToMoveTo, true);
            placesToMoveTo = checkPlaces(player, board, 0, 1, placesToMoveTo, true);
            placesToMoveTo = checkPlaces(player, board, 0, -1, placesToMoveTo, true);
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
        protectPieces(player, board, 1, 1, true);
        protectPieces(player, board, -1, 1, true);
        protectPieces(player, board, 1, -1, true);
        protectPieces(player, board, -1, -1, true);
        protectPieces(player, board, 1, 0, true);
        protectPieces(player, board, -1, 0, true);
        protectPieces(player, board, 0, 1, true);
        protectPieces(player, board, 0, -1, true);
    }
}
