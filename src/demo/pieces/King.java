package demo.pieces;

import demo.Player;

// TODO Need to make a List<List<>> or Piece[][] with attacked fields to see where King can move
public class King extends Piece {
    public King(Colour colour, int currentRow, int currentColumn) {
        super(colour, currentRow, currentColumn);
        name = "King";
        if (colour != Colour.White) {
            view = '\u2654';
        } else {
            view = '\u265A';
        }
    }

    @Override
    public boolean canMove(Player player, Piece[][] board) {
        return false;
    }

    @Override
    public void protect(Player player, Piece[][] board) {

    }

    @Override
    public boolean move(Player player, Piece[][] board, int row, int column) {
        int distanceRow = row - currentRow;
        int distanceColumn = column - currentColumn;
        if (Math.abs(distanceRow) > 1 || Math.abs(distanceColumn) > 1) {
            System.out.println(name + " don't move that way");
            System.out.println("Try again!");
            System.out.println();
            return false;
        }
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
    }
}
