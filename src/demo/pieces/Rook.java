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
    public boolean move(Player player, Piece[][] board, int row, int column) {
        int distanceRow = row - currentRow;
        int distanceColumn = column - currentColumn;
        boolean canMove;
        if (distanceRow == 0) {
            canMove = isPiecesInWay(board, distanceColumn, Direction.HORIZONTAL);
        } else if (distanceColumn == 0) {
            canMove = isPiecesInWay(board, distanceRow, Direction.VERTICAL);
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
