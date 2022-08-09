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
    public boolean canMove(Player player, Piece[][] board) {
        placesToMoveTo = new boolean[8][8];
        placesToMoveTo = checkPlaces(player, board, 1, 2, placesToMoveTo, false);
        placesToMoveTo = checkPlaces(player, board, 1, -2, placesToMoveTo, false);
        placesToMoveTo = checkPlaces(player, board, -1, 2, placesToMoveTo, false);
        placesToMoveTo = checkPlaces(player, board, -1, -2, placesToMoveTo, false);
        placesToMoveTo = checkPlaces(player, board, 2, 1, placesToMoveTo, false);
        placesToMoveTo = checkPlaces(player, board, 2, -1, placesToMoveTo, false);
        placesToMoveTo = checkPlaces(player, board, -2, 1, placesToMoveTo, false);
        placesToMoveTo = checkPlaces(player, board, -2, -1, placesToMoveTo, false);
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
        int distanceRow = Math.abs(row - currentRow);
        int distanceColumn = Math.abs(column - currentColumn);
        if (distanceRow == 1 && distanceColumn == 2 || distanceRow == 2 && distanceColumn == 1) {
            Piece piece = board[row][column];
            if (piece == null || piece.getColour() != colour) {
                movePiece(player, board, row, column);
                return true;
            } else {
                System.out.println(name + " can't move that way");
                System.out.println("Try again!");
                System.out.println();
                return false;
            }
        } else {
            System.out.println(name + " don't move that way");
            System.out.println("Try again!");
            System.out.println();
            return false;
        }
    }
}
