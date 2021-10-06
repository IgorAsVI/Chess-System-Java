package Chess;

import Chess.Pieces.ChessPosition;
import Chess.Pieces.King;
import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Rook;

import java.util.ArrayList;
import java.util.List;


public class ChessMatch {
    private int turn;
    private Color currentPlayer;
    private Board board;
    List<Piece> piecesOnTheBoard = new ArrayList<>();
    List<Piece> capturedPieces = new ArrayList<>();

    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public ChessMatch() {
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.White;
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
        piecesOnTheBoard.add(piece);
    }

    public boolean[][] possibleMoves(ChessPosition sourceposition) {
        Position position = sourceposition.toPosition();
        validateSourceposition(position);
        return board.piece(position).possibleMoves();
    }

    public ChessPiece performChessmove(ChessPosition srcPosition, ChessPosition targPosition) {
        Position source = srcPosition.toPosition();
        Position target = targPosition.toPosition();
        validateSourceposition(source);
        validateTargetposition(source, target);
        Piece capturedPiece = makemove(source, target);
        nextTurn();
        return (ChessPiece) capturedPiece;
    }

    private void validateSourceposition(Position position) {
        if (!board.thereIsAPiece(position)) {
            throw new ChessException("There is no piece on source position");
        }
        if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
            throw new ChessException("The chosen piece is not yours");
        }
        if (!board.piece(position).isThereAnyPossibleMoves()) {
            throw new ChessException("There is no possibles moves for the chosen piece");
        }
    }

    private void validateTargetposition(Position source, Position target) {
        if (!board.piece(source).possibleMove(target)) {
            throw new ChessException("The chosen piece can't move to target position");
        }
    }

    private Piece makemove(Position source, Position target) {
        Piece p = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);
        board.placepiece(p, target);
        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }
        return capturedPiece;


    }


    private void nextTurn() {
        turn++;
        if (currentPlayer == Color.White) {
            currentPlayer = Color.Black;
        } else {
            currentPlayer = Color.White;
        }
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
        placenewpiece('d', 8, new King(board, Color.Black));
        ;

    }

}
