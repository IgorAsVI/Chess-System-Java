package Chess.Pieces;

import Chess.ChessPiece;
import Chess.Color;
import boardgame.Board;

public class Knight extends ChessPiece {
    @Override
    public String toString() {
        return "K";
    }

    public Knight(Board board, Color color) {
        super(board, color);
    }
}
