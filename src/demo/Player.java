package demo;

import demo.pieces.Colour;
import demo.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private Colour colour;
    private List<Piece> tokenPieces;
    private boolean[][] attackedPlaces;
    private List<Piece> pieces;

    public Player(String name, Colour colour) {
        this.name = name;
        this.colour = colour;
        tokenPieces = new ArrayList<>();
        attackedPlaces = new boolean[8][8];
        pieces = new ArrayList<>();
    }

    public void protectPieces(Piece[][] board) {
        for (Piece piece : pieces) {
            piece.setProtected(false);
        }
        for (Piece piece : pieces) {
            piece.protect(this, board);
        }
    }

    public boolean[][] attackPlaces(Piece king, Piece[][] board) {
        attackedPlaces = new boolean[8][8];
        for (Piece piece : pieces) {
            piece.canMove(this, king, board);
        }
        for (Piece piece : pieces) {
            boolean[][] placesToMoveTo = piece.getPlacesToMoveTo();
            for (int i = 0; i < attackedPlaces.length; i++) {
                for (int j = 0; j < attackedPlaces[0].length; j++) {
                    if (placesToMoveTo[i][j]) {
                        attackedPlaces[i][j] = true;
                    }
                }
            }
        }
        return attackedPlaces;
    }

    public Piece getAttacker(int kingRow, int kingColumn) {
        for (Piece piece : pieces) {
            if (piece.getPlacesToMoveTo()[kingRow][kingColumn]) {
                return piece;
            }
        }
        return null;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Piece> getTokenPieces() {
        return tokenPieces;
    }

    public void setTokenPieces(List<Piece> tokenPieces) {
        this.tokenPieces = tokenPieces;
    }

    public boolean[][] getAttackedPlaces() {
        return attackedPlaces;
    }

    public void setAttackedPlaces(boolean[][] attackedPlaces) {
        this.attackedPlaces = attackedPlaces;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }
}
