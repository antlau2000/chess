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

    @Override
    public boolean move(Player player, Piece[][] board, int row, int column) {
        int distanceRow = row - currentRow;
        int distanceColumn = column - currentColumn;
        boolean canMove;
        if (distanceRow == distanceColumn) {
            canMove = isPiecesInWay(board, distanceRow, Direction.RIGHT_UP);
        } else if (distanceRow == -distanceColumn) {
            canMove = isPiecesInWay(board, distanceRow, Direction.LEFT_UP);
        } else {
            System.out.println(name + " don't move that way");
            System.out.println("Try again!");
            System.out.println();
            return false;
        }
        if (canMove) {
            movePiece(player, board, row, column);
            return true;
        }
        return false;
    }
}
