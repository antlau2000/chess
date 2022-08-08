package demo.pieces;

import demo.Player;

public class Pawn extends Piece {

    public Pawn(Colour colour, int currentRow, int currentColumn) {
        super(colour, currentRow, currentColumn);
        name = "Pawn";
        point = 1;
        if (colour != Colour.White) {
            view = '\u2659';
        } else {
            view = '\u265F';
        }
    }

    @Override
    public boolean move(Player player, Piece[][] board, int row, int column) {
        int startPositionRow;
        int distanceRow;
        int distanceColumn = Math.abs(column - currentColumn);
        int rowInFrontOfPiece;
        Piece pieceAtDestination = board[row][column];
        int rowForAttackingSpecialMove;
        if (colour == Colour.White) {
            startPositionRow = 1;
            distanceRow = row - currentRow;
            rowInFrontOfPiece = currentRow + 1;
            rowForAttackingSpecialMove = 4;
        } else {
            startPositionRow = 6;
            distanceRow = currentRow - row;
            rowInFrontOfPiece = currentRow - 1;
            rowForAttackingSpecialMove = 3;
        }

        if (currentRow == startPositionRow && distanceRow == 2 && distanceColumn == 0 && pieceAtDestination == null
                && board[rowInFrontOfPiece][column] == null) {
                movePiece(player, board, row, column);
                isSpecialPawnMoveUsed = true;
        } else if (distanceRow != 1) {
            System.out.println("Illegal move");
            System.out.println();
            return false;
        } else {
            if (distanceColumn == 0 && pieceAtDestination == null) {
                movePiece(player, board, row, column);
            } else if (distanceColumn == 1 && pieceAtDestination != null && pieceAtDestination.getColour() != colour) {
                movePiece(player, board, row, column);
            } else if (currentRow == rowForAttackingSpecialMove && distanceColumn == 1 && pieceAtDestination == null) {
                Piece piece = board[currentRow][column];
                if (piece instanceof Pawn && piece.isSpecialPawnMoveUsed()) {
                    movePieceAndAttackSpecialPawnMove(player, board, row, column);
                } else {
                    System.out.println("Illegal move");
                    System.out.println();
                    return false;
                }
            } else {
                System.out.println("Illegal move");
                System.out.println();
                return false;
            }
        }
        return true;
    }
}
