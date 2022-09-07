package demo.pieces;

import demo.Player;

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
    public boolean canMove(Player player, Piece king, Piece[][] board) {
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
        if (leftColumn >= 0 && canMoveKing(player, board, currentRow, leftColumn)) {
            canMove = true;
        }
        if (!isUsed && !player.getAttackedPlaces()[currentRow][currentColumn]) {
            Piece rightRook = board[currentRow][7];
            Piece leftRook = board[currentRow][0];
            if (rightRook instanceof Rook && !rightRook.isUsed) {
                if (board[currentRow][5] == null && board[currentRow][6] == null) {
                    if (!player.getAttackedPlaces()[currentRow][5] && !player.getAttackedPlaces()[currentRow][6]) {
                        placesToMoveTo[currentRow][6] = true;
                    }
                }
            }
            if (leftRook instanceof Rook && !leftRook.isUsed) {
                if (board[currentRow][1] == null && board[currentRow][2] == null && board[currentRow][3] == null) {
                    if (!player.getAttackedPlaces()[currentRow][2] && !player.getAttackedPlaces()[currentRow][3]) {
                        placesToMoveTo[currentRow][2] = true;
                    }
                }
            }
        }
        return canMove;
    }

    public boolean canMoveKing(Player player, Piece[][] board, int row, int column) {
        Piece piece = board[row][column];
        boolean canMove = false;
        if (piece == null) {
            if (!player.getAttackedPlaces()[row][column]) {
                placesToMoveTo[row][column] = true;
                canMove = true;
            }
        } else if (piece.getColour() != colour && !piece.isProtected()) {
            placesToMoveTo[row][column] = true;
            canMove = true;
        }
        return canMove;
    }

    @Override
    public boolean canMoveWhileCheck(Player player, boolean[][] stopCheck, Piece[][] board) {
        return canMove(player, this, board);
    }

    @Override
    public boolean isPinned(Player player, Piece king, Piece[][] board) {
        return false;
    }

    @Override
    public void protect(Player player, Piece[][] board) {
        protectPieces(player, board, 1, 1, false);
        protectPieces(player, board, -1, 1, false);
        protectPieces(player, board, 1, -1, false);
        protectPieces(player, board, -1, -1, false);
    }
}
