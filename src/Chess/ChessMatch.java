package Chess;

import Chess.Pieces.ChessPosition;
import Chess.Pieces.King;
import Chess.Pieces.Rook;
import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

;

public class ChessMatch {

    private Board board;

    public ChessMatch() {
        board = new Board(8, 8);
        initialsetup();
    }

    public ChessPiece[][] getpieces() {
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                mat[i][j] = (ChessPiece) board.piece(i, j);
            }


        }
        return mat;
    }

    private void placenewpiece(char column, int row, ChessPiece piece) {
        board.placepiece(piece, new ChessPosition(column, row).toPosition());
    }

    public ChessPiece performChessmove(ChessPosition srcPosition,ChessPosition targPosition){
        Position source = srcPosition.toPosition();
        Position target = targPosition.toPosition();
        validateSourceposition(source);
        Piece capturedPiece = makemove(source,target);
        return (ChessPiece) capturedPiece;
    }
    private void validateSourceposition(Position position0){
        if (!board.thereIsAPiece(position0)){
            throw new ChessExcepition("There is no piece on source position");
        }
    }
    private Piece makemove(Position source,Position target){
Piece p = board.removePiece(source);
Piece captredPiece = board.removePiece(target);
board.placepiece(p,target);
return captredPiece;

    }

    private void initialsetup() {
        placenewpiece('c', 1, new Rook(board, Color.White));
        placenewpiece('c', 2, new Rook(board, Color.White));
        placenewpiece('d', 2, new Rook(board, Color.White));
        placenewpiece('e', 2, new Rook(board, Color.White));
        placenewpiece('e', 1, new Rook(board, Color.White));
        placenewpiece('d', 1, new King(board, Color.White));

        placenewpiece('c', 7, new Rook(board, Color.Black));
        placenewpiece('c', 8, new Rook(board, Color.Black));
        placenewpiece('d', 7, new Rook(board, Color.Black));
        placenewpiece('e', 7, new Rook(board, Color.Black));
        placenewpiece('e', 8, new Rook(board, Color.Black));
        placenewpiece('d', 8, new King(board, Color.Black));;

    }

}
