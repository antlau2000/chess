package demo.pieces;

import demo.Player;

public interface Moving {
    boolean canMove(Player player, Piece king, Piece[][] board);
    boolean canMoveWhileCheck(Player player, boolean[][] stopCheck, Piece[][] board);
    boolean isPinned(Player player, Piece king, Piece[][] board);
    boolean move(Player player, Player opponent, Piece[][] board, int row, int column);
}
