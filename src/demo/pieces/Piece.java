package demo.pieces;

import demo.Player;

public abstract class Piece implements Moving {
    protected String name;
    protected Colour colour;
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
    public boolean isPinned(Player player, Piece king, Piece[][] board) {
        int rowDistance = Math.abs(king.currentRow - currentRow);
        int columnDistance = Math.abs(king.currentColumn - currentColumn);
        int reduceRowDistance = -1;
        int reduceColumnDistance = -1;
        boolean pieceIsBundledWithKing = true;
        int checkRow = currentRow;
        int checkColumn = currentColumn;
        if (rowDistance == columnDistance || rowDistance == 0 || columnDistance == 0) {
            if (rowDistance != 1 || columnDistance != 1) {
                int distance;
                if (rowDistance == columnDistance) {
                    distance = rowDistance;
                    if (king.currentRow - currentRow > 0) {
                        reduceRowDistance = 1;
                    }
                    if (king.currentColumn - currentColumn > 0) {
                        reduceColumnDistance = 1;
                    }
                } else if (rowDistance == 0) {
                    distance = columnDistance;
                    reduceRowDistance = 0;
                    if (king.currentColumn - currentColumn > 0) {
                        reduceColumnDistance = 1;
                    }
                } else {
                    distance = rowDistance;
                    reduceColumnDistance = 0;
                    if (king.currentRow - currentRow > 0) {
                        reduceRowDistance = 1;
                    }
                }
                for (int i = 0; i < distance - 1; i++) {
                    checkRow += reduceRowDistance;
                    checkColumn += reduceColumnDistance;
                    if (board[checkRow][checkColumn] != null) {
                        pieceIsBundledWithKing = false;
                        break;
                    }
                }
            }
            if (pieceIsBundledWithKing) {
                int increaseRow = reduceRowDistance * -1;
                int increaseColumn = reduceColumnDistance * -1;
                checkRow = currentRow;
                checkColumn = currentColumn;
                while (true) {
                    checkRow += increaseRow;
                    checkColumn += increaseColumn;
                    if (checkRow >= 0 && checkRow <= 7 && checkColumn >= 0 && checkColumn <= 7) {
                        Piece piece = board[checkRow][checkColumn];
                        if (piece != null) {
                            if (piece instanceof Queen) {
                                return true;
                            } else if (piece instanceof Rook) {
                                if (rowDistance != columnDistance) {
                                    return true;
                                }
                            } else if (piece instanceof Bishop) {
                                if (rowDistance == columnDistance) {
                                    return true;
                                }
                            }
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean canMoveWhileCheck(Player player, boolean[][] stopCheck, Piece[][] board) {
        boolean[][] placesToMoveWhileCheck = new boolean[8][8];
        boolean canMove = false;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (stopCheck[i][j] && placesToMoveTo[i][j]) {
                    placesToMoveWhileCheck[i][j] = true;
                    canMove = true;
                }
            }
        }
        placesToMoveTo = placesToMoveWhileCheck;
        return canMove;
    }

    @Override
    public boolean move(Player player, Player opponent, Piece[][] board, int row, int column) {
        if (placesToMoveTo[row][column]) {
            movePiece(player, opponent, board, row, column);
            return true;
        }
        return false;
    }

    protected void movePiece(Player player, Player opponent, Piece[][] board, int row, int column) {
        if (!isUsed) {
            isUsed = true;
        }
        if (board[row][column] != null) {
            player.getTokenPieces().add(board[row][column]);
            opponent.getPieces().remove(board[row][column]);
        } else if (row == 2) {
            Piece piece = board[row + 1][column];
            if (piece instanceof Pawn && ((Pawn) piece).isSpecialTurnUsed()) {
                player.getTokenPieces().add(piece);
                opponent.getPieces().remove(board[row + 1][column]);
                board[row + 1][column] = null;
            }
        } else if (row == 5) {
            Piece piece = board[row - 1][column];
            if (piece instanceof Pawn && ((Pawn) piece).isSpecialTurnUsed()) {
                player.getTokenPieces().add(piece);
                opponent.getPieces().remove(board[row - 1][column]);
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

    public void setProtected(boolean isProtected) {
        this.isProtected = isProtected;
    }

    public boolean[][] getPlacesToMoveTo() {
        return placesToMoveTo;
    }
}
