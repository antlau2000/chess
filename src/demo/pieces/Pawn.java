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
    public boolean canMove(Player player, Piece king, Piece[][] board) {
        placesToMoveTo = new boolean[8][8];
        boolean canMove = false;
        boolean canMoveUp = true;
        boolean canAttack = true;
        if (isPinned(player, king, board)) {
            if (king.getCurrentRow() - currentRow == 0) {
                return false;
            } else if (king.getCurrentColumn() - currentColumn != 0) {
                canMoveUp = false;
            } else {
                if (colour == Colour.White) {
                    if (king.getCurrentRow() - currentRow < 0) {
                        canAttack = false;
                    }
                } else {
                    if (king.getCurrentRow() - currentRow > 0) {
                        canAttack = false;
                    }
                }
            }
        }
        int rowPlus = 1;
        if (colour != Colour.White) {
            rowPlus = -1;
        }
        int tempRow = currentRow + rowPlus;
        int tempSpecialRow = tempRow + rowPlus;
        int tempColumnToRight = currentColumn + 1;
        int tempColumnToLeft = currentColumn - 1;

        if (tempRow >= 0 && tempRow <= 7 && board[tempRow][currentColumn] == null) {
            if (canMoveUp) {
                placesToMoveTo[tempRow][currentColumn] = true;
                canMove = true;
                if (currentRow == rowStartPosition && board[tempSpecialRow][currentColumn] == null) {
                    placesToMoveTo[tempSpecialRow][currentColumn] = true;
                }
            }
        }
        if (canAttack) {
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
    public boolean move(Player player, Player opponent, Piece[][] board, int row, int column) {
        if (placesToMoveTo[row][column]) {
            if (Math.abs(row - currentRow) == 2) {
                isSpecialTurnUsed = true;
            }
            movePiece(player, opponent, board, row, column);
            return true;
        }
        return false;
    }

    public void setSpecialTurnUsed(boolean specialTurnUsed) {
        isSpecialTurnUsed = specialTurnUsed;
    }

    public boolean isSpecialTurnUsed() {
        return isSpecialTurnUsed;
    }
}
