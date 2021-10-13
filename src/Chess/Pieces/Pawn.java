

package chess.pieces;

import Chess.ChessMatch;
import Chess.ChessPiece;
import Chess.Color;
import Chess.Pieces.Rook;
import boardgame.Board;
import boardgame.Position;
import org.omg.PortableServer.POA;

public class Pawn extends ChessPiece {

    private ChessMatch chessMatch;

    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public String toString() {
        return "P";
    }


    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

        if (getColor() == Color.White) {
            p.setValues(position.getRow() - 1, position.getColumn());
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }
            p.setValues(position.getRow() - 2, position.getColumn());
            Position p2 = new Position(position.getRow() - 1, position.getColumn());
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMovecount() == 0) {
                mat[p.getRow()][p.getColumn()] = true;
            }
            p.setValues(position.getRow() - 1, position.getColumn() - 1);
            if (getBoard().positionExists(p) && isThereopponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }
            p.setValues(position.getRow() - 1, position.getColumn() + 1);
            if (getBoard().positionExists(p) && isThereopponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }


            // Special move en passant white

            if (position.getRow() == 3){
                Position left = new Position(position.getRow(), position.getColumn() - 1) ;
                if (getBoard().positionExists(left) && isThereopponentPiece(left) && getBoard().piece(left)== chessMatch.getEnpassantvulnerable()){
                    mat[left.getRow() - 1][left.getColumn()] = true;
                }
                Position right = new Position(position.getRow(), position.getColumn()+1) ;
                if (getBoard().positionExists(right) && isThereopponentPiece(right) && getBoard().piece(right)== chessMatch.getEnpassantvulnerable()){
                    mat[right.getRow() - 1][right.getColumn()] = true;
                }
            }
        } else {
            p.setValues(position.getRow() + 1, position.getColumn());
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }
            p.setValues(position.getRow() + 2, position.getColumn());
            Position p2 = new Position(position.getRow() + 1, position.getColumn());
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMovecount() == 0) {
                mat[p.getRow()][p.getColumn()] = true;
            }
            p.setValues(position.getRow() + 1, position.getColumn() - 1);
            if (getBoard().positionExists(p) && isThereopponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }
            p.setValues(position.getRow() + 1, position.getColumn() + 1);
            if (getBoard().positionExists(p) && isThereopponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }
            // Special move en passant black
            if (position.getRow() == 4){
                Position left = new Position(position.getRow(), position.getColumn()-1) ;
                if (getBoard().positionExists(left) && isThereopponentPiece(left) && getBoard().piece(left)== chessMatch.getEnpassantvulnerable()){
                    mat[left.getRow() + 1][left.getColumn()] = true;
                }
                Position right = new Position(position.getRow(), position.getColumn()+1) ;
                if (getBoard().positionExists(right) && isThereopponentPiece(right) && getBoard().piece(right)== chessMatch.getEnpassantvulnerable()){
                    mat[right.getRow() + 1][right.getColumn()] = true;
                }
            }

        }
        return mat;
    }
}






