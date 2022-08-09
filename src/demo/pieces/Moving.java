package demo.pieces;

import demo.Player;

public interface Moving {
    boolean canMove(Player player, Piece[][] board);
    boolean move(Player player, Piece[][] board, int row, int column);
}
