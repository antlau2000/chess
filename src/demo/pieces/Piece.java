package demo.pieces;

import demo.Player;

public abstract class Piece implements Moving {
    protected String name;
    protected demo.pieces.Colour colour;
    protected char view;
    protected int currentRow;
    protected int currentColumn;
    protected int point;
    protected boolean[][] placesToMoveTo;
    protected boolean isProtected;
    protected boolean isUsed;

    public Piece(Colour colour, int currentRow, int currentColumn) {
        this.colour = colour;
        this.currentRow = currentRow;
        this.currentColumn = currentColumn;
    }

    @Override
    public boolean move(Player player, Piece[][] board, int row, int column) {
        if (placesToMoveTo[row][column]) {
            movePiece(player, board, row, column);
            return true;
        }
        return false;
    }

    protected void movePiece(Player player, Piece[][] board, int row, int column) {
        if (!isUsed) {
            isUsed = true;
        }
        if (board[row][column] != null) {
            player.getTokenPieces().add(board[row][column]);
        } else if (row == 2) {
            Piece piece = board[row + 1][column];
            if (piece instanceof Pawn && ((Pawn) piece).isSpecialTurnUsed()) {
                player.getTokenPieces().add(piece);
                board[row + 1][column] = null;
            }
        } else if (row == 5) {
            Piece piece = board[row - 1][column];
            if (piece instanceof Pawn && ((Pawn) piece).isSpecialTurnUsed()) {
                player.getTokenPieces().add(piece);
                board[row - 1][column] = null;
            }
        } else if (this instanceof King && Math.abs(column - currentColumn) == 2) {
            if (column - currentColumn == 2) {
                board[row][5] = board[row][7];
                board[row][7] = null;
                board[row][5].currentColumn = 5;
            } else {
                board[row][3] = board[row][0];
                board[row][0] = null;
                board[row][3].currentColumn = 3;
            }
        }

        board[row][column] = this;
        board[currentRow][currentColumn] = null;
        currentRow = row;
        currentColumn = column;
    }

    public boolean[][] checkPlaces(Player player, Piece[][] board, int rowPlus, int columnPlus,
                                   boolean[][] placesToMoveTo, boolean infinity) {
        int tempRow = currentRow;
        int tempColumn = currentColumn;
        do {
            tempRow += rowPlus;
            tempColumn += columnPlus;
            if (tempRow > 7 || tempColumn > 7 || tempRow < 0 || tempColumn < 0) {
                break;
            }
            Piece currentPlace = board[tempRow][tempColumn];
            if (currentPlace == null) {
                placesToMoveTo[tempRow][tempColumn] = true;
            } else {
                if (currentPlace.getColour() != player.getColour()) {
                    placesToMoveTo[tempRow][tempColumn] = true;
                }
                break;
            }
        } while (infinity);
        return placesToMoveTo;
    }

    public abstract void protect(Player player, Piece[][] board);

    public void protectPieces(Player player, Piece[][] board, int rowPlus, int columnPlus, boolean infinity) {
        int tempRow = currentRow;
        int tempColumn = currentColumn;
        do {
            tempRow += rowPlus;
            tempColumn += columnPlus;
            if (tempRow > 7 || tempColumn > 7 || tempRow < 0 || tempColumn < 0) {
                break;
            }
            Piece currentPiece = board[tempRow][tempColumn];
            if (currentPiece != null) {
                if (currentPiece.getColour() == player.getColour()) {
                    currentPiece.setProtected(true);
                }
                break;
            }
        } while (infinity);
    }

    public String getName() {
        return name;
    }

    public char getView() {
        return view;
    }

    public demo.pieces.Colour getColour() {
        return colour;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public int getCurrentColumn() {
        return currentColumn;
    }

    public int getPoint() {
        return point;
    }

    public boolean isProtected() {
        return isProtected;
    }

    public void setProtected(boolean aProtected) {
        isProtected = aProtected;
    }

    public boolean[][] getPlacesToMoveTo() {
        return placesToMoveTo;
    }
}
