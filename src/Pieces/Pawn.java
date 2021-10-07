package Pieces;

import Chess.ChessPiece;
import Chess.Color;
import boardgame.Board;

public class Pawn extends ChessPiece {
    public Pawn(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "P";
    }

    @Override
    public boolean[][] possibleMoves() {
        return new boolean[0][];
    }
}
