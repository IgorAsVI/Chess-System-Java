package Chess.Pieces;

import Chess.ChessPiece;
import Chess.Color;
import boardgame.Board;

public class Bishop extends ChessPiece {
    public Bishop(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "B";
    }
}
