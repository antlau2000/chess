package demo.pieces;

import demo.Player;

public interface Moving {
    boolean move(Player player, Piece[][] board, int row, int column);
}
