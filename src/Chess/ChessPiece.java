package Chess;

import Chess.Pieces.ChessPosition;
import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;



public abstract class ChessPiece extends Piece {
    private Color color;
    private int movecount;
    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    protected boolean isThereopponentPiece(Position position) {
        ChessPiece p = (ChessPiece) getBoard().piece(position);
        return p != null && p.getColor() != color;
    }

    public ChessPosition getChessPosition() {
        return ChessPosition.fromPosition(position);
    }

   public int getMovecount(){
        return movecount;
   }
    public void increaseMoveCount(){
        movecount++;
    }
    public void decreaseMoveCount(){
        movecount--;
    }
}
