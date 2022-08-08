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

    public Player(String name, Colour colour) {
        this.name = name;
        this.colour = colour;
        tokenPieces = new ArrayList<>();
        attackedPlaces = new boolean[8][8];
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
}
