package Chess;

import boardgame.Board;

public class ChessException extends RuntimeException{
public ChessException(String msg){
super(msg);

}

    public static class Queen extends ChessPiece {
        public Queen(Board board, Color color) {
            super(board, color);
        }

        @Override
        public String toString() {
            return "Q";
        }

        @Override
        public boolean[][] possibleMoves() {
            return new boolean[0][];
        }
    }
}
