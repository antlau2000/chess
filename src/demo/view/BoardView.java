package demo.view;

import demo.Player;
import demo.pieces.Piece;

import java.util.List;

public class BoardView {
    public void print(List<Player> players, Piece[][] board) {
        Player whitePlayer = players.get(0);
        Player blackPlayer = players.get(1);

        blackPlayer(blackPlayer);
        System.out.println();

        board(board);
        System.out.println();

        whitePlayer(whitePlayer);
        System.out.println();
        System.out.println();
    }

    public void print(String[][] board) {
        for (int i = board.length - 1; i >= 0; i--) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void board(Piece[][] board) {
        for (int i = board.length - 1; i >= 0; i--) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == null) {
                    System.out.print('\u2672');
                } else {
                    System.out.print(board[i][j].getView());
                }
            }
            System.out.println();
        }
        System.out.print("  ");
        char c = 'a';
        for (int i = 0; i < board.length; i++) {
            if (i % 2 == 0) {
                System.out.print(c++ + " ");
            } else {
                System.out.print(c++);
            }
        }
        System.out.println();
    }

    public void blackPlayer(Player player) {
        nameOfPlayer(player);
        tokenPieces(player);
    }

    public void whitePlayer(Player player) {
        tokenPieces(player);
        nameOfPlayer(player);
    }

    public void nameOfPlayer(Player player) {
        System.out.println(player.getName() + "(" + player.getColour() + ") - "
                + player.getTokenPieces().stream().map(Piece::getPoint).reduce(0, Integer::sum));
    }

    public void tokenPieces(Player player) {
        boolean firstPiece = true;
        if (player.getTokenPieces().size() != 0) {
            for (Piece piece : player.getTokenPieces()) {
                if (firstPiece) {
                    System.out.print(piece.getView());
                    firstPiece = false;
                } else {
                    System.out.print(", " + piece.getView());
                }
            }
            System.out.println();
        }
    }
}
