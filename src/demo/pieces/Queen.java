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
    public boolean move(Player player, Piece[][] board, int row, int column) {
        int distanceRow = row - currentRow;
        int distanceColumn = column - currentColumn;
        boolean canMove;
        if (distanceRow == 0) {
            canMove = isPiecesInWay(board, distanceColumn, Direction.HORIZONTAL);
        } else if (distanceColumn == 0) {
            canMove = isPiecesInWay(board, distanceRow, Direction.VERTICAL);
        } else if (distanceRow == distanceColumn) {
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
