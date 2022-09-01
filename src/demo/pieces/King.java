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
        placesToMoveTo = new boolean[8][8];
        boolean canMove = false;
        int upRow = currentRow + 1;
        if (upRow <= 7 && canMoveKing(player, board, upRow, currentColumn)) {
            canMove = true;
        }
        int downRow = currentRow - 1;
        if (downRow >= 0 && canMoveKing(player, board, downRow, currentColumn)) {
            canMove = true;
        }
        int rightColumn = currentColumn + 1;
        if (rightColumn <= 7 && canMoveKing(player, board, currentRow, rightColumn)) {
            canMove = true;
        }
        int leftColumn = currentColumn - 1;
        if (rightColumn >= 0 && canMoveKing(player, board, currentRow, rightColumn)) {
            canMove = true;
        }
        return canMove;
    }

    public boolean canMoveKing(Player player, Piece[][] board, int row, int column) {
        Piece piece = board[row][column];
        boolean canMove = false;
        if (!player.getAttackedPlaces()[row][column] && piece == null) {
            placesToMoveTo[row][column] = true;
            canMove = true;
        } else if (piece.getColour() != colour && !piece.isProtected()) {
            placesToMoveTo[row][column] = true;
            canMove = true;
        }
        return canMove;
    }

    @Override
    public void protect(Player player, Piece[][] board) {
        protectPieces(player, board, 1, 1, false);
        protectPieces(player, board, -1, 1, false);
        protectPieces(player, board, 1, -1, false);
        protectPieces(player, board, -1, -1, false);
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
