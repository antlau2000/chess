package demo.pieces;

import demo.Player;

public abstract class Piece implements Moving {
    protected String name;
    protected demo.pieces.Colour colour;
    protected char view;
    protected int currentRow;
    protected int currentColumn;
    protected boolean isSpecialPawnMoveUsed;
    protected int point;

    public Piece(Colour colour, int currentRow, int currentColumn) {
        this.colour = colour;
        this.currentRow = currentRow;
        this.currentColumn = currentColumn;
    }

    protected void movePiece(Player player, Piece[][] board, int row, int column) {
        if (board[row][column] != null) {
            player.getTokenPieces().add(board[row][column]);
        }
        board[row][column] = this;
        board[currentRow][currentColumn] = null;
        currentRow = row;
        currentColumn = column;
    }

    protected void movePieceAndAttackSpecialPawnMove(Player player, Piece[][] board, int row, int column) {
        int rowWithSpecialMovePawn;
        if (colour == Colour.White) {
            rowWithSpecialMovePawn = 4;
        } else {
            rowWithSpecialMovePawn = 3;
        }
        player.getTokenPieces().add(board[rowWithSpecialMovePawn][column]);
        board[rowWithSpecialMovePawn][column] = null;
        board[row][column] = this;
        board[currentRow][currentColumn] = null;
        currentRow = row;
        currentColumn = column;
    }

    protected boolean isPiecesInWay(Piece[][] board, int distance, Direction direction) {
        Piece tempPiece;
        int distanceRow = 0;
        int distanceColumn = 0;
        boolean finalPlace = true;
        switch (direction) {
            case HORIZONTAL:
                distanceColumn = distance;
                break;
            case VERTICAL:
                distanceRow = distance;
                break;
            case RIGHT_UP:
                distanceRow = distance;
                distanceColumn = distance;
                break;
            case LEFT_UP:
                distanceRow = distance;
                distanceColumn = -distance;
        }
        while (distance != 0) {
            tempPiece = board[currentRow + distanceRow][currentColumn + distanceColumn];
            if (distance < 0) {
                distance++;
            } else {
                distance--;
            }
            if (distanceRow != 0) {
                distanceRow = distance;
            }
            if (distanceColumn != 0) {
                if (direction != Direction.LEFT_UP) {
                    distanceColumn = distance;
                } else {
                    distanceColumn = -distance;
                }
            }
            if (tempPiece == null) {
                finalPlace = false;
            } else if (finalPlace && tempPiece.getColour() != colour) {
                finalPlace = false;
            } else {
                System.out.println("Wrong");
                System.out.println("Try again!");
                System.out.println();
                return false;
            }
        }
        return true;
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

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public int getCurrentColumn() {
        return currentColumn;
    }

    public void setCurrentColumn(int currentColumn) {
        this.currentColumn = currentColumn;
    }

    public boolean isSpecialPawnMoveUsed() {
        return isSpecialPawnMoveUsed;
    }

    public void setSpecialPawnMoveUsed(boolean specialPawnMoveUsed) {
        this.isSpecialPawnMoveUsed = specialPawnMoveUsed;
    }

    public int getPoint() {
        return point;
    }
}
