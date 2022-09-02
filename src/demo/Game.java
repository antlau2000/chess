package demo;

import demo.pieces.*;
import demo.view.BoardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static demo.pieces.Colour.White;
import static demo.pieces.Colour.Black;

public class Game {

    static Piece[][] board;
    static String[][] boardNotation;
    static Player playerWhite;
    static Player playerBlack;
    static List<Player> players = new ArrayList<>();
    static List<Piece> specialTurnPawns = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BoardView view = new BoardView();
        Piece currentPiece;

        System.out.println("0 for test without pawns:");
        if (scanner.nextInt() == 0) {
            startGameWithoutPawns();
        } else {
            startGame();
        }

//        startGame();

        Player currentPlayer = playerWhite;
        boolean[][] attackedPlaces;
        while (true) {
            view.print(players, board);
            currentPiece = choosePiece(scanner, currentPlayer);
            if (movePiece(scanner, currentPlayer, currentPiece)) {
                currentPlayer.protectPieces(board);
                attackedPlaces = currentPlayer.attackPlaces(board);
                if (currentPlayer.getColour() == White) {
                    currentPlayer = playerBlack;
                } else {
                    currentPlayer = playerWhite;
                }
                currentPlayer.setAttackedPlaces(attackedPlaces);
            }
        }
    }

    public static void startGame() {
        board = initiateBoard();
        boardNotation = initiateBoardNotation();
        playerWhite = new Player("Anthony", White);
        playerBlack = new Player("Mixa", Black);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                playerWhite.getPieces().add(board[i][j]);
                playerBlack.getPieces().add(board[7 - i][j]);
            }
        }
        players.add(playerWhite);
        players.add(playerBlack);
    }

    public static void startGameWithoutPawns() {
        board = initiateBoardWithoutPawns();
        boardNotation = initiateBoardNotation();
        playerWhite = new Player("Anthony", White);
        playerBlack = new Player("Mixa", Black);
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 8; j++) {
                playerWhite.getPieces().add(board[i][j]);
                playerBlack.getPieces().add(board[7 - i][j]);
            }
        }
        players.add(playerWhite);
        players.add(playerBlack);
    }

    public static Piece choosePiece(Scanner scanner, Player player) {
        Piece currentPiece = null;
        while (true) {
            System.out.println(player.getName() + ", choose a piece to move:");
            String input = scanner.next();
            for (int i = 0; i < boardNotation.length; i++) {
                if (boardNotation[i][0].contains(String.valueOf(input.charAt(1)))) {
                    for (int j = 0; j < boardNotation[i].length; j++) {
                        if (boardNotation[i][j].equals(input)) {
                            currentPiece = board[i][j];
                            break;
                        }
                    }
                    break;
                }
            }
            if (currentPiece == null) {
                System.out.println("No chess piece selected");
                System.out.println("Try again!");
                System.out.println();
            } else if (player.getColour() != currentPiece.getColour()) {
                System.out.println("Wrong colour");
                System.out.println("Try again!");
                System.out.println();
            } else if (!currentPiece.canMove(player, board)) {
                System.out.println("This chess piece can't move");
                System.out.println("Try again!");
                System.out.println();
            } else {
                System.out.println();
                break;
            }
        }
        return currentPiece;
    }

    public static boolean movePiece(Scanner scanner, Player currentPlayer, Piece currentPiece) {
        boolean isSpecialTurnUsed = false;
        while (true) {
            System.out.println(currentPlayer.getName() + ", choose a destination for your " + currentPiece.getName() + ":");
            String destination = scanner.next();
            if (destination.charAt(0) == '0') {
                return false;
            }
            int row = Character.getNumericValue(destination.charAt(1)) - 1;
            int column = destination.charAt(0) - 'a';
            if (row == currentPiece.getCurrentRow() && column == currentPiece.getCurrentColumn()) {
                System.out.println("That's the current place of your piece");
                System.out.println("Try again!");
                System.out.println();
                continue;
            }
            if (row < 0 || row > 7 || column < 0 || column > 7) {
                System.out.println("There's no such place on board");
                System.out.println("Try again!");
                System.out.println();
            } else {
                int distanceRow = Math.abs(row - currentPiece.getCurrentRow());
                if (currentPiece.move(currentPlayer, board, row, column)) {
                    if (currentPiece instanceof Pawn) {
                        int endRow;
                        if (currentPiece.getColour() == White) {
                            endRow = 7;
                        } else {
                            endRow = 0;
                        }
                        if (row == endRow) {
                            transformPawn(scanner, currentPlayer, currentPiece, board);
                        } else if (distanceRow == 2) {
                            isSpecialTurnUsed = true;
                        }
                    }
                    break;
                }
            }
        }
        if (specialTurnPawns.size() != 0) {
            Piece pawn;
            for (int i = 0; i < specialTurnPawns.size();) {
                pawn = specialTurnPawns.get(i);
                if (pawn.getColour() != currentPlayer.getColour()) {
                    ((Pawn) pawn).setSpecialTurnUsed(false);
                    specialTurnPawns.remove(i);
                } else {
                    i++;
                }
            }
        }
        if (isSpecialTurnUsed) {
            specialTurnPawns.add(currentPiece);
        }
        return true;
    }

    public static Piece[][] initiateBoard() {
        Board boardEntity = new Board();
        Piece[][] board = boardEntity.getArray();
        {
            board[0][0] = new Rook(White, 0, 0);
            board[0][1] = new Knight(White, 0, 1);
            board[0][2] = new Bishop(White, 0, 2);
            board[0][3] = new Queen(White, 0, 3);
            board[0][4] = new King(White, 0, 4);
            board[0][5] = new Bishop(White, 0, 5);
            board[0][6] = new Knight(White, 0, 6);
            board[0][7] = new Rook(White, 0, 7);
        }
        for (int i = 0; i < board[0].length; i++) {
            board[1][i] = new Pawn(White, 1, i);
            board[6][i] = new Pawn(Black, 6, i);
        }
        {
            board[7][0] = new Rook(Black, 7, 0);
            board[7][1] = new Knight(Black, 7, 1);
            board[7][2] = new Bishop(Black, 7, 2);
            board[7][3] = new Queen(Black, 7, 3);
            board[7][4] = new King(Black, 7, 4);
            board[7][5] = new Bishop(Black, 7, 5);
            board[7][6] = new Knight(Black, 7, 6);
            board[7][7] = new Rook(Black, 7, 7);
        }
        return board;
    }

    public static Piece[][] initiateBoardWithoutPawns() {
        Board boardEntity = new Board();
        Piece[][] board = boardEntity.getArray();
        {
            board[0][0] = new Rook(White, 0, 0);
            board[0][1] = new Knight(White, 0, 1);
            board[0][2] = new Bishop(White, 0, 2);
            board[0][3] = new Queen(White, 0, 3);
            board[0][4] = new King(White, 0, 4);
            board[0][5] = new Bishop(White, 0, 5);
            board[0][6] = new Knight(White, 0, 6);
            board[0][7] = new Rook(White, 0, 7);
        }
        {
            board[7][0] = new Rook(Black, 7, 0);
            board[7][1] = new Knight(Black, 7, 1);
            board[7][2] = new Bishop(Black, 7, 2);
            board[7][3] = new Queen(Black, 7, 3);
            board[7][4] = new King(Black, 7, 4);
            board[7][5] = new Bishop(Black, 7, 5);
            board[7][6] = new Knight(Black, 7, 6);
            board[7][7] = new Rook(Black, 7, 7);
        }
        return board;
    }

    public static String[][] initiateBoardNotation() {
        String[][] board = new String[8][8];
        char column = 'a';
        int row = 1;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = column + "" + row;
                column++;
            }
            row++;
            column = 'a';
        }
        return board;
    }

    public static void transformPawn(Scanner scanner, Player currentPlayer, Piece currentPiece, Piece[][] board) {
        int row = currentPiece.getCurrentRow();
        int column = currentPiece.getCurrentColumn();
        Colour colour = currentPiece.getColour();
        System.out.println(currentPlayer.getName() + ", your pawn is transforming. Choose a piece:");
        System.out.println("1 - Queen");
        System.out.println("2 - Rook");
        System.out.println("3 - Bishop");
        System.out.println("4 - Knight");
        boolean isRightNumber = false;
        do {
            int newPiece = Integer.parseInt(scanner.next());
            switch (newPiece) {
                case 1:
                    board[row][column] = new Queen(colour, row, column);
                    isRightNumber = true;
                    break;
                case 2:
                    board[row][column] = new Rook(colour, row, column);
                    isRightNumber = true;
                    break;
                case 3:
                    board[row][column] = new Bishop(colour, row, column);
                    isRightNumber = true;
                    break;
                case 4:
                    board[row][column] = new Knight(colour, row, column);
                    isRightNumber = true;
                    break;
                default:
                    System.out.println("Invalid number!");
                    System.out.println("Try again!");
                    System.out.println();
            }
        } while (!isRightNumber);
    }
}
