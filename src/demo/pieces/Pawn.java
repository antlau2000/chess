package demo.pieces;

import demo.Player;

public class Pawn extends Piece {
    private boolean isSpecialTurnUsed = false;
    private int rowStartPosition;

    public Pawn(Colour colour, int currentRow, int currentColumn) {
        super(colour, currentRow, currentColumn);
        name = "Pawn";
        point = 1;
        if (colour != Colour.White) {
            view = '\u2659';
            rowStartPosition = 6;
        } else {
            view = '\u265F';
            rowStartPosition = 1;
        }
    }

    @Override
    public boolean canMove(Player player, Piece[][] board) {
        placesToMoveTo = new boolean[8][8];
        boolean canMove = false;
        int rowPlus = 1;
        if (colour != Colour.White) {
            rowPlus = -1;
        }
        int tempRow = currentRow + rowPlus;
        int tempSpecialRow = tempRow + rowPlus;
        int tempColumnToRight = currentColumn + 1;
        int tempColumnToLeft = currentColumn - 1;

        if (tempRow >= 0 && tempRow <= 7 && board[tempRow][currentColumn] == null) {
            placesToMoveTo[tempRow][currentColumn] = true;
            canMove = true;
            if (currentRow == rowStartPosition && board[tempSpecialRow][currentColumn] == null) {
                placesToMoveTo[tempSpecialRow][currentColumn] = true;
            }
        }
        if (tempColumnToRight <= 7 && board[tempRow][tempColumnToRight] != null
                && board[tempRow][tempColumnToRight].getColour() != colour) {
            placesToMoveTo[tempRow][tempColumnToRight] = true;
            canMove = true;
        }
        if (tempColumnToLeft >= 0 && board[tempRow][tempColumnToLeft] != null
                && board[tempRow][tempColumnToLeft].getColour() != colour) {
            placesToMoveTo[tempRow][tempColumnToLeft] = true;
            canMove = true;
        }
        return canMove;
    }

    @Override
    public void protect(Player player, Piece[][] board) {
        int rowPlus = 1;
        if (colour != Colour.White) {
            rowPlus = -1;
        }

        int tempRow = currentRow + rowPlus;
        int tempColumnToRight = currentColumn + 1;
        int tempColumnToLeft = currentColumn - 1;

        if (tempColumnToRight <= 7) {
            Piece pieceToRight = board[tempRow][tempColumnToRight];
            if (pieceToRight != null && pieceToRight.getColour() == colour) {
                pieceToRight.setProtected(true);
            }
        }
        if (tempColumnToLeft >= 0) {
            Piece pieceToLeft = board[tempRow][tempColumnToLeft];
            if (pieceToLeft != null && pieceToLeft.getColour() == colour) {
                pieceToLeft.setProtected(true);
            }
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
                isSpecialTurnUsed = true;
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
                if (piece instanceof Pawn && ((Pawn) piece).isSpecialTurnUsed) {
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

    public void setSpecialTurnUsed(boolean specialTurnUsed) {
        isSpecialTurnUsed = specialTurnUsed;
    }
}
